package sa52.team03.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sa52.team03.adproject.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	
	@Query("Select s.id from Schedule s where s._class.id = :id")
	public List<Integer> findScheduleIDByClassID(int id);
	
	@Query("Select s from Schedule s where s.id = :id")
	public Schedule getSchedule(int id);
	
	@Query("SELECT MAX(id) from Schedule")
	public int getmaxScheduleid();
}
