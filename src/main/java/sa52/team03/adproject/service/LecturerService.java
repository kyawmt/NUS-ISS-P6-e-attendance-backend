package sa52.team03.adproject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Enrolment;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.StudentLeave;

public interface LecturerService {

	public List<Attendance> getListOfClass();

	public Lecturer getLecturerbyUsername(String username);

	public List<Integer> findClassIDbyLecID(int id);

	public List<Schedule> findScheduleByClassID(int id);

	public Attendance getAttendanceByClassSchedule(int id);

	public int getmaxScheduleid();

	public List<StudentLeave> getAll();

	public Schedule getSchedule(int id);

	public List<Integer> getAttendanceIDbyScheduleID(int id);

	public List<Attendance> getAttendancebyScheudleID(int id);

	public List<Schedule> getSchedules();

	public Schedule getSchedulebyId(int id);

	public Schedule saveSchedule(Schedule schedule);

	public void createAttendanceData(int scheduleId);

	public List<Schedule> getLecturerTodaySchedules(Lecturer lecturer);

	public List<Schedule> getLecturerSchedulesByRange(Lecturer lecturer, LocalDate startDate, LocalDate endDate);

	public List<Enrolment> findEnrolmentByClassid(int classid);

	public List<Class> getClassesByLecturerId(int id);

	public Map<String, Object> createClassMap(Class c);

	public Class getClassById(int classId);

	public List<Schedule> getSchedulesByClassId(int classId);

	public Map<String, Object> createClassAttendanceMap(Schedule s);

	public void saveEnrolment(Enrolment e);

	public List<Integer> getStudentIdByPredictedPerformance(int classId, String i);

	public Map<String, Object> createStudentMap(int id, List<Schedule> s);

	public void savePrediction(Integer classid) throws Exception;
	
	public Map<String, Object> createSideBarClassMap(Class c);
	public List<Integer> getallClassID();
	

}
