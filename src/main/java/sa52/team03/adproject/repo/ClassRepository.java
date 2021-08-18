package sa52.team03.adproject.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sa52.team03.adproject.domain.Class;

public interface ClassRepository extends JpaRepository<Class, Integer> {
	
	//For Unit Test
	public Class findBySize(Integer size);
	
	@Query("Select c.id from Class c where c.lecturer.id = :id")
	public List<Integer> findClassIDbyLecID(@Param("id") int id);

}
