package com.udacity.jwdnd.c1.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class ReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewApplication.class, args);
	}

	@Bean
	public String message(){
		System.out.println("message");
		return "Hello message";
	}

	@Bean
	public String uppercaseMessage(MessageService service){
		System.out.println("uppercaseMessage");
		return service.uppercase();
	}

	@Bean
	public String lowercaseMessage(MessageService service){
		System.out.println("lowercaseMessage");
		return service.lowercase();
	}

	/***
	 *
	 *   Add @Primary annotation. This will be default implementation
	 *
	 */
	@Bean
	@Primary
	@Qualifier("main")
	public String basicMessage(){
		System.out.println("inside basic message");
		return "Hello";
	}

	/***
	 *
	 *   Add @Qualifier annotation
	 *
	 */

	@Bean
	@Qualifier("other")
	@Autowired
	public String otherMessage(){
		System.out.println("inside other message");
		return "Hello"
				;
	}

	/**
	 *  This method will use Primary message
	 *  because there is no qualifier
	 *
	 */

	@Bean
	public String printMessage(String message){
		System.out.println(message);
		return "Smth";
	}

	/**
	 *  Use @Qualifier to specify withc instance of message to receive
	 *  This method will use @Qualified main
	 *
	 */
	@Bean
	@Autowired
	public String printMessage2(@Qualifier("main") String message){
		System.out.println(message);
		return "Smth";
	}
}
