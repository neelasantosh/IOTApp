package com.dto;

import java.io.Serializable;

public class User implements Serializable {
	private int id;
	private String name;
	private String email;
	private String recoveryEmail;
	private String mobile;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String name, String email, String recoveryEmail, String password, String mobile) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.recoveryEmail = recoveryEmail;
		this.mobile = mobile;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", recoveryEmail=" + recoveryEmail
				+ ", mobile=" + mobile + ", password=" + password + "]";
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
	public String getRecoveryEmail() {
		return recoveryEmail;
	}
	public void setRecoveryEmail(String recoveryEmail) {
		this.recoveryEmail = recoveryEmail;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String password;
}
