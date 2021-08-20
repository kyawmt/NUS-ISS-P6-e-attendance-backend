package sa52.team03.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sa52.team03.adproject.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	public Student findByUserName(String userName);
	
}
