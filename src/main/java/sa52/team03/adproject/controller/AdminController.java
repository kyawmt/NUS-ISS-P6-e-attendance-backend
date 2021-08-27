package sa52.team03.adproject.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sa52.team03.adproject.domain.AcademicPeriod;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Module;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.service.AdminService;
import sa52.team03.adproject.service.MailService;
import sa52.team03.adproject.utils.FaceUtil;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/admin/")
public class AdminController {
	
	@Autowired
	MailService mailService;

	@Autowired
	AdminService adminService;

	@GetMapping("/academicPeriod")
	public List<AcademicPeriod> getAcademicPeriods() {
		return adminService.getAcademicPeriods();
	}

	@GetMapping("/classes")
	public String getClasses() {

		ZoneId zone = ZoneId.systemDefault();
		SimpleDateFormat weekday = new SimpleDateFormat("EEEE", Locale.ENGLISH);

		JSONArray json = new JSONArray();

		for (Class _class : adminService.getclasses()) {

			JSONObject _classJson = new JSONObject();

			_classJson.put("id", _class.getId());
			_classJson.put("code", _class.getCode());
			_classJson.put("moduleCode", _class.getModule().getCode());
			_classJson.put("moduleName", _class.getModule().getName());
			_classJson.put("year", _class.getAcademicPeriod().getYear());
			_classJson.put("semester", _class.getAcademicPeriod().getSemester());
			_classJson.put("lecturerName",
					_class.getLecturer().getFirstName() + " " + _class.getLecturer().getLastName());
			_classJson.put("Monday", false);
			_classJson.put("Tuesday", false);
			_classJson.put("Wednesday", false);
			_classJson.put("Thursday", false);
			_classJson.put("Friday", false);

			for (Schedule schedule : _class.getSchedules()) {
				String weekDay = weekday.format(Date.from(schedule.getDate().atStartOfDay().atZone(zone).toInstant()));
				_classJson.put(weekDay, true);
			}

			json.put(_classJson);
		}

		return json.toString();
	}

	@DeleteMapping("/classes/{id}")
	public void deleteClasses(@PathVariable int id) {
		adminService.deleteclasses(id);
	}

	@PostMapping("/schedules")
	public String addSchedule(@RequestBody String scheduleJson) {

		JSONObject jsonObject = new JSONObject(scheduleJson);

		Lecturer lecturer = adminService.getLecturerById(jsonObject.getInt("lecturer"));
		Module module = adminService.getModuleById(jsonObject.getInt("module"));
		AcademicPeriod academicPeriod = adminService.getAcademicPeriodsById(jsonObject.getInt("academicPeriod"));
		int classsize = jsonObject.getInt("classsize");

		Class class1 = new Class(classsize, academicPeriod, module, lecturer);

		JSONObject days = jsonObject.getJSONObject("days");
		LocalDate start = academicPeriod.getStartDate();
		LocalDate end = academicPeriod.getEndDate();

		ZoneId zone = ZoneId.systemDefault();
		SimpleDateFormat weekday = new SimpleDateFormat("EEEE", Locale.ENGLISH);

		List<LocalDate> list = Stream.iterate(start, localDate -> localDate.plusDays(1))
				.limit(ChronoUnit.DAYS.between(start, end))
				.filter(date -> days
						.getBoolean(weekday.format(Date.from(date.atStartOfDay().atZone(zone).toInstant()))))
				.collect(Collectors.toList());

		for (LocalDate localDate : list) {
			Schedule schedule = new Schedule(class1, localDate);
			adminService.saveSchedule(schedule);
		}

		return "success";
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

	@PostMapping("/studentphoto")
	public String addStudentPic(@RequestParam("photo") String str, @RequestParam("id") int userId) {

		String img_data = str.substring(23, str.length());
		String successMsg = FaceUtil.addFace(img_data, userId);
		if (successMsg.equals("SUCCESS")) {
			Student student = adminService.getStudentById(userId);
			student.setPhotoRegistered(true);
			adminService.saveStudent(student);
		}
		System.out.println("photo register " + successMsg);
		return successMsg;
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
	public String deleteStudent(@PathVariable int id) {
		adminService.deleteStudent(id);
		String successMsg = FaceUtil.deleteUser(id);
		System.out.println("photo delete " + successMsg);
		return successMsg;

	}

	@GetMapping("/students/check-exist/{id}")
	public Boolean isStudentExist(@PathVariable int id, @RequestParam String userName) {
		return adminService.isStudentExist(id, userName);
	}

	@GetMapping("/lecturers")
	public List<Lecturer> getLecturers() {
		return adminService.getLecturers();
	}

	@PostMapping("/lecturers")
	public Lecturer addLecturer(@RequestBody Lecturer lecturer) {
		return adminService.saveLecturer(lecturer);
	}

	@GetMapping("/lecturers/{id}")
	public Lecturer getLecturerById(@PathVariable int id) {
		return adminService.getLecturerById(id);
	}

	@PutMapping("/lecturers/{id}")
	public Lecturer updateLecturer(@PathVariable int id, @RequestBody Lecturer lecturerDeatails) {
		Lecturer lecturer = adminService.getLecturerById(id);

		lecturer.setFirstName(lecturerDeatails.getFirstName());
		lecturer.setLastName(lecturerDeatails.getLastName());
		lecturer.setUserName(lecturerDeatails.getUserName());

		Lecturer updatedlecturer = adminService.saveLecturer(lecturer);
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

	@GetMapping("/modules/validation/{toCheck}/{moduleValidation}")
	public Map<String, Object> getValidationForModule(@PathVariable String toCheck,
			@PathVariable String moduleValidation) {
		return adminService.createValidationMap(toCheck, moduleValidation);
	}

	@GetMapping("/modules")
	public List<Module> getModule() {
		return adminService.getModules();
	}

	@GetMapping("/modules/{id}")
	public Module getModuleById(@PathVariable int id) {
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
	public List<Map<String, Object>> getClassByModuleId(@PathVariable int id) {
		List<Map<String, Object>> classMapList = new ArrayList<>();
		List<Class> moduleClasses = adminService.getClassByModuleId(id);

		for (Class c : moduleClasses) {
			Map<String, Object> classMap = adminService.createClassMap(c);
			classMapList.add(classMap);
		}

		return classMapList;
	}

	@GetMapping("/module-classes-info/{id}")
	public Map<String, Object> getClassInfoByClassId(@PathVariable int id) {
		Map<String, Object> classMap = new HashMap<>();
		for (Class c : adminService.getClasses()) {
			if (c.getId() == id)
				classMap = adminService.createClassMap(c);
		}
		return classMap;
	}

	@GetMapping("/module-classes-students/{id}")
	public List<Map<String, Object>> getStudentsByClassId(@PathVariable int id) {
		List<Map<String, Object>> studentMapList = new ArrayList<>();
		List<Student> classStudents = adminService.getStudentsByClassId(id);

		for (Student s : classStudents) {
			Map<String, Object> studentMap = adminService.createStudentMap(s, id);
			studentMapList.add(studentMap);
		}

		return studentMapList;
	}

	@PostMapping("/class-enroll-students/{id}")
	public void enrollStudents(@PathVariable int id, @RequestBody Integer[] selectedStudentsId) {
		for (int i = 0; i < selectedStudentsId.length; i++)
			adminService.enrollStudent(id, selectedStudentsId[i]);

	}

	@GetMapping("/class-enroll-students/{id}")
	public List<Student> getStudentsNotInClass(@PathVariable int id) {
		List<Student> allStudents = adminService.getStudents();
		List<Student> inclassStudents=adminService.getStudentsByClassId(id);
		allStudents.removeAll(inclassStudents);
		List<Student> notInclassStudents=allStudents;
		return notInclassStudents;
	}

	@PostMapping("/module-classes-students/{id}")
	public void removeStudentsFromClass(@PathVariable int id, @RequestBody Integer[] selectedStudentsId) {
		for (int i = 0; i < selectedStudentsId.length; i++)
			adminService.removeStudentInClass(id, selectedStudentsId[i]);
	}

	@PostMapping("/email/{classCode}/{id}")
	public String sendRemindMail(@PathVariable String classCode,@PathVariable int id) {
		
	Student student=adminService.getStudentById(id);
	String toEmail=student.getUserName();
	String fullname=student.getFirstName()+" "+student.getLastName();
	Class _class=adminService.getClassByCode(classCode);
	Module module=_class.getModule();
	
	String subject="Class attendance rate Email Notification";
	
	String text="Dear "+fullname+",\r\n"
			+ "\r\n"
			+ "You are not meeting the minimum attendance requirement for "+module.getName()+". Please be reminded that you will need to meet "+module.getMinAttendance()+"% in order to sit for the end-of-semester examinations.\r\n"
			+ "\r\n"
			+ "This is an automated email. Please do not reply to this email.\r\n"
			+ "\r\n"
			+ "Regards,\r\n"
			+ "School Administrator";
	
	mailService.sendMail(toEmail,subject,text);
	return "email send";
	}
	
	// testing for ML predicted attendance
	@GetMapping(value = "predict/{classId")
	public void testoifpredictedschedulecan(@PathVariable int classID) throws Exception {
		adminService.updateClassPredictedAttendanceRate(classID);
	}

}
