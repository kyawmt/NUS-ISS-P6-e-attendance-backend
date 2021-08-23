package sa52.team03.adproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Module;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.service.AdminService;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/admin/")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@GetMapping("/schedules")
	public List<Schedule> getSchedules(){
		return adminService.getSchedules();
	}
	
	@PostMapping("/schedules")
	public Schedule addSchedule(@RequestBody String scheduleJson){
		
		
		
		return adminService.getSchedules().get(0);
	}
	
	@DeleteMapping("/schedules/{id}")
	public void deleteSchedule(@PathVariable int id) {
		adminService.deleteSchedule(id);
	}
	
    @GetMapping("/students")
    public List<Student> getStudents() {
        return adminService.getStudents();
    }
    
    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable int id) {
        return adminService.getStudentById(id);
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return adminService.saveStudent(student);
    }
    
    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student updatedStudent = adminService.getStudentById(id);
        
        updatedStudent.setFirstName(student.getFirstName());
        updatedStudent.setLastName(student.getLastName());
        updatedStudent.setUserName(student.getUserName());
        
        return adminService.saveStudent(updatedStudent);
    }
    
    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable int id) {
        adminService.deleteStudent(id);
    }
    
	@GetMapping("/students/check-exist/{id}")
	public Boolean isStudentExist(@PathVariable int id, @RequestParam String userName) {
		return adminService.isStudentExist(id, userName);
	}
	
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
	
	@GetMapping("/lecturers/check-exist/{id}")
	public Boolean isLecturerExist(@PathVariable int id, @RequestParam String userName) {
		return adminService.isLecturerExist(id, userName);
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
		
	@GetMapping("/module-classes/{id}")
	public List<Map<String, Object>> getClassByModuleId(@PathVariable int id){
		List<Map<String, Object>> classMapList =  new ArrayList<>();
		List<Class> moduleClasses=adminService.getClassByModuleId(id);
		 
		for(Class c:moduleClasses) {
			Map<String,Object> classMap=adminService.createClassMap(c);
			classMapList.add(classMap);
		}
		
		return classMapList;
	}
	
	@GetMapping("/module-classes-info/{id}")
	public Map<String,Object> getClassInfoByClassId(@PathVariable int id){
		Map<String,Object> classMap=new HashMap<>();
		for(Class c:adminService.getClasses()) {
			if(c.getId()==id)
				classMap=adminService.createClassMap(c);
		}
		return classMap;
	}
	
	@GetMapping("/module-classes-students/{id}")
	public List<Map<String, Object>> getStudentsByClassId(@PathVariable int id){
		List<Map<String, Object>> studentMapList =new ArrayList<>();
		List<Student> classStudents=adminService.getStudentsByClassId(id);
		
		for(Student s:classStudents) {
			Map<String,Object> studentMap=adminService.createStudentMap(s, id);
			studentMapList.add(studentMap);
		}
		
		return studentMapList;
	}
	
	//testing for ML predicted attendance
	@GetMapping(value = "predict/{classId")
	public void testoifpredictedschedulecan (@PathVariable int classID) throws Exception{
		adminService.updateClassPredictedAttendanceRate(classID);
	}
	
	@GetMapping("/modules/validation/{toCheck}/{moduleValidation}")
	public Map<String, Object> getValidationForModule(@PathVariable String toCheck, @PathVariable String moduleValidation) {
		return adminService.createValidationMap(toCheck, moduleValidation);
	}
	
}
