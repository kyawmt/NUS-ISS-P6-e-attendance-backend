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
import sa52.team03.adproject.domain.Lecturer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EAttendanceBackendApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LecturerRepoUnitTest {
	
	@Autowired
	private LecturerRepository lrepo;
	
	@Test
	@Order(1)
	public void create() {
		
		Lecturer lecturer1 = new Lecturer("L1234", "Fan", "Liu", "lecturer1@email.com","123456");
		Lecturer savedlecturer1 = lrepo.save(lecturer1);
		assertNotNull(savedlecturer1);
		
	}
	
	@Test
	@Order(2)
	public void update() {
		
		Lecturer lecturer1 = lrepo.findByUserName("lecturer1@email.com");
		String password = "234567";
		lecturer1.setPassword(password);
		Lecturer savedlecturer1 = lrepo.save(lecturer1);
		assertEquals(password, savedlecturer1.getPassword());
		
	}
	
	@Test
	@Order(3)
	public void Delete() {	
		
		Lecturer lecturer1 = lrepo.findByUserName("lecturer1@email.com");
		lrepo.delete(lecturer1);
		Lecturer lecturer2 = lrepo.findByUserName("lecturer1@email.com");
		assertNull(lecturer2);
		
	}

}
