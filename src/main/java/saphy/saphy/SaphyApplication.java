package saphy.saphy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SaphyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaphyApplication.class, args);
		//WebHook 테스트를 위한 수정2
	}

}
