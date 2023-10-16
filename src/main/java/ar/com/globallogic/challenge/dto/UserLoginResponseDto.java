package ar.com.globallogic.challenge.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserLoginResponseDto {

	private String id;
	private String name;
	private String email;
	private String password;
	private Date created;
	private Date lastLogin;
	private Boolean isActive;
	private String token;
	private Set<PhoneDto> phones = new HashSet<PhoneDto>();

	public UserLoginResponseDto() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<PhoneDto> getPhones() {
		return phones;
	}

	public void setPhones(Set<PhoneDto> phones) {
		this.phones = phones;
	}

}
