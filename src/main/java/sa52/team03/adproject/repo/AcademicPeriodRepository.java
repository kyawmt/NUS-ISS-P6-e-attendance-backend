package sa52.team03.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa52.team03.adproject.domain.AcademicPeriod;

public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod, Integer> {

	public AcademicPeriod findByYear(String year);
}
