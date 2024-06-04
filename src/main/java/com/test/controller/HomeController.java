package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.dao.UserDao;
import com.test.entities.User;
import com.test.helper.MyMessage;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/")

	public String index(Model m) {
		m.addAttribute("title", "Home");
		return "home";
	}

	@GetMapping("/hawa")
	public String esso(Model m) {
		m.addAttribute("user", new User());
		return "hawa";
	}

	@GetMapping("/signup")
	public String signup(Model m) {

		// List<String> Users = this.userDao.getAllUser();
		// System.out.println(Users);

		m.addAttribute("title", "Sign Up");
		m.addAttribute("user", new User());

		return "signup";
	}

	@PostMapping("/doprocess")
	public String processForm(@Valid @ModelAttribute("user") User user, BindingResult result,

			Model m, HttpSession session) {

		try {

			if (result.hasErrors()) {
				System.out.println(result);
				m.addAttribute("user", user);
				return "hawa";
			}

			if (user.isAgreeds() == false) {
				m.addAttribute("user", user);
				session.setAttribute("message", new MyMessage("Accept the terms and agreement", "alert-danger"));
				System.out.println("error bhayo");
				return "hawa";
			}

			user.setRole("ROLE_USER");
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			this.userDao.save(user);

			m.addAttribute("user", new User());

			System.out.println("Details are" + user);
			session.setAttribute("message", new MyMessage("Succesfully Registered", "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("user", user);
			session.setAttribute("message", new MyMessage("This email have already been used", "alert-danger"));
		}
		return "hawa";
	}

	@GetMapping("/login")
	public String login() {

		return "login";
	}

}
