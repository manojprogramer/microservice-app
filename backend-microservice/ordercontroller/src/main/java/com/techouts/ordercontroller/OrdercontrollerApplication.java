package com.techouts.ordercontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrdercontrollerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdercontrollerApplication.class, args);
	}

}
