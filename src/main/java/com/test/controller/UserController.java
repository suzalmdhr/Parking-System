package com.test.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.dao.BookingDao;
import com.test.dao.FloorRepo;
import com.test.dao.SlotDao;
import com.test.dao.UserDao;
import com.test.entities.Bookings;
import com.test.entities.Slot;
import com.test.entities.User;
import com.test.entities.floor;
import com.test.helper.MyMessage;
import com.test.service.EntityService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private FloorRepo floorRepo;

	@Autowired
	private SlotDao slotDao;

	@Autowired
	private BookingDao bookingDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private EntityService entityService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@ModelAttribute
	public void commonData(Model m,Principal princi ){


		
		User userByEmail = this.userDao.getUserByEmail(princi.getName());
		m.addAttribute("username", userByEmail.getUsername());
	}

	@GetMapping("/login")
	public String userLogin() {

		return "login";
	}

	@GetMapping("/userHome")
	public String userHome() {

		return "user/userHome";
	}

	

	@GetMapping("/parkinglist")
	public String userParking(Model model) {
		List<floor> all = this.floorRepo.findAll();

		model.addAttribute("floors", all);
		return "user/parkinglist";
	}

	@GetMapping("/book/{fid}")
	public String showFloor(@PathVariable("fid") Long fid, Model model) {

		floor floor = this.floorRepo.findById(fid).get();
		model.addAttribute("name", floor.getName());
		model.addAttribute("address", floor.getAddress());

		List<Slot> slotsByFloorId = this.slotDao.getSlotsByFloorId(fid);

		model.addAttribute("slots", slotsByFloorId);
		return "user/book";
	}

	@GetMapping("/process-contact")
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

		return String.format("redirect:/user/book/%d", fid);
	}


	//password change User ko
	@GetMapping("/userChange")
	public String changePass(){

		return "user/change";
	}


	@PostMapping("/change-password")
	public String processPass(@RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass,Principal principal,HttpSession session){

		System.out.println(oldPass);
		System.out.println(newPass);

		User user = this.userDao.getUserByEmail(principal.getName());

		if(this.bCryptPasswordEncoder.matches(oldPass, user.getPassword())){
			System.out.println("You can now change it");
			user.setPassword(bCryptPasswordEncoder.encode(newPass));
			this.userDao.save(user);
			session.setAttribute("message", new MyMessage("Password changed successfully", "alert-success"));
			
		}
		else{
			System.out.println("didnt worked");
			session.setAttribute("message", new MyMessage("Password doesn't match", "alert-danger"));
		}





		return "user/change";
	}

	@GetMapping("checkBookingStatus")
	@ResponseBody
	public boolean checkBookingStatus(Principal principal){

		String name = principal.getName();
		User userByEmail = this.userDao.getUserByEmail(name);

		return bookingDao.existsByUserAndStatus(userByEmail, true);

	}
}
