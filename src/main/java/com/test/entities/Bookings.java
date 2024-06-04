package com.test.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Bookings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bId;
	
	private String licNum;
	
	private LocalDateTime sDate;
	
	private LocalDateTime eDate;
	
	private String username;
	
	private String email;
	
	private Boolean status;
	
	private String slot;
	
	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@OneToOne
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "books")
	private Slot slots;

	public Slot getSlots() {
		return slots;
	}

	public void setSlots(Slot slots) {
		this.slots = slots;
	}

	public String getLicNum() {
		return licNum;
	}

	public void setLicNum(String licNum) {
		this.licNum = licNum;
	}

	

	
	

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public LocalDateTime getsDate() {
		return sDate;
	}

	public void setsDate(LocalDateTime sDate) {
		this.sDate = sDate;
	}

	public LocalDateTime geteDate() {
		return eDate;
	}

	public void seteDate(LocalDateTime eDate) {
		this.eDate = eDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public Bookings(int bId, String licNum, LocalDateTime sDate, LocalDateTime eDate, String username, String email,
			User user) {
		super();
		this.bId = bId;
		this.licNum = licNum;
		this.sDate = sDate;
		this.eDate = eDate;
		this.username = username;
		this.email = email;
		this.user = user;
	}

	public Bookings() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Bookings [licNum=" + licNum + ", sDate=" + sDate + ", eDate=" + eDate + ", username=" + username
				+ ", email=" + email + "]";
	}
	
	
	

}
