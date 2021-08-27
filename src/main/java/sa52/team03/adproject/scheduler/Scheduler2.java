package sa52.team03.adproject.scheduler;

import java.util.List;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import sa52.team03.adproject.service.AdminService;
import sa52.team03.adproject.service.LecturerService;

public class Scheduler2 extends TimerTask {
	
	@Autowired
	LecturerService lecturerService;
	
	@Autowired
	AdminService adminService;
	
	public void run() {
		try {
			List<Integer> classIDs = lecturerService.getallClassID();
			for (Integer i : classIDs)
				adminService.updateClassPredictedAttendanceRate(i);
		}
		catch (Exception e) {
			System.out.println("Error happened");
		}
	}

}
