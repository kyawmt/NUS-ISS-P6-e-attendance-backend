package sa52.team03.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	
//	public Attendance getAttendanceByClassSchedule(int id);
	
	@Query("Select a.id from Attendance a where a.schedule.id = :id ")
	public List<Integer> getAttendanceIDbyScheduleID (int id);
	
	@Query("Select a from Attendance a where a.schedule.id = :id")
	public List<Attendance> getAttendancebyScheduleID (int id);
	
	
	public Attendance findByScheduleAndStudent(Schedule schedule, Student student);
	
    @Query("SELECT a FROM Attendance a JOIN a.schedule s WHERE s._class = :_class AND a.student = :student")
    public List<Attendance> findAttendanceByClassAndStudent(@Param("_class") Class _class, @Param("student") Student student);

}
