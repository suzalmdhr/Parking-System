package com.test.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.type.descriptor.java.LocalDateJavaType;
import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.dao.BookingDao;
import com.test.dao.FloorRepo;
import com.test.dao.SlotDao;
import com.test.dao.UserDao;
import com.test.entities.Bookings;
import com.test.entities.Slot;
import com.test.entities.User;
import com.test.entities.floor;
import com.test.service.EntityService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private BookingDao bookingDao;

	@Autowired
	private FloorRepo floorRepo;

	@Autowired
	private SlotDao slotDao;

	@Autowired
	private EntityService entityService;

	// common data
	@ModelAttribute
	public void commonData(Model m, Principal princi) {

		User user = this.userDao.getUserByEmail(princi.getName());

		String username = user.getUsername();
		m.addAttribute("username", username);

	}

	@GetMapping("/home")
	public String home(Principal princi, Model m) {

		m.addAttribute("title", "Welcome - ADMIN");

		return "admin/Home";
	}

	// opening booking form

	// to collect form field
	// @PostMapping("/process-contact")
	// public String addBook(@ModelAttribute Bookings books,@ModelAttribute Slot
	// slots, Principal princi,Model m,
	// @RequestParam("sid")String sid,@RequestParam("slotname")String slotname) {
	// System.out.println(books);
	// System.out.println(sid);
	//
	// int arkoSid= Integer.parseInt(sid);
	// Optional<Slot> slot = this.slotDao.findById(arkoSid);
	// System.out.println("slot is "+ slot);
	//
	// String name = princi.getName();//email aayo
	//
	// User user = this.userDao.getUserByEmail(name);//email bata find garyo user
	// aayo
	//
	//
	//
	// books.setStatus(true);
	//
	// books.setUser(user);
	//
	// user.setBooks(books);
	//
	// slots.setSlotName(slotname);
	// slots.setStatus(true);
	//
	//
	//
	// this.userDao.save(user);
	//
	//
	// m.addAttribute("value", books.getStatus());
	// m.addAttribute("slot", books.getSlot());
	//
	// System.out.println("now updated user is "+user);
	// return "admin/book";
	// }

	// book garne process this is
	@PostMapping("/process-contact")
	public String addForm(@ModelAttribute Bookings bookings, @ModelAttribute Slot slots, Principal principal, Model m) {

		String name = principal.getName();

		User userByEmail = this.userDao.getUserByEmail(name);

		if (bookingDao.existsByUserAndStatus(userByEmail, true)) {
			m.addAttribute("errorMessage", "You cannot book more than one slot");
			return "admin/errorpage";
		}

		bookings.setStatus(true);
		bookings.setUser(userByEmail);

		int slotkoId = slots.getsId();

		Slot slot = this.slotDao.findById(slotkoId).get();

		slot.setStatus(true);

		slot.setBooks(bookings);

		System.out.println("slot is" + slot);
		System.out.println("bookings is " + bookings);

		this.entityService.saveEntities(slot, bookings);

		List<Slot> slots2 = this.slotDao.getAllSlots();
		m.addAttribute("slots", slots2);

		Long fid = slot.getFloors().getId();

		return String.format("redirect:/admin/book/%d", fid);
	}

	// for showing parking list

	@GetMapping("/parkinglist")
	public String parkingList(Model model) {
		List<floor> all = this.floorRepo.findAll();
		model.addAttribute("floors", all);

		return "admin/parkingList";
	}

	@GetMapping("/book")
	public String showBook(Model m) {
		// List<String> allSlots = this.bookingDao.getAllSlots();
		// System.out.println(allSlots);
		//
		// m.addAttribute("slots", allSlots);

		// List<String> allStatus = this.bookingDao.getAllStatus();
		// System.out.println(allStatus);
		//
		// m.addAttribute("status", allStatus);

		List<Slot> slots2 = this.slotDao.getAllSlots();
		System.out.println(slots2);
		m.addAttribute("slots", slots2);
		return "admin/book";
	}

	@GetMapping("/addParkingSlot")
	public String addParking(Model model) {

		System.out.println("This is parking slot worked ");
		model.addAttribute("flooring", new floor());
		return "admin/parkingSlot";
	}

	@PostMapping("/processSlot")
	public String processSlot(@ModelAttribute floor floors) {
		System.out.println(floors);

		System.out.println(floors.getNumberOfSlots());
		this.floorRepo.save(floors);

		for (int i = 1; i <= floors.getNumberOfSlots(); i++) {

			Slot slots = new Slot();

			slots.setStatus(false);
			slots.setSlotName("Slot-" + i);
			slots.setFloors(floors);

			System.out.println(slots);
			this.slotDao.save(slots);
		}

		return "admin/parkingSlot";
	}

	// delete the foor
	@GetMapping("/delete/{fid}")
	public String deleteFloor(@PathVariable("fid") Long fid, Model m) {
		Optional<floor> optionalFloor = this.floorRepo.findById(fid);
		floor nayaFloor = optionalFloor.get();

		boolean hasBookedSlots = nayaFloor.getSlots().stream()
				.anyMatch(slot -> slot.getStatus() != null && slot.getStatus());

		if (hasBookedSlots) {

			return "admin/arkoerror";
		}

		else {
			this.floorRepo.delete(nayaFloor);
		}

		return "redirect:/admin/parkinglist";

	}

	// booking on the basis of flooor

	@GetMapping("/book/{fid}")
	public String showFloor(@PathVariable("fid") Long fid, Model model) {
		List<Slot> slotsByFloorId = this.slotDao.getSlotsByFloorId(fid);
		model.addAttribute("slots", slotsByFloorId);

		Optional<floor> floorbyId = this.floorRepo.findById(fid);

		floor floorPayo = floorbyId.get();

		model.addAttribute("name", floorPayo.getName());
		model.addAttribute("address", floorPayo.getAddress());

		return "admin/book";
	}

	@PostMapping("/delete/users")
	public String deleteBookers(@ModelAttribute Slot slots) {

		Optional<Slot> slotsById = this.slotDao.findById(slots.getsId());
		Slot slot = slotsById.get();
		int getbIds = slot.getBooks().getbId();
		slot.setBooks(null);
		slot.setStatus(false);
		this.slotDao.save(slot);
		Bookings bookings = this.bookingDao.findById(getbIds).get();
		// this.bookingDao.delete(bookings);
		System.out.println(bookings);

		bookings.setLicNum(null);
		bookings.setsDate(null);
		bookings.seteDate(null);
		bookings.setUsername(null);
		bookings.setEmail(null);
		bookings.setStatus(null);
		bookings.setUser(null);

		this.bookingDao.save(bookings);

		// this.slotDao.save(slot);
		// System.out.println(slot);

		Long fid = slot.getFloors().getId();

		return String.format("redirect:/admin/book/%d", fid);
	}

}
