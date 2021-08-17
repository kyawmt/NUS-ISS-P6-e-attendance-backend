package sa52.team03.adproject.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sa52.team03.adproject.domain.Admin;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.repo.AdminRepository;
import sa52.team03.adproject.repo.LecturerRepository;
import sa52.team03.adproject.repo.StudentRepository;
import sa52.team03.adproject.utils.TokenUtil;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthController {

	@Resource
	private AdminRepository adminRepository;

	@Resource
	private LecturerRepository lecturerRepository;

	@Resource
	private StudentRepository studentRepository;

	@Resource
	private TokenUtil tokenUtil;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> Authenticate(@RequestBody String json) throws Exception {

		JsonNode jsonNode = mapper.readTree(json);

		String username = jsonNode.path("username").textValue();
		String password = jsonNode.path("password").textValue();

		Admin admin = adminRepository.findByUserName(username);
		Lecturer lecturer = lecturerRepository.findByUserName(username);
		Student student = studentRepository.findByUserName(username);

		if ((admin != null && admin.getPassword().equals(password))
				|| (lecturer != null && lecturer.getPassword().equals(password))
				|| (student != null && student.getPassword().equals(password))) {

			String token = tokenUtil.generateToken(username);
			HttpHeaders headers = new HttpHeaders();
			headers.add("JwtToken", token);

			return new ResponseEntity<>(headers, HttpStatus.OK);

		} else {

			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
