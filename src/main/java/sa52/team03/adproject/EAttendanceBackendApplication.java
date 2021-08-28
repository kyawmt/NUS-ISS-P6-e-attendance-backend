package sa52.team03.adproject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import sa52.team03.adproject.service.AdminService;
import sa52.team03.adproject.service.LecturerService;
import sa52.team03.adproject.domain.Class;

@SpringBootApplication
public class EAttendanceBackendApplication {

	@Autowired
	public LecturerService lecturerService;

	@Autowired
	public AdminService adminService;

	public static void main(String[] args) {
		SpringApplication.run(EAttendanceBackendApplication.class, args);

	}

	@Bean
	CommandLineRunner runner() {
		return args -> {

			Timer timer = new Timer();			
			TimerTask s2 = new TimerTask() {
				
				@Override 
				public void run() {
					try {
						List<Integer> classid = adminService.classID();
						if (classid != null) {
							for (Integer i : classid) {
								adminService.updateClassPredictedAttendanceRate(i);
						}
					}
					
					}  catch (Exception e) {
						System.out.println(e);
					}
					
				}
			};
			timer.scheduleAtFixedRate(s2, 0, 600000);

		};
	}

}
