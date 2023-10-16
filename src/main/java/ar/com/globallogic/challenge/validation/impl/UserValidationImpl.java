package ar.com.globallogic.challenge.validation.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ar.com.globallogic.challenge.dto.PhoneDto;
import ar.com.globallogic.challenge.dto.UserLoginRequestDto;
import ar.com.globallogic.challenge.dto.UserSignUpRequestDto;
import ar.com.globallogic.challenge.exception.ValidationException;
import ar.com.globallogic.challenge.model.User;
import ar.com.globallogic.challenge.repository.UserRepository;
import ar.com.globallogic.challenge.security.JwtUtils;
import ar.com.globallogic.challenge.validation.UserValidation;

@Service
public class UserValidationImpl implements UserValidation {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtils jwtUtils;

	private static final String EMAIL_REGEXP_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	private static final String PASSWORD_REGEXP_PATTERN = "^(?=.*[A-Z].*)(?=.*\\d.*\\d.*)(?!.*[A-Z].*[A-Z])(?!.*\\d.*\\d.*\\d)([A-Za-z\\d]{8,12})$";

	@Override
	public void validateSignUp(UserSignUpRequestDto userSignUpRequestDto) {
		Set<String> errores = new HashSet<String>();

		String email = userSignUpRequestDto.getEmail();
		if (!StringUtils.hasText(email)) {
			errores.add("El Email es obligatorio");
		} else {
			if (!patternMatches(email, EMAIL_REGEXP_PATTERN)) {
				errores.add("El Email no tiene un formato valido");
			} else {
				User user = userRepository.findByEmail(email).orElse(null);
				if (user != null) {
					errores.add("El Email ya esta registrado a un usuario");
				}
			}
		}

		String password = userSignUpRequestDto.getPassword();
		if (!StringUtils.hasText(password)) {
			errores.add("La contraseña es obligatoria");
		} else {
			if (!patternMatches(password, PASSWORD_REGEXP_PATTERN)) {
				errores.add("La contraseña no tiene un formato valido");
			}
		}

		Set<PhoneDto> phones = userSignUpRequestDto.getPhones();
		Boolean telefonoSinDatos = phones.stream().anyMatch(
				p -> p.getCitycode() == null || p.getNumber() == null || !StringUtils.hasText(p.getCountrycode()));
		if (telefonoSinDatos) {
			errores.add("Hay telefonos sin datos completos");
		}

		if (!errores.isEmpty()) {
			throw new ValidationException(errores);
		}
	}

	@Override
	public void validateLogin(UserLoginRequestDto userLoginRequestDto) {
		Set<String> errores = new HashSet<String>();

		String token = userLoginRequestDto.getToken();
		if (!StringUtils.hasText(token)) {
			errores.add("El Token es obligatorio");
		} else {
			String id = jwtUtils.getUserIdFromJwtToken(token);
			if (id == null) {
				errores.add("No se encontró el ID del usuario");
			} else {
				User user = userRepository.findById(id).orElse(null);
				if (user == null) {
					errores.add("No se encontró el usuario");
				} else if (!user.getIsActive()) {
					errores.add("El usuario no se encuentra activo");
				}
			}
		}

		if (!errores.isEmpty()) {
			throw new ValidationException(errores);
		}
	}

	private boolean patternMatches(String text, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(text).matches();
	}
}
