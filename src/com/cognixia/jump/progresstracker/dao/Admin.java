package com.cognixia.jump.progresstracker.dao;

public class Admin{
private int UserId;
private String username="adminJoe123";
private String password="passwordJoe";
private int roleType=1;

@Override
public String toString() {
	return "Admin [UserId=" + UserId + ", username=" + username + ", password=" + password + ", roleType=" + roleType
			+ "]";
}
public int getUserId() {
	return UserId;
}

public String getUsername() {
	return username;
}

public String getPassword() {
	return password;
}

public int getRoleType() {
	return roleType;
}



}
