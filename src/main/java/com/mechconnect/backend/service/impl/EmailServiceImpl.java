package com.mechconnect.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mechconnect.backend.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	  @Autowired
	    private JavaMailSender mailSender;

	    @Override
	    public void sendOtpEmail(String toEmail, String otp) {

	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject("MechConnect - Password Reset OTP");
	        message.setText(
	            "Dear User,\n\n" +
	            "Your OTP for password reset is: " + otp + "\n\n" +
	            "This OTP is valid for 5 minutes.\n\n" +
	            "If you did not request this, please ignore this email.\n\n" +
	            "Regards,\nMechConnect Team"
	        );

	        mailSender.send(message);
	    }
	}