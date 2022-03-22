package com.generic.um.restconsumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "Notification-Service",url = "http://localhost:8083/")
public interface IOtpRestConsumer {
	
	@GetMapping("/auth/otp/verifyOtp/{email}/{otp}")
	public boolean verifyOtp(@RequestHeader(name = "csrfToken") String csrfToken,@PathVariable("email")String email, @PathVariable("otp")String otp);

	@GetMapping("/auth/otp/getMessage")
	public String getMessage(@RequestHeader(name = "csrfToken") String csrfToken);
	
	@GetMapping("/auth/otp/getMessage")
	public String getMessage();

}
