package com.generic.um.service;

import java.io.InputStream;
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

	public UserWrapper verifyAndCreateSocialMediaUser(UserWrapper user);

	public UserWrapper isValidUser(User user);

	public String updatePasswordByOTPVerification(String emailId, String otp, String password);

	public String forgotPassword(User user);

	public User verifyOTPAndCreateUser(User entity, String otp, String email);
	
	public void changeAdminStatus(Integer userId, Boolean flag);

	public void uploadProfilePic(InputStream file, String fileName) ;




}
