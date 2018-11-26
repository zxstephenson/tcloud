package com.cloud.shiro.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.cloud.common.constant.Constants;

public class PasswordUtil {

	public static String getEncryptPasswordHex(String oriendPassword, String salt){
		
		SimpleHash simpleHash = new SimpleHash(Constants.ALGORITHM_NAME, oriendPassword,
		            ByteSource.Util.bytes(salt), Constants.HASH_ITERATIONS);
        return simpleHash.toHex();
	}
}
