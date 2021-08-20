package sa52.team03.adproject.service;

import java.util.List;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.StudentLeave;

public interface LecturerService {
	
	public List<Attendance> getListOfClass();
	
	public Lecturer getLecturerbyUsername(String username);
	
	public List<Integer> findClassIDbyLecID(int id);
	
	public List<Integer> findScheduleByClassID(int id);
	
	public Attendance getAttendanceByClassSchedule(int id);
	
	public int getmaxScheduleid();
	
	public List<StudentLeave> getAll();

	public Schedule getSchedule(int id);
	
	public List<Integer> getAttendanceIDbyScheduleID (int id);

	public List<Attendance> getAttendancebyScheudleID(int id);
	
	public List<Schedule> getSchedules();
	
	public Schedule getSchedulebyId(int id);
	
	public Schedule saveSchedule(Schedule schedule);	
	
	public void createAttendanceData(int scheduleId);

}
