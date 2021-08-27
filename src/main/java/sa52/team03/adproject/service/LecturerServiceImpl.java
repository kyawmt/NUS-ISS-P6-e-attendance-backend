package sa52.team03.adproject.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Enrolment;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.domain.StudentLeave;
import sa52.team03.adproject.repo.AttendanceRepository;
import sa52.team03.adproject.repo.ClassRepository;
import sa52.team03.adproject.repo.EnrolmentRepository;
import sa52.team03.adproject.repo.LecturerRepository;
import sa52.team03.adproject.repo.ModuleRepository;
import sa52.team03.adproject.repo.ScheduleRepository;
import sa52.team03.adproject.repo.StudentLeaveRepository;
import sa52.team03.adproject.repo.StudentRepository;

@Service
public class LecturerServiceImpl implements LecturerService {

	@Autowired
	LecturerRepository lrepo;

	@Autowired
	ClassRepository crepo;

	@Autowired
	ScheduleRepository srepo;

	@Autowired
	AttendanceRepository arepo;

	@Autowired
	ModuleRepository mrepo;

	@Autowired
	StudentRepository sturepo;

	@Autowired
	StudentLeaveRepository leaverepo;

	@Autowired
	ScheduleRepository scheRepo;

	@Autowired
	AttendanceRepository attendRepo;

	@Autowired
	EnrolmentRepository erepo;

	@Autowired
	EnrolmentRepository enrolmentRepo;

	@Autowired
	AdminService adminService;
	
	@Autowired 
	LecturerService lecturerService;

	@Override
	public List<Attendance> getListOfClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lecturer getLecturerbyUsername(String username) {
		return lrepo.findByUserName(username);

	}

	@Override
	public List<Integer> findClassIDbyLecID(int id) {
		return crepo.findClassIDbyLecID(id);
	}

	@Override
	public List<Schedule> findScheduleByClassID(int id) {
		return srepo.findScheduleByClassID(id);
	}

	@Override
	public List<Integer> getAttendanceIDbyScheduleID(int id) {
		return arepo.getAttendanceIDbyScheduleID(id);
	}

	@Override
	public List<Attendance> getAttendancebyScheudleID(int id) {
		return arepo.getAttendancebyScheduleID(id);
	}

	@Override
	public List<StudentLeave> getAll() {
		return leaverepo.findAll();
	}

	@Override
	public Schedule getSchedule(int id) {
		return srepo.getById(id);
	}

	@Override
	public int getmaxScheduleid() {
		return srepo.getmaxScheduleid();
	}

	@Override
	public Attendance getAttendanceByClassSchedule(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Schedule> getSchedules() {
		return scheRepo.findAll();
	}

	@Override
	public Schedule getSchedulebyId(int id) {
		return scheRepo.getById(id);
	}

	@Override
	public Schedule saveSchedule(Schedule schedule) {
		return scheRepo.save(schedule);
	}

	@Override
	public void createAttendanceData(int scheduleId) {

		Schedule schedule = scheRepo.getById(scheduleId);

		if (schedule.getAttendances().size() == 0) {

			Collection<Enrolment> enrolments = schedule.get_class().getEnrolments();

			for (Enrolment enrolment : enrolments) {

				Attendance attendance = new Attendance();
				attendance.setSchedule(schedule);
				attendance.setStudent(enrolment.getStudent());
				attendance.setSignIn(false);
				attendance.setSignOut(false);
				attendRepo.save(attendance);
			}

		}
	}

	@Override
	public List<Schedule> getLecturerTodaySchedules(Lecturer lecturer) {

		List<Schedule> lecturerTodaySchedules = new ArrayList<Schedule>();

		List<Schedule> todaySchedules = scheRepo.getTodaySchedules(LocalDate.now());

		for (Schedule schedule : todaySchedules) {

			if (schedule.get_class().getLecturer() == lecturer) {
				lecturerTodaySchedules.add(schedule);
			}

		}

		return lecturerTodaySchedules;
	}

	@Override
	public List<Schedule> getLecturerSchedulesByRange(Lecturer lecturer, LocalDate startDate, LocalDate endDate) {

		List<Schedule> lecturerSchedulesByRange = new ArrayList<Schedule>();

		List<Schedule> schedules = scheRepo.findAll();

		for (Schedule schedule : schedules) {

			if (schedule.get_class().getLecturer() == lecturer) {

				if ((schedule.getDate().isAfter(startDate) && schedule.getDate().isBefore(endDate))
						|| schedule.getDate().isEqual(startDate) || schedule.getDate().isEqual(endDate)) {

					lecturerSchedulesByRange.add(schedule);

				}
			}

		}

		return lecturerSchedulesByRange;
	}

	@Override
	public List<Enrolment> findEnrolmentByClassid(int classid) {
		return erepo.findEnrolmentByClassid(classid);
	}

	@Override
	public void saveEnrolment(Enrolment e) {
		erepo.save(e);
	}

	@Override
	public List<Class> getClassesByLecturerId(int id) {
		return crepo.findClassByLecturerId(id);
	}

	
	@Override
	public Map<String, Object> createClassMap(Class c) {
		String classAttendanceRate = adminService.calculateClassAttendanceRate(c.getId());
		int classSize = calculateClassSize(c.getId());
		int classPredictedPassRate = calculateClassPerformanceRate(c.getId(), "1");
		int classPredictedFailRate = calculateClassPerformanceRate(c.getId(), "0");

		Map<String, Object> classMap = new HashMap<>();
		classMap.put("id", c.getId());
		classMap.put("modulename", c.getModule().getName());
		classMap.put("modulecode", c.getModule().getCode());
		classMap.put("moduleid", c.getModule().getId());
		classMap.put("code", c.getCode());
		classMap.put("year", c.getAcademicPeriod().getYear());
		classMap.put("semester", c.getAcademicPeriod().getSemester());
		classMap.put("rate", classAttendanceRate);
		classMap.put("performancePass", classPredictedPassRate);
		classMap.put("performanceFail", classPredictedFailRate);
		classMap.put("size", classSize);

		return classMap;

	}

	@Override
	public Class getClassById(int classId) {
		return crepo.findById(classId).get();
	}

	public int calculateClassSize(int classId) {
		return enrolmentRepo.findByClassId(classId).size();
	}

	public int calculateClassPerformanceRate(int classId, String x) {
		List<Enrolment> enrolmentList = enrolmentRepo.findByClassId(classId);
		int counter = 0;

		for (Enrolment e : enrolmentList) {

			if (e.getPredictedPerformance().contains(x)) {
				counter++;
			}
		}

		return counter;
	}

	@Override
	public List<Schedule> getSchedulesByClassId(int classId) {
		return srepo.findByClassId(classId);
	}

	@Override
	public Map<String, Object> createClassAttendanceMap(Schedule s) {

		Map<String, Object> classAttendanceMap = new HashMap<>();
		classAttendanceMap.put("id", s.getId());
		classAttendanceMap.put("date", s.getDate());
		classAttendanceMap.put("predictedAttendanceRate", s.getPredictedAttendance());
		classAttendanceMap.put("actualAttendanceRate", calculateScheduleAttendanceRate(s));

		return classAttendanceMap;
	}

	public double calculateScheduleAttendanceRate(Schedule schedule) {

		int classSize = calculateClassSize(schedule.get_class().getId());

		List<Attendance> attendedStudent = new ArrayList<>();
		double classAttendanceRate = 0;

		for (Attendance a : arepo.findAll()) {
			if (a.getSchedule().getId() == schedule.getId()) {
				if (a.getSignIn() == true && a.getSignOut() == true)
					attendedStudent.add(a);
			}
		}

		if (classSize != 0)
			classAttendanceRate = ((double)attendedStudent.size() / (double)classSize) * 100;
		
		return Math.round(classAttendanceRate);
	}

	@Override
	public List<Integer> getStudentIdByPredictedPerformance(int classId, String i) {

		return enrolmentRepo.findStudentIdByClassId(classId, i);

	}

	@Override
	public Map<String, Object> createStudentMap(int id, List<Schedule> schedules) {

		Student studentDetails = sturepo.findById(id).get();

		Map<String, Object> studentAttendanceMap = new HashMap<>();
		studentAttendanceMap.put("id", studentDetails.getId());
		studentAttendanceMap.put("firstname", studentDetails.getFirstName());
		studentAttendanceMap.put("lastname", studentDetails.getLastName());
		studentAttendanceMap.put("studentId", studentDetails.getStudentId());
		studentAttendanceMap.put("username", studentDetails.getUserName());
		studentAttendanceMap.put("attendancerate", calculateStudentAttendanceRate(id, schedules));

		return studentAttendanceMap;
	}

	public int calculateStudentAttendanceRate(int id, List<Schedule> schedules) {

		int totalClasses = 0;
		int studentPresentRate = 0;
		double studentAttendanceRate = 0;

		for (Schedule s : schedules) {
			for (Attendance a : arepo.findAll()) {
				if (a.getSchedule().getId() == s.getId() && a.getStudent().getId() == id) {
					if (a.getSignIn() == true && a.getSignOut() == true) {
						studentPresentRate++;
					}
					totalClasses++;
				}
			}
		}

		if (totalClasses != 0)
			studentAttendanceRate = ((double)studentPresentRate / (double)totalClasses) * 100;

		return (int) studentAttendanceRate;

	}

	@Override
	public Map<String, Object> createSideBarClassMap(Class c) {
		String classAttendanceRate = adminService.calculateClassAttendanceRate(c.getId());
		int classSize = calculateClassSize(c.getId());

		Map<String, Object> classMap = new HashMap<>();
		classMap.put("id", c.getId());
		classMap.put("modulename", c.getModule().getName());
		classMap.put("modulecode", c.getModule().getCode());
		classMap.put("moduleid", c.getModule().getId());
		classMap.put("code", c.getCode());
		classMap.put("year", c.getAcademicPeriod().getYear());
		classMap.put("semester", c.getAcademicPeriod().getSemester());
		classMap.put("rate", classAttendanceRate);
		classMap.put("size", classSize);

		return classMap;
	}
	
	
	@Override
	public void savePrediction (Integer classid) throws Exception {
				
		URL url = new URL("https://sa52team3gradeprediction.de.r.appspot.com/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setDoOutput(true);

		List<Student> selectedStudents = adminService.getStudentsByClassId(classid);
		List<Integer> studentids = new ArrayList<>();
		for (Student s : selectedStudents) {
			studentids.add(s.getId());
		}
		Integer[] studentid = (Integer[]) studentids.toArray(new Integer[studentids.size()]);
		List<Integer> studentAttendanceRate = new ArrayList<>();
		for (Integer a : studentids) {
			int b = adminService.calculateStudentAttendanceRate(classid, a);
			int c = 100 - b;
			studentAttendanceRate.add(c);
		}

		JSONArray a1 = new JSONArray();
		for (Integer i : studentAttendanceRate) {
			a1.put(i);
		}

		try (OutputStream os = con.getOutputStream()) {
			os.write(a1.toString().getBytes("UTF-8"));
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			String[] predict = response.toString().split(",");
			List<Enrolment> e = lecturerService.findEnrolmentByClassid(classid);
			Enrolment[] es = (Enrolment[]) e.toArray(new Enrolment[e.size()]);

				for (int j = 0; j < predict.length; j++) {
					if (es[j].getStudent().getId() == studentid[j]) {
						String predict1 = predict[j].replaceAll("\\D+", "");
						es[j].setPredictedPerformance(predict1);
						lecturerService.saveEnrolment(es[j]);

					}
				}
				}
		}
	
}

	
		
