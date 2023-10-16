package ar.com.globallogic.challenge.validation;

import ar.com.globallogic.challenge.dto.UserLoginRequestDto;
import ar.com.globallogic.challenge.dto.UserSignUpRequestDto;

public interface UserValidation {

	void validateLogin(UserLoginRequestDto userLoginRequestDto);

	void validateSignUp(UserSignUpRequestDto userSignUpRequestDto);

}
