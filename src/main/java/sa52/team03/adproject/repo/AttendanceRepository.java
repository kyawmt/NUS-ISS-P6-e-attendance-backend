package sa52.team03.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sa52.team03.adproject.domain.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	
//	public Attendance getAttendanceByClassSchedule(int id);
	
	@Query("Select a.id from Attendance a where a.schedule.id = :id ")
	public List<Integer> getAttendanceIDbyScheduleID (int id);
	
	@Query("Select a from Attendance a where a.schedule.id = :id")
	public List<Attendance> getAttendancebyScheduleID (int id);
	
	
}
