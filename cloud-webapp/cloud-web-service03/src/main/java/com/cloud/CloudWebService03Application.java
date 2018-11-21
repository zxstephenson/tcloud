package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudWebService03Application {

	public static void main(String[] args) {
	    /*args = new String[]{"--server.port=9002"};*/
		SpringApplication.run(CloudWebService03Application.class, args);
	}
}
