package sa52.team03.adproject.scheduler;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import sa52.team03.adproject.controller.LecturerController;

public class schedule1 extends TimerTask{
	
	@Autowired
	public LecturerController lcontrol;
	
	public void run() {
		
		try {
			lcontrol.saveAttendance();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

}
