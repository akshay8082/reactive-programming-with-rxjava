package com.example.observable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ObservableApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObservableApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}

@RestController
class NameController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/greet")
	public String greet() {
		Observable<String> nameObservable = Observable.fromCallable(this::getNameFromMS)
				.subscribeOn(Schedulers.newThread());
		Observable<String> greetingObservable = Observable.fromCallable(this::getGreetingFromMs)
				.subscribeOn(Schedulers.newThread());
		String response = Observable.zip(nameObservable, greetingObservable, this::merge).blockingFirst();
		return response;
	}

	private String merge(String nameObservable, String greetingObservable) {
		return "Hello " + nameObservable + ", " + greetingObservable;
	}

	private String getNameFromMS() {
		return restTemplate.getForEntity("http://localhost:5050/getName", String.class).getBody();

	}

	private String getGreetingFromMs() {
		return restTemplate.getForEntity("http://localhost:6060/getGreeting", String.class).getBody();
	}

}
