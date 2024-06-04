package com.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.BookingDao;
import com.test.dao.SlotDao;
import com.test.entities.Bookings;
import com.test.entities.Slot;

import jakarta.transaction.Transactional;

@Service
public class EntityService {
	
	@Autowired
	private SlotDao slotDao;
	@Autowired
	private BookingDao bookingDao;
	
	
	@Transactional
	public void saveEntities(Slot slot,Bookings books) {
		
		this.bookingDao.save(books);
		this.slotDao.save(slot);
		
	}

}
