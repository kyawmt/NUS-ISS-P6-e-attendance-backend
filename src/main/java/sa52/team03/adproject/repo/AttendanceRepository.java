package sa52.team03.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa52.team03.adproject.domain.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

}
