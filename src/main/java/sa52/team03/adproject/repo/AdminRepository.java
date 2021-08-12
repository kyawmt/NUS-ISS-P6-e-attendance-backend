package sa52.team03.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa52.team03.adproject.domain.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	public Admin findByUserName(String userName);
}
