package cmc.surfmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SurfmateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurfmateApplication.class, args);
	}

}
