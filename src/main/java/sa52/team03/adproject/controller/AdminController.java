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
import sa52.team03.adproject.domain.Module;
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
	
	@GetMapping("/modules")
	public List<Module> getModule() {
		return adminService.getModules();
	}
	
	@GetMapping("/modules/{id}")
	public Module getModuleById( @PathVariable int id) {
		return adminService.getModuleById(id);
	}

	@PostMapping("/modules")
	public Module addModule(@RequestBody Module module) {
		return adminService.saveModule(module);
	}
	
	@PutMapping("/modules/{id}")
	public Module updateModule(@PathVariable int id, @RequestBody Module module) {
		Module updatedModule = adminService.getModuleById(id);
		
		updatedModule.setCode(module.getCode());
		updatedModule.setName(module.getName());
		updatedModule.setMinAttendance(module.getMinAttendance());
		
		return adminService.saveModule(updatedModule);
	}
	
	@DeleteMapping("/modules/{id}")
	public void deleteModule(@PathVariable int id) {
		adminService.deleteModule(id);
	}
}
