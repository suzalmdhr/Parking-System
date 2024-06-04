package com.test.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UserId;
	
	@NotBlank(message = "Username should not be blank")
	@Size(min = 2,max = 10,message = "Min 2 and max 10 chars are allowed")
	private String username;
	
//	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$",message = "Email isn't in well format")
	private String email;
	
	
	private long phoneNumber;
	
	@Pattern(regexp = "^(?=.*[0-9]).{8,}$",message = "Password should contain a number")
	private String password;
	
	private String role;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
	private Bookings books;
	
	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public Bookings getBooks() {
		return books;
	}

	public void setBooks(Bookings books) {
		this.books = books;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private String Gender;
	
	
	private boolean agreeds;

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

	

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public boolean isAgreeds() {
		return agreeds;
	}

	public void setAgreeds(boolean agreeds) {
		this.agreeds = agreeds;
	}
	
	
	

	

	public User(int userId,
			@NotBlank(message = "Username should not be blank") @Size(min = 2, max = 10, message = "Min 2 and max 10 chars are allowed") String username,
			@Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Email isn't in well format") String email,
			long phoneNumber,
			@Pattern(regexp = "^(?=.*[0-9]).{8,}$", message = "Password should contain a number") String password,
			String role, Bookings books, String gender, boolean agreeds) {
		super();
		UserId = userId;
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.role = role;
		this.books = books;
		Gender = gender;
		this.agreeds = agreeds;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [UserId=" + UserId + ", username=" + username + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", password=" + password + ", role=" + role + ", books=" + books + ", Gender=" + Gender + ", agreeds="
				+ agreeds + "]";
	}

	

	
	
	

}
