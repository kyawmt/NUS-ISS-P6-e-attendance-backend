package sa52.team03.adproject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.AcademicPeriod;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Student;

public interface StudentService {

	public Student getStudentByUserName(String userName);

	public List<Class> getStudentClasses(Student student);

	public List<Map<String, Object>> getStudentClassModuleScheduleAttendance(Student student);

	public AcademicPeriod getAcademicPeriodByDate(LocalDate date);

	public Schedule getScheduleById(int scheduleId);

	public Student findStudentByUserName(String userName);

	public Attendance findAttendanceByScheduleAndStudent(Schedule schedule, Student student);

	public void saveAttendance(Attendance attendance);

}
