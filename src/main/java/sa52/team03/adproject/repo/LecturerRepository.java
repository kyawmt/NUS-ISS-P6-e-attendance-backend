package sa52.team03.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Class;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
	
	public Lecturer findByUserName(String userName);
	


}
