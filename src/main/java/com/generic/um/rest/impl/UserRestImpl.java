package com.generic.um.rest.impl;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generic.core.constants.IGenericConstants;
import com.generic.core.model.User;
import com.generic.core.wrapper.UserWrapper;
import com.generic.um.restconsumer.IOtpRestConsumer;
import com.generic.um.service.IUserService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/unauth/user")
public class UserRestImpl  {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(UserRestImpl.class);
	
	@Autowired
	IUserService service;
	
	@Autowired
	IOtpRestConsumer otpRestConsumer;
	
	
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
	public String getMessage(@RequestHeader Map<String,Object> headers){
//		return otpRestConsumer.getMessage(headers.get(IGenericConstants.AUTH_HEADER).toString());
		return otpRestConsumer.getMessage();
	}
	
	@PostMapping("/create")
	public User insert( @RequestBody User entity)  {
			return service.insert(entity);
	}
	
	
}
