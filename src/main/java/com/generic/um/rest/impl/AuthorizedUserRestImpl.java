package com.generic.um.rest.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generic.core.constants.IGenericConstants;
import com.generic.core.model.User;
import com.generic.core.rest.impl.GenericRestImpl;
import com.generic.um.rest.IAuthorizedUserRest;
import com.generic.um.service.IUserService;

@RestController
@RequestMapping("/auth/user")
public class AuthorizedUserRestImpl extends GenericRestImpl<User, Integer> implements IAuthorizedUserRest,IGenericConstants {
	
	@Autowired
	IUserService service;
	

	@PostConstruct
	public void setService(){
		super.setService(service);
	}

	@Override
	@GetMapping("/enableUser/{userId}/{flag}")
	public String enableUser(@PathVariable("userId") Integer userId, @PathVariable("flag")Boolean flag) {
		service.enableUser(userId, flag);
		return SUCCESS_JSON;
	}

	@Override
	@GetMapping("/changeAdminStatus/{userId}/{flag}")
	public String changeAdminStatus(@PathVariable("userId")Integer userId, @PathVariable("flag") Boolean flag) {
		service.changeAdminStatus(userId, flag);
		return SUCCESS_JSON;
	}
	
	@GetMapping("/logout")
	public String logout() {
		return service.logout();
	}
	
	@GetMapping("/getMessage")
	public String getMessage(@RequestHeader Map<String,Object> headers){
//		return otpRestConsumer.getMessage(headers.get(IGenericConstants.AUTH_HEADER).toString());
		return null;
	}
	
	@GetMapping("/getUserMessage")
	@Override
	public String getUserMessage(@RequestHeader Map<String,Object> headers){
		return "Sample Message";
	}
	
	

}
