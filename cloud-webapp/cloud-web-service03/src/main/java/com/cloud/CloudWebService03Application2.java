package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudWebService03Application2 {

	public static void main(String[] args) {
	    args = new String[]{"--server.port=9222"};
		SpringApplication.run(CloudWebService03Application2.class, args);
	}
}
