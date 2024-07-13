package saphy.saphy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SaphyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaphyApplication.class, args);
	}

}
