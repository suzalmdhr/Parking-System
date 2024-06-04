package com.test.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.dao.SlotDao;
import com.test.entities.Slot;

@Controller
public class SlotController {
	
	@Autowired
	private SlotDao slotDao;
	
	@GetMapping("/slots")
	@ResponseBody
	public String allSlots() {
		
	List<Slot> allSlots = this.slotDao.getAllSlots();
	System.out.println(allSlots);
		return "this is dummy slot";
	}

}
