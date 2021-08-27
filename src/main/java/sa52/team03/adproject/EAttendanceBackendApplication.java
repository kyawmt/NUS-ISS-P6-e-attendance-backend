package sa52.team03.adproject;

import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sa52.team03.adproject.scheduler.Schedule2;
import sa52.team03.adproject.scheduler.schedule1;



@SpringBootApplication
public class EAttendanceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAttendanceBackendApplication.class, args);
		
		Timer timer = new Timer();
		schedule1 s1 = new schedule1();
		Schedule2 s2 = new Schedule2();
		timer.scheduleAtFixedRate(s1,0, 600000);
		timer.scheduleAtFixedRate(s2, 0, 600000);
	}

}
