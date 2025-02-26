package proyecto1.msreports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "proyecto1.msreports.repository")
public class MsReportsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsReportsApplication.class, args);
	}

}
