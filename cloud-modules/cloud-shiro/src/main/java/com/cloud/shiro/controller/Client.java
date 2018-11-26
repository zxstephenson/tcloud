package com.cloud.shiro.controller;

import com.cloud.shiro.utils.PasswordUtil;

public class Client {

	public static void main(String[] args) {
		String password = PasswordUtil.getEncryptPasswordHex(
				"admin", "827d722d8baa4b37a9484578345077d5");
		System.out.println("password = " + password);
	}
}
