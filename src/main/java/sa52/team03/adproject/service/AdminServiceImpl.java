package sa52.team03.adproject.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sa52.team03.adproject.domain.Admin;
import sa52.team03.adproject.repo.AdminRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa52.team03.adproject.domain.AcademicPeriod;
import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Enrolment;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Module;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.repo.AcademicPeriodRepository;
import sa52.team03.adproject.repo.AttendanceRepository;
import sa52.team03.adproject.repo.ClassRepository;
import sa52.team03.adproject.repo.EnrolmentRepository;
import sa52.team03.adproject.repo.LecturerRepository;
import sa52.team03.adproject.repo.ModuleRepository;
import sa52.team03.adproject.repo.ScheduleRepository;
import sa52.team03.adproject.repo.StudentRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	LecturerRepository lecturerRepo;

	@Autowired
	ModuleRepository moduleRepo;

	@Autowired
	AdminRepository adminRepo;

	@Autowired
	ClassRepository classRepo;

	@Autowired
	AcademicPeriodRepository academicPeriodRepo;

	@Autowired
	ScheduleRepository scheduleRepo;

	@Autowired
	AttendanceRepository attendanceRepo;

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	EnrolmentRepository enrolmentRepo;

	@Override
	public List<Student> getStudents() {
		return studentRepo.findAll();
	}

	@Override
	public Student getStudentById(int id) {
		return studentRepo.findById(id).get();
	}

	@Override
	public Student saveStudent(Student student) {
		return studentRepo.save(student);
	}

	@Override
	public void deleteStudent(int id) {
		studentRepo.deleteById(id);
	}

	@Override
	public Boolean isStudentExist(int id, String userName) {
		for (Student s : studentRepo.findAll()) {
			if (s.getId() == id)
				continue;
			if (s.getUserName().equalsIgnoreCase(userName))
				return true;
		}
		for (Lecturer l : lecturerRepo.findAll()) {
			if (l.getUserName().equalsIgnoreCase(userName))
				return true;
		}
		for (Admin a : adminRepo.findAll()) {
			if (a.getUserName().equalsIgnoreCase(userName))
				return true;
		}
		return false;
	}

	@Override
	public List<Class> getClasses() {
		return classRepo.findAll();
	}

	@Override
	public Class saveClass(Class _class) {
		return classRepo.save(_class);
	}

	@Override
	public List<Lecturer> getLecturers() {
		return lecturerRepo.findAll();
	}

	@Override
	public Lecturer saveLecturer(Lecturer lecturer) {
		return lecturerRepo.save(lecturer);
	}

	@Override
	public Lecturer getLecturerById(int id) {
		return lecturerRepo.getById(id);
	}

	@Override
	public void deleteLecturer(int id) {
		lecturerRepo.deleteById(id);
	}

	@Override
	public Boolean isLecturerExist(int id, String userName) {
		for (Lecturer l : lecturerRepo.findAll()) {
			if (l.getId() == id)
				continue;
			if (l.getUserName().equalsIgnoreCase(userName))
				return true;
		}
		for (Student s : studentRepo.findAll()) {
			if (s.getUserName().equalsIgnoreCase(userName))
				return true;
		}
		for (Admin a : adminRepo.findAll()) {
			if (a.getUserName().equalsIgnoreCase(userName))
				return true;
		}
		return false;
	}

	@Override
	public List<Module> getModules() {
		return moduleRepo.findAll();
	}

	@Override
	public Module getModuleById(int id) {
		return moduleRepo.findById(id).get();
	}

	@Override
	public Module saveModule(Module module) {
		return moduleRepo.save(module);
	}

	@Override
	public void deleteModule(int id) {
		moduleRepo.deleteById(id);
	}

	@Override
	public List<Schedule> getSchedules() {
		return scheduleRepo.findAll();
	}

	@Override
	public Schedule saveSchedule(Schedule schedule) {
		return scheduleRepo.save(schedule);
	}

	@Override
	public void deleteSchedule(int id) {
		scheduleRepo.deleteById(id);
	}

	public List<LocalDate> getScheduleDates(LocalDate startDate, LocalDate endDate, List<Integer> days) {
		// This method is suggested by Min

		return null;
	}

	@Override
	public List<Class> getClassByModuleId(int id) {
		List<Class> selectedClasses = new ArrayList<>();
		Module module = moduleRepo.getById(id);
		for (Class c : classRepo.findAll()) {
			if (c.getModule().equals(module))
				selectedClasses.add(c);
		}
		return selectedClasses;
	}

	@Override
	public List<Student> getStudentsByClassId(int id) {
		List<Integer> selectedStudentId = new ArrayList<>();
		List<Student> selectedStudents = new ArrayList<>();

		for (Enrolment e : enrolmentRepo.findAll()) {
			if (e.get_class().getId() == id)
				selectedStudentId.add(e.getStudent().getId());
		}

		for (Integer i : selectedStudentId) {
			for (Student s : studentRepo.findAll()) {
				if (s.getId() == i)
					selectedStudents.add(s);
			}
		}
		return selectedStudents;
	}

	@Override
	public List<Schedule> getClassScheduleUntilNow(int classId) {
		List<Schedule> classScheduleUntilNow = new ArrayList<>();

		for (Schedule s : scheduleRepo.findAll()) {
			if (s.get_class().getId() == classId && s.getDate().isBefore(LocalDate.now().plusDays(1)))
				classScheduleUntilNow.add(s);
		}

		return classScheduleUntilNow;
	}

	@Override
	public String calculateClassAttendanceRate(int classId) {

		List<Student> classSize = new ArrayList<>();
		List<Attendance> attendedStudent = new ArrayList<>();
		List<Schedule> classScheduleUntilNow = this.getClassScheduleUntilNow(classId);
		double classAttendanceRate = 0;

		for (Enrolment e : enrolmentRepo.findAll()) {
			if (e.get_class().getId() == classId)
				classSize.add(e.getStudent());
		}

		for (Schedule s : classScheduleUntilNow) {
			for (Attendance a : attendanceRepo.findAll()) {
				if (a.getSchedule().getId() == s.getId()) {
					if (a.getSignIn() == true && a.getSignOut() == true)
						attendedStudent.add(a);
				}
			}
		}

		if (classSize.size() != 0)
			classAttendanceRate = (double) attendedStudent.size() / (classSize.size() * classScheduleUntilNow.size())
					* 100;

		return String.valueOf((int) classAttendanceRate) + "%";

	}

	@Override
	public int calculateStudentAttendanceRate(int classId, int studentId) {
		List<Schedule> classScheduleUntilNow = this.getClassScheduleUntilNow(classId);
		int attendanceTimes = 0;
		double studentAttendanceRate = 0;

		for (Attendance a : attendanceRepo.findAll()) {
			for (Schedule s : classScheduleUntilNow) {
				if (a.getSchedule().getId() == s.getId() && a.getStudent().getId() == studentId && a.getSignIn() == true
						&& a.getSignOut() == true)
					attendanceTimes++;
			}
		}

		if (classScheduleUntilNow.size() != 0)
			studentAttendanceRate = (double) attendanceTimes / classScheduleUntilNow.size() * 100;

		return (int) studentAttendanceRate;
	}

	@Override
	public Map<String, Object> createClassMap(Class c) {
		String classAttendanceRate = calculateClassAttendanceRate(c.getId());

		Map<String, Object> classMap = new HashMap<>();
		classMap.put("id", c.getId());
		classMap.put("code", c.getCode());
		classMap.put("modulecode", c.getModule().getCode());
		classMap.put("moduleid", c.getModule().getId());
		classMap.put("year", c.getAcademicPeriod().getYear());
		classMap.put("semester", c.getAcademicPeriod().getSemester());
		classMap.put("maxSize", c.getSize());
		classMap.put("currentSize", getStudentsByClassId(c.getId()).size());
		classMap.put("remainingSize", c.getSize() - getStudentsByClassId(c.getId()).size());
		classMap.put("rate", classAttendanceRate);

		return classMap;
	}

	@Override
	public Map<String, Object> createStudentMap(Student s, int classId) {
		int attendanceRate = calculateStudentAttendanceRate(classId, s.getId());
		int reachMinAttendanceOrNot = 0;

		if (attendanceRate >= classRepo.getById(classId).getModule().getMinAttendance())
			reachMinAttendanceOrNot = 1;

		Map<String, Object> studentMap = new HashMap<>();
		studentMap.put("id", s.getId());
		studentMap.put("studentId", s.getStudentId());
		studentMap.put("firstName", s.getFirstName());
		studentMap.put("lastName", s.getLastName());
		studentMap.put("userName", s.getUserName());
		studentMap.put("rate", String.valueOf(attendanceRate) + "%");
		studentMap.put("reachMinAttendanceOrNot", reachMinAttendanceOrNot);

		return studentMap;
	}

	@Override
	public void enrollStudent(int classId, int studentId) {
		Class c = classRepo.getById(classId);
		Student s = studentRepo.getById(studentId);

		Enrolment e = new Enrolment(c, s);
		if (getStudentsByClassId(c.getId()).size() <= c.getSize()) {
			enrolmentRepo.save(e);
		}
	}

	@Override
	public void removeStudentInClass(int classId, int studentId) {
		for (Enrolment e : enrolmentRepo.findAll()) {
			if (e.get_class().getId() == classId && e.getStudent().getId() == studentId)
				enrolmentRepo.delete(e);
		}
	}

	// For MAchine Learning Model 1--predict class student pass or not
	@Override
	public int getStudentAbsenceTimes(int classId, int studentId) {
		List<Schedule> classScheduleUntilNow = this.getClassScheduleUntilNow(classId);
		int studentAbsenceTimes = (1 - calculateStudentAttendanceRate(classId, studentId) / 100)
				* classScheduleUntilNow.size();
		return studentAbsenceTimes;
	}

	@Override
	public int predictStudentPassOrNot(int classId, int studentId) {
		// get studentAbsenceTimes from last method,pass into python
		// get the python predicted result and save as predictStudentPassOrNot.
		int predictStudentPassOrNot = 0;
		return predictStudentPassOrNot;
	}

	// in lecturer
	@Override
	public void updateClassStudentPredictedGrade(int classId) {
		List<Student> classStudents = getStudentsByClassId(classId);
		for (Enrolment e : enrolmentRepo.findAll()) {
			for (Student s : classStudents) {
				if (e.get_class().getId() == classId && e.getStudent().getId() == s.getId()) {
					if (predictStudentPassOrNot(classId, s.getId()) == 1)
						e.setPredictedPerformance("pass");
					else
						e.setPredictedPerformance("fail");
				}
			}
		}
	}

	// For MAchine Learning Model 2 -- predict class attendance rate
	@Override
	public List<Schedule> getClassFutureSchedule(int classId) {
		List<Schedule> classFutureSchedule = new ArrayList<>();

		for (Schedule s : scheduleRepo.findAll()) {
			if (s.get_class().getId() == classId && s.getDate().isAfter(LocalDate.now()))
				classFutureSchedule.add(s);
		}

		return classFutureSchedule;
	}

	@Override
	public void updateClassPredictedAttendanceRate(int classId) throws Exception {

		int max = 100;
		int min = 80;
		int range = max - min + 1;

		List<LocalDate> futureScheduleDate = new ArrayList<>();
		List<Schedule> ss = new ArrayList<>();

		for (Schedule s : getClassFutureSchedule(classId)) {
			futureScheduleDate.add(s.getDate());
			ss.add(s);
		}

		LocalDate[] dates = (LocalDate[]) futureScheduleDate.toArray(new LocalDate[futureScheduleDate.size()]);

		List<Integer> randomnumbers = new ArrayList<>();

		for (int i = 0; i < dates.length; i++) {
			int rand = (int) (Math.random() * range) + min;
			randomnumbers.add(rand);
		}
		Integer[] rand = (Integer[]) randomnumbers.toArray(new Integer[randomnumbers.size()]);

		List<Map> list = new ArrayList<>();

		for (int i = 0; i < rand.length; i++) {
			Map<String, Object> ob = new HashMap<>();
			ob.put("ds", dates[i]);
			ob.put("y", rand[i]);
			list.add(ob);
		}

		Schedule[] sss = (Schedule[]) ss.toArray(new Schedule[ss.size()]);

		URL url = new URL("https://sa52team3gradeprediction.de.r.appspot.com/attend");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setDoOutput(true);

		List<JSONObject> jsonObj = new ArrayList<JSONObject>();
		for (Map data : list) {
			JSONObject obj = new JSONObject(data);
			jsonObj.add(obj);
		}

		try (OutputStream os = con.getOutputStream()) {
			os.write(jsonObj.toString().getBytes("UTF-8"));
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			String[] predict = response.toString().split(",");

			for (int j = 0; j < predict.length; j++) {
				Schedule s = sss[j];
				String str = predict[j].replaceAll("[^\\d.]", "");
				double a = Double.parseDouble(str);
				int b = (int) Math.round(a);
				;
				s.setPredictedAttendance(b);
				scheduleRepo.save(s);
			}
		}

	}

	@Override
	public List<AcademicPeriod> getAcademicPeriods() {
		return academicPeriodRepo.findAll();
	}

	@Override
	public AcademicPeriod getAcademicPeriodsById(int id) {
		return academicPeriodRepo.getById(id);
	}

	@Override
	public List<Class> getclasses() {
		return classRepo.findAll();
	}

	@Override
	public void deleteclasses(int id) {
		classRepo.deleteById(id);
	}

	@Override
	public Map<String, Object> createValidationMap(String toCheck, String moduleValidation) {

		Map<String, Object> moduleValidationMap = new HashMap<>();

		if (toCheck.contains("name")) {
			if (moduleRepo.findByName(moduleValidation) == null) {
				moduleValidationMap.put("modulename", "true");
			} else {
				moduleValidationMap.put("modulename", "false");
			}
		}

		if (toCheck.contains("code")) {
			if (moduleRepo.findByCode(moduleValidation) == null) {
				moduleValidationMap.put("modulecode", "true");
			} else {
				moduleValidationMap.put("modulecode", "false");
			}
		}

		return moduleValidationMap;
	}

	@Override
	public Class getClassByCode(String code) {
		Class _class = classRepo.getByCode(code);
		return _class;
	}
	
	@Override
	public List<Integer> classID(){
		List<Schedule> allSchedules = scheduleRepo.findAll();
		List<Integer> classIDbeforefilter = new ArrayList<>();
		for (Schedule s: allSchedules) {
			if(s.getDate().isAfter(LocalDate.now())) {
				int a = s.get_class().getId();
				classIDbeforefilter.add(a);
			}
		}
		List<Integer> distinct = classIDbeforefilter.stream().distinct().collect(Collectors.toList());
		return distinct;
	}
	
	@Override
	public void saveAcademicPeriod(AcademicPeriod acPeriod) {
		
		academicPeriodRepo.save(acPeriod);
	}
	
	@Override
	public void saveAdmin(Admin admin) {
		adminRepo.save(admin);
	}
}
