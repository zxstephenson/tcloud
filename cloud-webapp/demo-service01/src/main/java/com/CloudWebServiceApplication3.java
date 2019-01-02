package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudWebServiceApplication3 {

	public static void main(String[] args) {
	    args = new String[]{"--spring.profiles.active=dev3"};
		SpringApplication.run(CloudWebServiceApplication3.class, args);
	}
}
