package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The application’s bootstrap class and primary Spring configuration class 
 * 1.configuration and bootstrapping
 * @Configuration—Designates a class as a configuration class using Spring’s Java-based configuration. Although we won’t be writing
 * a lot of configuration in this book, we’ll favor Java-based configuration over XML configuration when we do. 
 * @ComponentScan—Enables component-scanning so that the web controller classes and other components you write will be
 * automatically discovered and registered as beans in the Spring application context. A little later in this chapter, we’ll write a simple
 * Spring MVC controller that will be annotated with @Controller so that component-scanning can find it. 
 * @EnableAutoConfiguration—This humble little annotation might as well be named @Abracadabra because it’s the one line of
 * configuration that enables the magic of Spring Boot auto-configuration. This one line keeps you from having to write the pages of
 * configuration that would be required otherwise
 * @author User
 *
 */
@SpringBootApplication // Enable component scanning auto-configuration
public class SpringBootInActionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootInActionApplication.class, args);// Bootstrap the application
	}

}
