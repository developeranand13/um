package com.generic.um.service.impl;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generic.core.exception.BusinessException;
import com.generic.core.model.User;
import com.generic.core.model.User.UserRole;
import com.generic.core.model.User.UserType;
import com.generic.core.security.TokenHelper;
import com.generic.core.service.impl.GenericServiceImpl;
import com.generic.core.utils.AESEncryptor;
import com.generic.core.wrapper.UserWrapper;
import com.generic.um.dao.IUserDao;
import com.generic.um.service.IUserService;

import ch.qos.logback.classic.Logger;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Integer> implements IUserService {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	IUserDao dao;

	@Autowired
	AESEncryptor pwEncoder;
	
	@Autowired
	TokenHelper tokenHelper;

	@PostConstruct
	public void setService() {
		super.setCrudRepository(dao);
	}

	@Override
	public User findByUserName(String username) {
		return dao.findUserByUserName(username);
	}

	@Override
	public Boolean isUsernameExists(String userName) {
		return dao.isUsernameExists(userName);
	}

	@Override
	public User findUserByUserName(String userName) {
		return dao.findUserByUserName(userName);
	}

	@Override
	public User findUserByUserNameAndType(String userName, String userType) {
		return dao.findUserByUserNameAndType(userName, userType);
	}

	@Override
	public List<User> getBirthdayUserList() {
		return dao.getBirthdayUserList();
	}

	@Override
	public UserWrapper verifyAndCreateSocialMediaUser(UserWrapper userWrapper) {
		if (UserType.GOOGLE.name().equalsIgnoreCase(userWrapper.getUserType())) {
		    handleGoogleUser(userWrapper);
		}
		return userWrapper;
	}

	@Override
	public UserWrapper isValidUser(User user) {
		user.setPassword(pwEncoder.encode(user.getPassword()));
		User userFromDB = dao.isValidUser(user.getUserName(), user.getPassword());
		if (userFromDB != null) {
			if (Boolean.FALSE.equals(userFromDB.getEnabled())) {
				throw new BusinessException("User disabled", "User disabled. Please contact administrator");
			}
			userFromDB.setLoginStatus(true);
			dao.save(userFromDB);
			return getUserWrapperFromUser(userFromDB);
		} else {
			throw new BusinessException(null, "Invalid username and password");
		}
	}

	@Override
	public String updatePasswordByOTPVerification(String emailId, String otp, String password) {
		return null;
	}

	@Override
	public String forgotPassword(User user) {
		return null;
	}

	@Override
	public User verifyOTPAndCreateUser(User entity, String otp, String email) {
		return null;
	}
	
	
	private UserWrapper getUserWrapperFromUser(User userFromDB) {
		UserWrapper wrapper = new UserWrapper();
		wrapper.setUserName(userFromDB.getUserName());
		wrapper.setContactNumber(userFromDB.getContactNumber());
		wrapper.setId(userFromDB.getId());
		wrapper.setEmailId(userFromDB.getEmailId());
		wrapper.setEnabled(userFromDB.getEnabled());
		wrapper.setName(userFromDB.getFirstName() +" "+userFromDB.getLastName());
		wrapper.setToken(tokenHelper.generateToken(userFromDB));
		wrapper.setUserType(userFromDB.getUserType().name());
		wrapper.setRole(userFromDB.getRole().name());
		wrapper.setDateOfBirth(userFromDB.getDateOfBirth());
		wrapper.setAniversary(userFromDB.getAniversary());
		wrapper.setProfilePicPath(userFromDB.getProfilePicPath());
		return wrapper;
	}
	
	private void handleGoogleUser(UserWrapper userWrapper) {
		User user = dao.findUserByUserNameAndType(userWrapper.getUserName(), userWrapper.getUserType());
		if (user == null) {
			user = new User();
			user.setUserType(UserType.GOOGLE);
			user.setRole(UserRole.ROLE_USER);
			user.setEmailId(userWrapper.getEmailId());
			user.setUserName(userWrapper.getUserName());
			user.setFirstName(userWrapper.getFirstName());
			user.setLastName(userWrapper.getLastName());
			user.setProfilePicPath(userWrapper.getProfilePicPath());
			user = dao.save(user);
		} else if (Boolean.FALSE.equals(user.getEnabled())) {
			throw new BusinessException("User disabled", "User disabled. Please contact administrator");
		}
		// userWrapper = getUserWrapperFromUser(user);
		userWrapper.setId(user.getId());
		userWrapper.setProfilePicPath(user.getProfilePicPath());
		logger.info("Profile pic path :" + user.getProfilePicPath());
		userWrapper.setContactNumber(user.getContactNumber());
		userWrapper.setDateOfBirth(user.getDateOfBirth());
		userWrapper.setAniversary(user.getAniversary());
		userWrapper.setContactNumber(user.getContactNumber());
		userWrapper.setToken(tokenHelper.generateToken(user));
		userWrapper.setRole(user.getRole().name());

	}


	public void changeAdminStatus(Integer userId, Boolean flag) {
		User user = dao.findById(userId).get();
		user.setRole(flag ? UserRole.ROLE_ADMIN : UserRole.ROLE_USER);
		dao.save(user);
	}


	public void uploadProfilePic(InputStream file, String fileName) {
		// TODO Auto-generated method stub
		
	}

}
