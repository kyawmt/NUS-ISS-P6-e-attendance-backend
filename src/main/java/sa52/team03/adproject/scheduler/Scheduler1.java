package sa52.team03.adproject.scheduler;

import java.util.List;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import sa52.team03.adproject.service.LecturerService;

public class Scheduler1 extends TimerTask{
	
	@Autowired 
	LecturerService lecturerService;
	
	public void run() {
		try {
			List<Integer> classIDs = lecturerService.getallClassID();
			for (Integer i : classIDs)
				lecturerService.savePrediction(i);
		}
		catch (Exception e) {
			System.out.println("Error happened");
		}
	}
}
