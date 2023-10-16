package ar.com.globallogic.challenge.service;

import ar.com.globallogic.challenge.dto.UserLoginRequestDto;
import ar.com.globallogic.challenge.dto.UserLoginResponseDto;
import ar.com.globallogic.challenge.dto.UserSignUpRequestDto;
import ar.com.globallogic.challenge.dto.UserSignUpResponseDto;

public interface UserService {

	UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);

	UserSignUpResponseDto signUp(UserSignUpRequestDto userSignUpRequestDto);

}
