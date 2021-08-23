package sa52.team03.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa52.team03.adproject.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	public Student findByUserName(String userName);
	
}
