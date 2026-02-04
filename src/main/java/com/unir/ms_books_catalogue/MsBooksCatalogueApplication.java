package com.unir.ms_books_catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.unir.ms_books_catalogue.controller",
		"com.unir.ms_books_catalogue.service",
		"com.unir.ms_books_catalogue"
})
@EnableDiscoveryClient
public class MsBooksCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBooksCatalogueApplication.class, args);
	}

}
