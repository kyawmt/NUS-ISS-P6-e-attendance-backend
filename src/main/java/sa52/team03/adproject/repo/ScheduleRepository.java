package sa52.team03.adproject.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sa52.team03.adproject.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	
	@Query("Select s from Schedule s where s._class.id = :id")
	public List<Schedule> findScheduleByClassID(int id);
	
	@Query("Select s from Schedule s where s.id = :id")
	public Schedule getSchedule(int id);
	
	@Query("SELECT MAX(id) from Schedule")
	public int getmaxScheduleid();
	
	@Query("Select s from Schedule s where s.date = :todayDate")
	public List<Schedule> getTodaySchedules(LocalDate todayDate);
	
	@Query("SELECT s FROM Schedule s WHERE s._class.id = :classId")
	List<Schedule> findByClassId(@Param("classId") int classId);

}
