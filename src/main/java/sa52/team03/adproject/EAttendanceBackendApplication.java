package sa52.team03.adproject;

import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sa52.team03.adproject.scheduler.Scheduler1;
import sa52.team03.adproject.scheduler.Scheduler2;

@SpringBootApplication
public class EAttendanceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAttendanceBackendApplication.class, args);
		
		Timer timer = new Timer();
		Scheduler1 s1 = new Scheduler1();
		Scheduler2 s2 = new Scheduler2();
		timer.scheduleAtFixedRate(s1,2000, 600000);
		timer.scheduleAtFixedRate(s2, 2000, 600000);
		
	}

}
