package ar.com.globallogic.challenge.dto;

import java.util.HashSet;
import java.util.Set;

public class UserSignUpRequestDto {

	private String name;
	private String email;
	private String password;
	private Set<PhoneDto> phones = new HashSet<PhoneDto>();

	public UserSignUpRequestDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<PhoneDto> getPhones() {
		return phones;
	}

	public void setPhones(Set<PhoneDto> phones) {
		this.phones = phones;
	}

}
