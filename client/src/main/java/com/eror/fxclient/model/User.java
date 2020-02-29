package com.eror.fxclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

    private long id;

    private String name;

    private String surname;

    private String username;

    private String email;

    private String password;

    private Role role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

//	@Override
//	public String toString() {
//		return "User{" +
//				"id=" + id +
//				", firstName='" + firstName + '\'' +
//				", lastName='" + lastName + '\'' +
//				", dob=" + dob +
//				", gender='" + gender + '\'' +
//				", role=" + role.getName().toString() +
//				", email='" + email + '\'' +
//				", password='" + password + '\'' +
//				'}';
//	}

	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
