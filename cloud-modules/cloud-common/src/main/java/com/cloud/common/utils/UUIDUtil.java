package com.cloud.common.utils;

import java.util.UUID;

public class UUIDUtil {

	/**
	 * 生成UUID
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
}
