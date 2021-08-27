package sa52.team03.adproject.repo;

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
	
	@Query("SELECT c FROM Class c WHERE c.lecturer.id = :id")
	public List<Class> findClassByLecturerId(@Param("id") int id);

	@Query("SELECT c FROM Class c WHERE c.code = :code")
	public Class getByCode(@Param("code") String code);

}
