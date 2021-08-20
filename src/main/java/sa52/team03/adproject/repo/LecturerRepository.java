package sa52.team03.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sa52.team03.adproject.domain.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
	
	public Lecturer findByUserName(String userName);
	


}
