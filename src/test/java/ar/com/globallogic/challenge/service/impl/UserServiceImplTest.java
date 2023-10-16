package ar.com.globallogic.challenge.service.impl;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.com.globallogic.challenge.dto.PhoneDto;
import ar.com.globallogic.challenge.dto.UserLoginRequestDto;
import ar.com.globallogic.challenge.dto.UserLoginResponseDto;
import ar.com.globallogic.challenge.dto.UserSignUpRequestDto;
import ar.com.globallogic.challenge.dto.UserSignUpResponseDto;
import ar.com.globallogic.challenge.model.Phone;
import ar.com.globallogic.challenge.model.User;
import ar.com.globallogic.challenge.repository.UserRepository;
import ar.com.globallogic.challenge.security.JwtUtils;
import ar.com.globallogic.challenge.validation.UserValidation;

@SpringBootTest(properties = {"spring.config.name=test", "spring.config.location=classpath:/"})
public class UserServiceImplTest {

	@MockBean
	private UserValidation userValidation;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private JwtUtils jwtUtils;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void signUp() {
		String email = "algo@algo.com";
		String name = "name";
		String password = "String12";
		String passwordEncrypted = "passwordEncrypted";
		String token = "token";

		UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();
		userSignUpRequestDto.setEmail(email);
		userSignUpRequestDto.setName(name);
		userSignUpRequestDto.setPassword(password);

		Integer citycode = 351;
		String countrycode = "AR";
		Long number = 1234567L;
		PhoneDto phoneDto = new PhoneDto();
		phoneDto.setCitycode(citycode);
		phoneDto.setCountrycode(countrycode);
		phoneDto.setNumber(number);

		userSignUpRequestDto.getPhones().add(phoneDto);

		Mockito.doNothing().when(userValidation).validateSignUp(userSignUpRequestDto);
		Mockito.when(this.passwordEncoder.encode(password)).thenReturn(passwordEncrypted);
		Mockito.when(this.jwtUtils.generateJwtToken(Mockito.any())).thenReturn(token);

		UserSignUpResponseDto userSignUpResponseDto = userServiceImpl.signUp(userSignUpRequestDto);

		Assertions.assertEquals(userSignUpResponseDto.getIsActive(), true);
		Assertions.assertEquals(userSignUpResponseDto.getToken(), token);
		Assertions.assertNotNull(userSignUpResponseDto.getCreated());
		Assertions.assertNotNull(userSignUpResponseDto.getId());
		Assertions.assertNull(userSignUpResponseDto.getLastLogin());
	}

	@Test
	public void loginTest() {
		String token = "token";
		String id = "id";
		Date created = new Date();
		String newToken = "newToken";
		Boolean isActive = true;
		String name = "name";
		String email = "algo@algo.com";
		String password = "password";

		User user = new User();
		user.setId(id);
		user.setCreated(created);
		user.setIsActive(isActive);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);

		Integer citycode = 351;
		String countrycode = "AR";
		Long number = 1234567L;
		Phone phone = new Phone();
		phone.setCitycode(citycode);
		phone.setCountrycode(countrycode);
		phone.setNumber(number);

		user.getPhones().add(phone);

		UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setToken(token);

		Mockito.doNothing().when(userValidation).validateLogin(userLoginRequestDto);
		Mockito.when(this.jwtUtils.getUserIdFromJwtToken(token)).thenReturn(id);
		Mockito.when(this.userRepository.getReferenceById(id)).thenReturn(user);
		Mockito.when(this.jwtUtils.generateJwtToken(id)).thenReturn(newToken);

		UserLoginResponseDto userLoginResponseDto = userServiceImpl.login(userLoginRequestDto);

		Assertions.assertEquals(userLoginResponseDto.getIsActive(), isActive);
		Assertions.assertEquals(userLoginResponseDto.getToken(), newToken);
		Assertions.assertEquals(userLoginResponseDto.getCreated(), created);
		Assertions.assertEquals(userLoginResponseDto.getId(), id);
		Assertions.assertEquals(userLoginResponseDto.getName(), name);
		Assertions.assertEquals(userLoginResponseDto.getEmail(), email);
		Assertions.assertEquals(userLoginResponseDto.getPassword(), password);
		Assertions.assertNotNull(userLoginResponseDto.getLastLogin());
	}
}
