package sa52.team03.adproject.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.repo.ClassRepository;
import sa52.team03.adproject.service.AdminService;
import sa52.team03.adproject.service.LecturerService;

public class Scheduler2 extends TimerTask {
	
	@Autowired
	private LecturerService lecturerService;
	
	@Autowired 
	private ClassRepository crepo;
	
	@Autowired
	AdminService adminService;
	
	public void run() {
		
		List<Class> c = crepo.findAll();
		List<Integer> ic = new ArrayList<>();
		for (Class cc : c) {
			ic.add(cc.getId());
		}
		for (Integer i : ic) {
			try {
				adminService.updateClassPredictedAttendanceRate(i);
			}
			catch (Exception e) {
				System.out.println(e);
		}
	}
	}
}


