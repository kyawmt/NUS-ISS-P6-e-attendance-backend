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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EAttendanceBackendApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClassRepoUnitTest {
	
	@Autowired
	private ClassRepository crepo;
	
	@Autowired
	private AcademicPeriodRepository aprepo;
	
	@Autowired
	private LecturerRepository lrepo;
	
	@Autowired
	private ModuleRepository mrepo;
	
	LocalDate date1 = LocalDate.now();
	LocalDate date2 = LocalDate.now().plusDays(10);
	
	@Test
	@Order(1)
	public void create() {
		
		Lecturer lecturer1 = new Lecturer("L1234", "Fan", "Liu", "lecturer1@email.com","123456");		
		Module module1 = new Module("COMP101", "Introduction to Computing", 75);		
		AcademicPeriod ap1 = new AcademicPeriod("AY2020/2021", Semester.SEMESTER1, date1, date2);	
		Class class1 = new Class(1, ap1, module1, lecturer1);
		Class savedclass1 = crepo.save(class1);
		
		assertNotNull(savedclass1);
		
	}
	
	@Test
	@Order(2)
	public void update() {
		
		Module module1 = mrepo.findByCode("COMP101");
		Lecturer lecturer1 = lrepo.findByUserName("lecturer1@email.com");
		AcademicPeriod ap1 = aprepo.findByYear("AY2020/2021");
		
		module1.setCode("COMP102");
		Module savedmodule1 = mrepo.save(module1);
		lecturer1.setPassword("234567");
		Lecturer savedlecturer1 = lrepo.save(lecturer1);
		ap1.setEndDate(date2.plusDays(5));
		AcademicPeriod savedap1 = aprepo.save(ap1);
		Class class1 = crepo.findBySize(1);
		
		assertEquals(class1.getModule().getCode(), savedmodule1.getCode());
		assertEquals(class1.getLecturer().getPassword(), savedlecturer1.getPassword());
		assertEquals(class1.getAcademicPeriod().getEndDate(), savedap1.getEndDate());
		
		Module module2 = new Module("MATH101", "Introduction to Mathematics", 75);
		Lecturer lecturer2 = new Lecturer("L2345", "Cher Wah", "Tan", "lecturer2@email.com","123456");
		AcademicPeriod ap2 = new AcademicPeriod("AY2021/2022", Semester.SEMESTER2, date1, date2);
		Class class2 = crepo.findBySize(1);
		
		class2.setModule(module2);
		class2.setLecturer(lecturer2);
		class2.setAcademicPeriod(ap2);
		Class class3 = crepo.save(class2);
		
		assertEquals(class3.getModule().getCode(), module2.getCode());
		assertEquals(class3.getLecturer().getStaffId(), lecturer2.getStaffId());
		assertEquals(class3.getAcademicPeriod().getYear(), ap2.getYear());
		
	}
	
	@Test
	@Order(3)
	public void Delete() {
		
		DeleteClass();
//		DeleteModule();
//		DeleteLecturer();
//		DeleteAcademicPeriod();
		
	}
	
	public void DeleteClass() {
		
		Class class1 = crepo.findBySize(1);
		crepo.delete(class1);
		
		Module module1 = mrepo.findByCode("MATH101");
		Lecturer lecturer1 = lrepo.findByUserName("lecturer2@email.com");
		AcademicPeriod ap1 = aprepo.findByYear("AY2021/2022");
		Class class2 = crepo.findBySize(1);
		
		assertNull(class2);
		assertNotNull(module1);
		assertNotNull(lecturer1);
		assertNotNull(ap1);
		
		mrepo.delete(module1);
		lrepo.delete(lecturer1);
		aprepo.delete(ap1);	
	
	}
	
	public void DeleteModule() {	
		
		Module module1 = mrepo.findByCode("MATH101");
		Lecturer lecturer1 = lrepo.findByUserName("lecturer2@email.com");
		AcademicPeriod ap1 = aprepo.findByYear("AY2021/2022");
		
		mrepo.delete(module1);
		Class class1 = crepo.findBySize(1);
		assertNull(class1);
		
		lrepo.delete(lecturer1);
		Class class2 = crepo.findBySize(1);
		assertNull(class2);
		
		aprepo.delete(ap1);
		Class class3 = crepo.findBySize(1);
		assertNull(class3);
		
		Class class4 = crepo.findBySize(1);
		assertNull(class4);	
		
	}
	
	public void DeleteLecturer() {
		
		Class class1 = crepo.findBySize(1);
		class1.setLecturer(null);
		Class savedclass1 = crepo.save(class1);
		Lecturer lecturer1 = lrepo.findByUserName("lecturer2@email.com");		
		lrepo.delete(lecturer1);
		
		Module module1 = mrepo.findByCode("MATH101");
		AcademicPeriod ap1 = aprepo.findByYear("AY2021/2022");
		
		assertNotNull(savedclass1);
		mrepo.delete(module1);
		aprepo.delete(ap1);
	
	}
	
	public void DeleteAcademicPeriod() {
		
		AcademicPeriod ap1 = aprepo.findByYear("AY2021/2022");
		aprepo.delete(ap1);
		
		Module module1 = mrepo.findByCode("MATH101");
		Lecturer lecturer1 = lrepo.findByUserName("lecturer2@email.com");
		Class class1 = crepo.findBySize(1);
		
		assertNull(class1);
		mrepo.delete(module1);
		lrepo.delete(lecturer1);
	
	}

}
