package BananaBrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "BananaBrain.model")
public class BananaBrainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BananaBrainApplication.class, args);
	}

}