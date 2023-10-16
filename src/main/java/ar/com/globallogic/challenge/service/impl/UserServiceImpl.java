package ar.com.globallogic.challenge.service.impl;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.globallogic.challenge.dto.PhoneDto;
import ar.com.globallogic.challenge.dto.UserLoginRequestDto;
import ar.com.globallogic.challenge.dto.UserLoginResponseDto;
import ar.com.globallogic.challenge.dto.UserSignUpRequestDto;
import ar.com.globallogic.challenge.dto.UserSignUpResponseDto;
import ar.com.globallogic.challenge.model.Phone;
import ar.com.globallogic.challenge.model.User;
import ar.com.globallogic.challenge.repository.UserRepository;
import ar.com.globallogic.challenge.security.JwtUtils;
import ar.com.globallogic.challenge.service.UserService;
import ar.com.globallogic.challenge.validation.UserValidation;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public UserSignUpResponseDto signUp(UserSignUpRequestDto userSignUpRequestDto) {
		this.userValidation.validateSignUp(userSignUpRequestDto);

		User user = this.getUserFromSignUpRequesDto(userSignUpRequestDto);

		this.userRepository.save(user);

		return this.getUserSignUpResponseDtoFromUser(user);
	}

	@Override
	public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
		this.userValidation.validateLogin(userLoginRequestDto);

		String id = jwtUtils.getUserIdFromJwtToken(userLoginRequestDto.getToken());

		User user = this.userRepository.getReferenceById(id);

		user.setLastLogin(new Date());

		this.userRepository.save(user);

		return this.getUserLoginResponseDtoFromUser(user);
	}

	private User getUserFromSignUpRequesDto(UserSignUpRequestDto userSignUpRequestDto) {
		User user = new User();

		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		user.setId(id);

		user.setName(userSignUpRequestDto.getName());
		user.setEmail(userSignUpRequestDto.getEmail());
		user.setPassword(passwordEncoder.encode(userSignUpRequestDto.getPassword()));
		user.setPhones(
				userSignUpRequestDto.getPhones().stream().map(this::getPhoneFromPhoneDto).collect(Collectors.toSet()));
		user.getPhones().stream().forEach(p -> p.setUser(user));
		user.setCreated(new Date());
		user.setIsActive(true);

		return user;
	}

	private Phone getPhoneFromPhoneDto(PhoneDto phoneDto) {
		Phone phone = new Phone();

		phone.setNumber(phoneDto.getNumber());
		phone.setCitycode(phoneDto.getCitycode());
		phone.setCountrycode(phoneDto.getCountrycode());

		return phone;
	}

	private PhoneDto getPhoneDtoFromPhone(Phone phone) {
		PhoneDto phoneDto = new PhoneDto();

		phoneDto.setNumber(phone.getNumber());
		phoneDto.setCitycode(phone.getCitycode());
		phoneDto.setCountrycode(phone.getCountrycode());

		return phoneDto;
	}

	private UserSignUpResponseDto getUserSignUpResponseDtoFromUser(User user) {
		UserSignUpResponseDto userSignUpResponseDto = new UserSignUpResponseDto();

		userSignUpResponseDto.setId(user.getId());
		userSignUpResponseDto.setCreated(user.getCreated());
		userSignUpResponseDto.setLastLogin(user.getLastLogin());
		userSignUpResponseDto.setIsActive(user.getIsActive());
		userSignUpResponseDto.setToken(this.jwtUtils.generateJwtToken(user.getId()));

		return userSignUpResponseDto;
	}

	private UserLoginResponseDto getUserLoginResponseDtoFromUser(User user) {
		UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();

		userLoginResponseDto.setId(user.getId());
		userLoginResponseDto.setCreated(user.getCreated());
		userLoginResponseDto.setLastLogin(user.getLastLogin());
		userLoginResponseDto.setToken(this.jwtUtils.generateJwtToken(user.getId()));
		userLoginResponseDto.setIsActive(user.getIsActive());
		userLoginResponseDto.setName(user.getName());
		userLoginResponseDto.setEmail(user.getEmail());
		userLoginResponseDto.setPassword(user.getPassword());
		userLoginResponseDto
				.setPhones(user.getPhones().stream().map(this::getPhoneDtoFromPhone).collect(Collectors.toSet()));

		return userLoginResponseDto;
	}
}
