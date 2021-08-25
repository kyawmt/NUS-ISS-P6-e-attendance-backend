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
import sa52.team03.adproject.domain.Module;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EAttendanceBackendApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ModuleRepoUnitTest {

	@Autowired
	private ModuleRepository mrepo;
	
	@Test
	@Order(1)
	public void create() {
		
		Module module1 = new Module("COMP101", "Introduction to Computing", 75);
		Module savedmodule1 = mrepo.save(module1);
		assertNotNull(savedmodule1);
		
	}
	
	@Test
	@Order(2)
	public void update() {
		
		Module module1 = mrepo.findByCode("COMP101");
		String code = "COMP102";
		module1.setCode(code);
		Module savedmodule1 = mrepo.save(module1);
		assertEquals(code, savedmodule1.getCode());
		
	}
	
	@Test
	@Order(3)
	public void Delete() {	
		
		Module module1 = mrepo.findByCode("COMP102");
		mrepo.delete(module1);
		Module module2 = mrepo.findByCode("COMP102");
		assertNull(module2);
		
	}
	
}
