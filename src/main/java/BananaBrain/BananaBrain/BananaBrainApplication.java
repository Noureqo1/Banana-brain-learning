package BananaBrain.BananaBrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BananaBrainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BananaBrainApplication.class, args);
	}

	// REST Controller to handle web requests
	@RestController
	class HelloWorldController {

		@GetMapping("/")
		public String helloWorld() {
			return "Hello, Noureqo!";
		}
	}
}
