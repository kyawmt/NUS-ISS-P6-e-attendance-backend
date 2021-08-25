package sa52.team03.adproject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EAttendanceBackendApplication {	

	public static void main(String[] args) {
		SpringApplication.run(EAttendanceBackendApplication.class, args);
	}	
	
}


