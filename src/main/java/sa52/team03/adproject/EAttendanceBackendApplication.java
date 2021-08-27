package sa52.team03.adproject;

import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sa52.team03.adproject.scheduler.Schedule2;
import sa52.team03.adproject.scheduler.schedule1;
import sa52.team03.adproject.service.AdminService;
import sa52.team03.adproject.service.LecturerService;

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

			TimerTask s1 = new TimerTask() {

				@Override
				public void run() {
					try {
						List<Integer> classid = lecturerService.getallClassID();
						for (Integer i : classid)
							adminService.updateClassPredictedAttendanceRate(i);
					} catch (Exception e) {
						System.out.println(e);
					}
				}

			};

//		Schedule2 s2 = new Schedule2();

			timer.scheduleAtFixedRate(s1, 0, 600000);
//		timer.scheduleAtFixedRate(s2, 0, 600000);

		};
	}

}
