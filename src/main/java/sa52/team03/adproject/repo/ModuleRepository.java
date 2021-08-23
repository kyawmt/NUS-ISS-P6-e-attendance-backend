package sa52.team03.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sa52.team03.adproject.domain.Module;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
	
	public Module findByCode(@Param("code") String code);

	public Module findByName(String moduleName);
	
}
