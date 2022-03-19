package com.generic.um.rest.impl;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generic.core.model.User;
import com.generic.core.rest.impl.GenericRestImpl;
import com.generic.core.wrapper.UserWrapper;
import com.generic.um.restconsumer.IOtpRestConsumer;
import com.generic.um.service.IUserService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/unauth/user")
public class UserRestImpl extends GenericRestImpl<User, Integer> {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(UserRestImpl.class);
	
	@Autowired
	IUserService service;
	
	@Autowired
	IOtpRestConsumer otpRestConsumer;
	
	@PostConstruct
	public void setService(){
		super.setService(service);
	}
	
	@PostMapping("/verifyOTPAndCreateUser/{otp}/{email}")
	public User verifyOTPAndCreateUser(User entity,@PathVariable("otp") String otp,@PathVariable("email") String email)  {
			return service.verifyOTPAndCreateUser(entity,otp,email);
	}
	
	@GetMapping("/isUsernameExists/{userName}")
	public Boolean isUsernameExists(@PathVariable("userName") String userName) {
		logger.info("Inside isUsernameExists:{}"+userName);
		return service.isUsernameExists(userName);
	}
	
	@PostMapping("/forgotpassword")
	public String forgotPassword(User user) {
		return service.forgotPassword(user);
	}
	
	
	
	@GetMapping("/updatepassword/{password}/{emailId}/{otp}")
	public String updatePassword(@PathVariable("emailId") String emailId, @PathVariable("otp") String otp,
			@PathVariable("password") String password) {
		logger.info("Going to update password");
		return service.updatePasswordByOTPVerification(emailId, otp, password);
	}
	
	@PostMapping("/isvaliduser")
	public UserWrapper isValidUser(@RequestBody User user) {
		logger.info("Going to check user validity new {} " , user.getUserName());
		return service.isValidUser(user);
	}
	
	
	
	@PostMapping("/verifyAndCreateSocialMediaUser")
	public UserWrapper verifyAndCreateSocialMediaUser(@RequestBody UserWrapper user) {
		logger.info("Going inside verifyAndCreateSocialMediaUser for :{}" , user.getUserName());
		return service.verifyAndCreateSocialMediaUser(user);
	}
	
	@GetMapping("/getMessage")
	public String getMessage(){
		return otpRestConsumer.getMessage();
	}
	
	
}
