package sa52.team03.adproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.service.AdminService;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/admin/")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@GetMapping("/lecturers")
	public List<Lecturer> getLecturers(){
		return adminService.getLecturers();
	}
	
	@PostMapping("/lecturers")
	public Lecturer addLecturer(@RequestBody Lecturer lecturer) {
		return adminService.saveLecturer(lecturer);
	}
	
	@GetMapping("/lecturers/{id}")
	public Lecturer getLecturerById(@PathVariable int id){
		return adminService.getLecturerById(id);
	}
	
	@PutMapping("/lecturers/{id}")
	public Lecturer updateLecturer(@PathVariable int id,@RequestBody Lecturer lecturerDeatails){
		Lecturer lecturer=adminService.getLecturerById(id);
		
		lecturer.setFirstName(lecturerDeatails.getFirstName());
		lecturer.setLastName(lecturerDeatails.getLastName());
		lecturer.setUserName(lecturerDeatails.getUserName());
		
		Lecturer updatedlecturer=adminService.saveLecturer(lecturer);
		return updatedlecturer;
	}
	
	@DeleteMapping("/lecturers/{id}")
	public void deleteLecturer(@PathVariable int id) {
		adminService.deleteLecturer(id);
	}
}
