package sa52.team03.adproject.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sa52.team03.adproject.EAttendanceBackendApplication;
import sa52.team03.adproject.domain.AcademicPeriod;
import sa52.team03.adproject.domain.AcademicPeriod.Semester;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Module;
import sa52.team03.adproject.domain.Schedule;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EAttendanceBackendApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AcademicPeriodRepoUnitTest {

	@Autowired
	private AcademicPeriodRepository aprepo;

	@Autowired
	private ScheduleRepository srepo;

	@Test
	@Order(1)
	public void create() {

		AcademicPeriod ap1 = new AcademicPeriod(2021, Semester.SEMESTER1);
		AcademicPeriod ap2 = new AcademicPeriod(2021, Semester.SEMESTER2);
		AcademicPeriod ap3 = new AcademicPeriod(2022, Semester.SEMESTER1);
		AcademicPeriod ap4 = new AcademicPeriod(2022, Semester.SEMESTER2);
		AcademicPeriod ap5 = new AcademicPeriod(2023, Semester.SEMESTER1);
		AcademicPeriod ap6 = new AcademicPeriod(2023, Semester.SEMESTER2);
		AcademicPeriod ap7 = new AcademicPeriod(2019, Semester.SEMESTER1);
		AcademicPeriod ap8 = new AcademicPeriod(2019, Semester.SEMESTER2);
		AcademicPeriod savedap1 = aprepo.save(ap1);
		AcademicPeriod savedap2 = aprepo.save(ap2);
		AcademicPeriod savedap3 = aprepo.save(ap3);
		AcademicPeriod savedap4 = aprepo.save(ap4);
		AcademicPeriod savedap5 = aprepo.save(ap5);
		AcademicPeriod savedap6 = aprepo.save(ap6);
		AcademicPeriod savedap7 = aprepo.save(ap7);
		AcademicPeriod savedap8 = aprepo.save(ap8);

		assertNotNull(savedap1);
		assertNotNull(savedap2);
		assertNotNull(savedap3);
		assertNotNull(savedap4);
		assertNotNull(savedap5);
		assertNotNull(savedap6);
		assertNotNull(savedap7);
		assertNotNull(savedap8);
	}

	@Test
	@Order(2)
	public void createSchedule() {
		Lecturer lecturer1 = new Lecturer("L1234", "Fan", "Liu", "lecturer1@email.com", "123456");
		Module module1 = new Module("COMP101", "Introduction to Computing", 75);
		AcademicPeriod ap1 = new AcademicPeriod(2020, Semester.SEMESTER1);
		Schedule schedule1 = new Schedule(new Class(1, ap1, module1, lecturer1), LocalDate.now());
		srepo.save(schedule1);
	}

}
