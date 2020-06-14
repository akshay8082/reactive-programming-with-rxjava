package com.example.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

}


@RestController
class RestAPIController {

	@GetMapping("/getName")
	public String getName(){
		return "Peter Parker";
	}

	@GetMapping("/getGreeting")
	public String getGreeting(){
		return "Good morning";
	}
}
