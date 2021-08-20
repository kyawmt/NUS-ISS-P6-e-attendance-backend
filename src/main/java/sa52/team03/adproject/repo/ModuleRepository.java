package sa52.team03.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa52.team03.adproject.domain.Module;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
	
	public Module findByCode(String code);
	
}
