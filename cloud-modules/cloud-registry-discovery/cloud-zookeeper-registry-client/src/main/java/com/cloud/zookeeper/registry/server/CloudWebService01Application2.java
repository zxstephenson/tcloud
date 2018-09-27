package com.cloud.zookeeper.registry.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudWebService01Application2 {

	public static void main(String[] args) {
	    args = new String[]{"--server.port=8005"};
		SpringApplication.run(CloudWebService01Application2.class, args);
	}
}
