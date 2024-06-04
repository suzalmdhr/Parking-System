package com.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.entities.Slot;

public interface SlotDao extends JpaRepository<Slot, Integer> {

	// @Query("select status from Bookings")
	// public List<String> getAllStatus();

	// @Query("select u from User u where u.email = :email")
	// public User getUserByEmail(@Param("email") String email);

	@Query("from Slot")
	public List<Slot> getAllSlots();

	@Query("select u from Slot u where u.sId = :sId")
	public Slot getASlot(@Param("sId") int sId);

	@Query("select s from Slot s where s.floors.id= :floorId")
	public List<Slot> getSlotsByFloorId(@Param("floorId") Long floorId);

}
