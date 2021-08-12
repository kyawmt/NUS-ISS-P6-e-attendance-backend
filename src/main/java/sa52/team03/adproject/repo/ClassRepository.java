package sa52.team03.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa52.team03.adproject.domain.Class;

public interface ClassRepository extends JpaRepository<Class, Integer> {
	
	//For Unit Test
	public Class findBySize(Integer size);

}
