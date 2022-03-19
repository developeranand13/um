package com.generic.um.restconsumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Notification-Service",url = "http://localhost:8083/")
public interface IOtpRestConsumer {
	
	@GetMapping("/unauth/otp/verifyOtp/{email}/{otp}")
	public boolean verifyOtp(@PathVariable("email")String email, @PathVariable("otp")String otp);

	@GetMapping("/unauth/otp/getMessage")
	public String getMessage();

}
