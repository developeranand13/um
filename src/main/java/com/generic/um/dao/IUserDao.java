package com.generic.um.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generic.core.model.User;

@Repository
public interface IUserDao extends CrudRepository<User, Integer>{
	
	public List<User> findByFirstName(String firstName);
	
	public List<User> findByLastName(String lastName);
	
	@Query("select u from User u where u.userName = :userName and u.password = :passWord")
	public User isValidUser(@Param("userName")String userName,@Param("passWord")String passWord);

	@Query("select new java.lang.Boolean(count(*) > 0) from User u  where u.userName = :userName ")
	public Boolean isUsernameExists(@Param("userName") String userName);

	@Query("select u from User u where u.userName = :userName")
	public User findUserByUserName(@Param("userName") String userName);

	@Query("select u from User u where u.userName = :userName and u.userType = :userType ")
	public User findUserByUserNameAndType(@Param("userName") String userName, @Param("userType")String userType);
	
	@Query("select u from User u where DATE(u.dateOfBirth) = DATE(NOW())")
	public List<User> getBirthdayUserList();

}
