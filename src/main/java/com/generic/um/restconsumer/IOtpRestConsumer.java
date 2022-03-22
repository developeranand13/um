package com.generic.um.restconsumer;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import feign.HeaderMap;

@FeignClient(name = "Notification-Service",url = "http://localhost:8083/")
public interface IOtpRestConsumer {
	
	@GetMapping("/unauth/otp/verifyOtp/{email}/{otp}")
	public boolean verifyOtp(@PathVariable("email")String email, @PathVariable("otp")String otp);

	@GetMapping("/auth/otp/getMessage")
//	@Headers("csrfToken : {csrfToken}")
	public String getMessage(@RequestHeader(name = "csrfToken") String csrfToken);
	
	@GetMapping("/auth/otp/getMessage")
//	@Headers("csrfToken : {csrfToken}")
	public String getMessage();

}
