package com.example.make91.Bookstore.domain;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class SignupForm {
    @NotEmpty
    @Size(min=2, max=32)
    private String username = "";

    @NotEmpty
    @Size(min=5, max=100)
    @Pattern(regexp = ".+@.+[.].+", message = "must contain @ and .")
    private String email = "";
    
    @NotEmpty
    @Size(min=5, max=128)
    private String password = "";

    @NotEmpty
    @Size(min=5, max=128)
    private String passwordCheck = "";

    @NotEmpty
    private String role = "USER";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
}
