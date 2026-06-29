package com.techouts.cartController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class CartControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartControllerApplication.class, args);
	}

}
