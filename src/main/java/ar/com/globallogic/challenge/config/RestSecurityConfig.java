package ar.com.globallogic.challenge.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.com.globallogic.challenge.exception.RestResponseEntityExceptionHandler;

@Configuration
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class RestSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected ResponseEntityExceptionHandler restxceptionHandler() {
		return new RestResponseEntityExceptionHandler();
	}
}
