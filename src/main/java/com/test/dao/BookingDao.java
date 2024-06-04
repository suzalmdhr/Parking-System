package com.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.entities.Bookings;
import com.test.entities.User;

public interface BookingDao extends JpaRepository<Bookings, Integer> {

	// @Query("select u from User u where u.email = :email")
	// public User getUserByEmail(@Param("email") String email);

	@Query("select slot from Bookings")
	public List<String> getAllSlots();

	@Query("select status from Bookings")
	public List<String> getAllStatus();

	boolean existsByUserAndStatus(User user, Boolean status);

}
