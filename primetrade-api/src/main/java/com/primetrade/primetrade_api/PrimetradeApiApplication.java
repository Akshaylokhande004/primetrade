package com.primetrade.primetrade_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class PrimetradeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimetradeApiApplication.class, args);
	}



}
