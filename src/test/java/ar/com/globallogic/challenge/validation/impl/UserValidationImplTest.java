package ar.com.globallogic.challenge.validation.impl;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ar.com.globallogic.challenge.dto.PhoneDto;
import ar.com.globallogic.challenge.dto.UserLoginRequestDto;
import ar.com.globallogic.challenge.dto.UserSignUpRequestDto;
import ar.com.globallogic.challenge.exception.ValidationException;
import ar.com.globallogic.challenge.model.Phone;
import ar.com.globallogic.challenge.model.User;
import ar.com.globallogic.challenge.repository.UserRepository;
import ar.com.globallogic.challenge.security.JwtUtils;

@SpringBootTest(properties = {"spring.config.name=test", "spring.config.location=classpath:/"})
public class UserValidationImplTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private JwtUtils jwtUtils;

	@InjectMocks
	private UserValidationImpl userValidationImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void signUp_NoErrors() {
		String email = "algo@algo.com";
		String name = "name";
		String password = "String12";

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

		Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());

		userValidationImpl.validateSignUp(userSignUpRequestDto);
	}

	@Test
	public void signUp_NoEmail() {
		String email = "";
		String name = "name";
		String password = "String12";

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

		Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());

		Assertions.assertThrows(ValidationException.class,
				() -> userValidationImpl.validateSignUp(userSignUpRequestDto));
	}

	@Test
	public void signUp_InvalidEmail() {
		String email = "algo@algo";
		String name = "name";
		String password = "String12";

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

		Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());

		Assertions.assertThrows(ValidationException.class,
				() -> userValidationImpl.validateSignUp(userSignUpRequestDto));
	}

	@Test
	public void signUp_EmailExists() {
		String email = "algo@algo.com";
		String name = "name";
		String password = "String12";

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

		User user = new User();

		userSignUpRequestDto.getPhones().add(phoneDto);

		Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		Assertions.assertThrows(ValidationException.class,
				() -> userValidationImpl.validateSignUp(userSignUpRequestDto));
	}

	@Test
	public void signUp_NoPassword() {
		String email = "algo@algo.com";
		String name = "name";
		String password = "";

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

		Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());

		Assertions.assertThrows(ValidationException.class,
				() -> userValidationImpl.validateSignUp(userSignUpRequestDto));
	}

	@Test
	public void signUp_InvalidPassword() {
		String email = "algo@algo.com";
		String name = "name";
		String password = "String123S";

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

		Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());

		Assertions.assertThrows(ValidationException.class,
				() -> userValidationImpl.validateSignUp(userSignUpRequestDto));
	}

	@Test
	public void signUp_UncompletePhones() {
		String email = "algo@algo.com";
		String name = "name";
		String password = "String123";

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
		PhoneDto phoneDto2 = new PhoneDto();

		userSignUpRequestDto.getPhones().add(phoneDto2);

		Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());

		Assertions.assertThrows(ValidationException.class,
				() -> userValidationImpl.validateSignUp(userSignUpRequestDto));
	}

	@Test
	public void validateLoginTest_NoErrors() {
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

		Mockito.when(this.jwtUtils.getUserIdFromJwtToken(token)).thenReturn(id);
		Mockito.when(this.userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(this.jwtUtils.generateJwtToken(id)).thenReturn(newToken);

		userValidationImpl.validateLogin(userLoginRequestDto);
	}

	@Test
	public void validateLoginTest_NoToken() {
		String token = "";
		String id = "id";
		Date created = new Date();
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

		Assertions.assertThrows(ValidationException.class, () -> userValidationImpl.validateLogin(userLoginRequestDto));
	}

	@Test
	public void validateLoginTest_NoId() {
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

		Mockito.when(this.jwtUtils.getUserIdFromJwtToken(token)).thenReturn(null);

		Assertions.assertThrows(ValidationException.class, () -> userValidationImpl.validateLogin(userLoginRequestDto));
	}

	@Test
	public void validateLoginTest_UserNotFound() {
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

		Mockito.when(this.jwtUtils.getUserIdFromJwtToken(token)).thenReturn(id);
		Mockito.when(this.userRepository.findById(id)).thenReturn(Optional.empty());

		Assertions.assertThrows(ValidationException.class, () -> userValidationImpl.validateLogin(userLoginRequestDto));
	}

	@Test
	public void validateLoginTest_UserNotActive() {
		String token = "token";
		String id = "id";
		Date created = new Date();
		String newToken = "newToken";
		Boolean isActive = false;
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

		Mockito.when(this.jwtUtils.getUserIdFromJwtToken(token)).thenReturn(id);
		Mockito.when(this.userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(this.jwtUtils.generateJwtToken(id)).thenReturn(newToken);

		Assertions.assertThrows(ValidationException.class, () -> userValidationImpl.validateLogin(userLoginRequestDto));
	}
}
