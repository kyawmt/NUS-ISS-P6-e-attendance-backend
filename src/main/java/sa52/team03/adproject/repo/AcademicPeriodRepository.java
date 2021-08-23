package sa52.team03.adproject.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sa52.team03.adproject.domain.AcademicPeriod;

public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod, Integer> {

	public AcademicPeriod findByYear(String year);
	
    @Query("SELECT a FROM AcademicPeriod a WHERE :date BETWEEN a.startDate AND a.endDate")
    public AcademicPeriod findAcademicPeriodByDate(@Param("date")LocalDate date);
}
