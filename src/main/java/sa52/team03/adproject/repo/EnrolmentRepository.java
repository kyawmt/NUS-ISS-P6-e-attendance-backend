package sa52.team03.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sa52.team03.adproject.domain.AcademicPeriod;
import sa52.team03.adproject.domain.Enrolment;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.domain.Class;

public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {

	@Query("SELECT e._class FROM Enrolment e WHERE e.student = :student AND e._class.academicPeriod = :academicPeriod")
	public List<Class> findClassesByStudent(@Param("student") Student student,
			@Param("academicPeriod") AcademicPeriod academicPeriod);

	@Query("SELECT e FROM Enrolment e WHERE e._class.id = :classid")
	public List<Enrolment> findEnrolmentByClassid(@Param("classid") int classid);

	@Query("SELECT e FROM Enrolment e WHERE e._class.id = :classId")
	List<Enrolment> findByClassId(@Param("classId") int classId);

	@Query("SELECT e.student.id FROM Enrolment e WHERE e._class.id = :classId AND e.predictedPerformance = :i")
	public List<Integer> findStudentIdByClassId(@Param("classId") int classId, @Param("i") String i);

}
