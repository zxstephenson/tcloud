package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@SpringCloudApplication
public class CloudWebService01Application {

	public static void main(String[] args) {
		SpringApplication.run(CloudWebService01Application.class, args);
	}
}
