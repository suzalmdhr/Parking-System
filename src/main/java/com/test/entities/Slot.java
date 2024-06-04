package com.test.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sId;

	private String SlotName;

	private LocalDate sDates;

	private LocalTime sTime;
	private Boolean status;

	@JsonIgnore
	@ManyToOne
	private floor floors;

	public LocalTime getsTime() {
		return sTime;
	}

	public void setsTime(LocalTime sTime) {
		this.sTime = sTime;
	}

	public LocalDate getsDates() {
		return sDates;
	}

	public void setsDates(LocalDate sDates) {
		this.sDates = sDates;
	}

	public Bookings getBooks() {
		return books;
	}

	public void setBooks(Bookings books) {
		this.books = books;
	}

	@JsonIgnore
	@OneToOne
	private Bookings books;

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public String getSlotName() {
		return SlotName;
	}

	public void setSlotName(String slotName) {
		SlotName = slotName;
	}

	// public LocalDate getsDate() {
	// return sDate;
	// }
	//
	// public void setsDate(LocalDate sDate) {
	// this.sDate = sDate;
	// }
	//
	// public LocalTime getsTime() {
	// return sTime;
	// }
	//
	// public void setsTime(LocalTime sTime) {
	// this.sTime = sTime;
	// }

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Slot() {
		super();
		// TODO Auto-generated constructor stub
	}

	public floor getFloors() {
		return floors;
	}

	public void setFloors(floor floors) {
		this.floors = floors;
	}

	public Slot(int sId, String slotName, LocalDate sDates, LocalTime sTime, Boolean status, floor floors,
			Bookings books) {
		this.sId = sId;
		SlotName = slotName;
		this.sDates = sDates;
		this.sTime = sTime;
		this.status = status;
		this.floors = floors;
		this.books = books;
	}

	@Override
	public String toString() {
		return "Slot [sId=" + sId + ", SlotName=" + SlotName + ", sDates=" + sDates + ", sTime=" + sTime + ", status="
				+ status + ", books=" + books + "]";
	}

}
