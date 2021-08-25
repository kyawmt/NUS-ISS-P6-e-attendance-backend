package sa52.team03.adproject.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import sa52.team03.adproject.domain.Student;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EAttendanceBackendApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepoUnitTest {

	@Autowired
	private StudentRepository srepo;
	
	@Test
	@Order(1)
	public void create() {
		
		Student student1 = new Student("S1234", "William", "Wu", "student1@email.com", "123456");
		Student savedstudent1 = srepo.save(student1);
		assertNotNull(savedstudent1);
		
	}
	
	@Test
	@Order(2)
	public void update() {
		
		Student student1 = srepo.findByUserName("student1@email.com");
		String password = "234567";
		student1.setPassword(password);
		Student savedstudent1 = srepo.save(student1);
		assertEquals(password, savedstudent1.getPassword());
		
	}
	
	@Test
	@Order(3)
	public void Delete() {	
		
		Student student1 = srepo.findByUserName("student1@email.com");
		srepo.delete(student1);
		Student student2 = srepo.findByUserName("student1@email.com");
		assertNull(student2);
		
	}
	
}
