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
import sa52.team03.adproject.domain.Admin;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EAttendanceBackendApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminRepoUnitTest {
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Test
	@Order(1)
	public void create() {
		
		Admin admin1 = new Admin("A1234", "Esther", "Tan", "admin1@email.com", "123456");
		Admin admin2 = new Admin("A2345", "Megan", "Wong", "admin2@email.com", "123456");
		Admin savedAdmin1 = adminRepo.save(admin1);
		Admin savedAdmin2 = adminRepo.save(admin2);
		assertNotNull(savedAdmin1);
		assertNotNull(savedAdmin2);
		
	}
	
	@Test
	@Order(2)
	public void update() {
		
		Admin admin1 = adminRepo.findByUserName("admin1@email.com");
		String password = "234567";
		admin1.setPassword(password);
		Admin savedAdmin1 = adminRepo.save(admin1);
		assertEquals(password, savedAdmin1.getPassword());
		
	}
	
	@Test
	@Order(3)
	public void Delete() {		
		
		Admin admin1 = adminRepo.findByUserName("admin1@email.com");
		Admin admin2 = adminRepo.findByUserName("admin2@email.com");
		adminRepo.delete(admin1);
		adminRepo.delete(admin2);
		Admin admin3 = adminRepo.findByUserName("admin1@email.com");
		assertNull(admin3);
		
	}

}
