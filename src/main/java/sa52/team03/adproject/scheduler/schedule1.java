package sa52.team03.adproject.scheduler;

import java.util.List;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import sa52.team03.adproject.service.AdminService;
import sa52.team03.adproject.service.LecturerService;

public class schedule1 extends TimerTask{
	
	@Autowired
	public LecturerService lecturerService;
	
	@Autowired
	public AdminService adminService;
	
	public void run() {
		
		try {
			List<Integer> classid = lecturerService.getallClassID();
			for (Integer i: classid)
				adminService.updateClassPredictedAttendanceRate(i);		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

}
