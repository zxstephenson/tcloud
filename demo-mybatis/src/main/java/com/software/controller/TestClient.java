package com.software.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestClient {

	public static void main(String[] args) {
		System.err.println(new BCryptPasswordEncoder().encode("admin"));
	}
}
