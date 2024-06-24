package com.test.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.dao.UserDao;
import com.test.emailService.EmailService;
import com.test.entities.User;
import com.test.helper.MyMessage;


import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/forgot")
    public String openEmail(){

        return "forgotPage";
    }

    @PostMapping("/sendOTP")
    public String sendOTP(@RequestParam("email") String email,HttpSession session){
Random random=new Random();
int otp = random.nextInt(99999);
System.out.println("otp is"+ otp);


String subject="Reset password";
		String message= "<div style='border:1px solid #e2e2e2; padding:20px'> "
							+"<h1>Your OTP is "+	otp
							+"</h1></div>";
                           
                          
                            boolean sendgarekoOTP = this.emailService.sendEmail(email,subject,message);

                            User users = this.userDao.getUserByEmail(email);
                            if(users==null) {
                                System.out.println("error happened");
                                session.setAttribute("message", new MyMessage("You have not signed in ", "alert-danger"));
                                return "forgotPage";
                            }
                            
                            else {
                                session.setAttribute("myotp", otp);
                                session.setAttribute("myemail", email);
                                session.setAttribute("message", new MyMessage("OTP Sent", "alert-success"));
                                return "verify_otp";
                            }


          


      
    }

@PostMapping("/processOTP")
    public String processOTP(@RequestParam("otp") int otp,HttpSession session,Model model){
        System.out.println(otp);
        Integer enteredOtp = (Integer) session.getAttribute("myotp");
        String enteredEmail = (String) session.getAttribute("myemail");
        if(otp == enteredOtp ){
                model.addAttribute("myemail", enteredEmail);
                session.setAttribute("message", new MyMessage("Change your password", "alert-success"));
                return "changePassword";
        }
        else{
            session.setAttribute("message", new MyMessage("OTP didn't matched", "alert-danger"));
            return "verify_otp";
        }
       

       
    }

    @Autowired
    private BCryptPasswordEncoder bcruBCryptPasswordEncoder;

    @PostMapping("/changepassword")
    public String changePass(@RequestParam("email")String email, @RequestParam("nPass") String nPass,HttpSession session){

User user = this.userDao.getUserByEmail(email);
System.out.println(user);

user.setPassword(bcruBCryptPasswordEncoder.encode(nPass));
this.userDao.save(user);
session.setAttribute("message",new MyMessage("Password changed successfully", "alert-success") );
       

return "changePassword";
    }

}
