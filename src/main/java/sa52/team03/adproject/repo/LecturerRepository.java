package sa52.team03.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sa52.team03.adproject.domain.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
	
	public Lecturer findByUserName(String userName);
	


}
