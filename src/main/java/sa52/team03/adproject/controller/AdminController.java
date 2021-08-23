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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/admin/")
public class AdminController {

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

	// testing for ML predicted attendance
	@GetMapping(value = "predict/{classId")
	public void testoifpredictedschedulecan(@PathVariable int classID) throws Exception {
		adminService.updateClassPredictedAttendanceRate(classID);
	}

}
