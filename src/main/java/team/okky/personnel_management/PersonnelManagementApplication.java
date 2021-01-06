package team.okky.personnel_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PersonnelManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonnelManagementApplication.class, args);
	}

}
