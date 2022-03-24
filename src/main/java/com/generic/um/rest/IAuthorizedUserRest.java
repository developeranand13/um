package com.generic.um.rest;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

public interface IAuthorizedUserRest {
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String enableUser(Integer userId,Boolean flag);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	String changeAdminStatus(Integer userId, Boolean flag);

	@PreAuthorize("hasRole('ROLE_USER')")
	String getUserMessage(Map<String, Object> headers);

}
