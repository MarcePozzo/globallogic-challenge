package ar.com.globallogic.challenge.controller;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.globallogic.challenge.dto.UserLoginRequestDto;
import ar.com.globallogic.challenge.dto.UserLoginResponseDto;
import ar.com.globallogic.challenge.dto.UserSignUpRequestDto;
import ar.com.globallogic.challenge.dto.UserSignUpResponseDto;
import ar.com.globallogic.challenge.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/sign-up")
	@PermitAll
	public UserSignUpResponseDto signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
		return userService.signUp(userSignUpRequestDto);
	}

	@PostMapping("/login")
	public UserLoginResponseDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
		return userService.login(userLoginRequestDto);
	}
}
