package com.generic.um.service;

import java.util.List;

import com.generic.core.model.User;
import com.generic.core.service.IGenericService;
import com.generic.core.wrapper.UserWrapper;

public interface IUserService extends IGenericService<User, Integer> {

	User findByUserName(String username);
	
	public Boolean isUsernameExists( String userName);

	public User findUserByUserName( String userName);

	public User findUserByUserNameAndType( String userName, String userType);
	
	public List<User> getBirthdayUserList();

	UserWrapper verifyAndCreateSocialMediaUser(UserWrapper user);

	UserWrapper isValidUser(User user);

	String updatePasswordByOTPVerification(String emailId, String otp, String password);

	String forgotPassword(User user);

	User verifyOTPAndCreateUser(User entity, String otp, String email);

}
