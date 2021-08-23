package sa52.team03.adproject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa52.team03.adproject.repo.ScheduleRepository;
import sa52.team03.adproject.domain.AcademicPeriod;
import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.repo.AcademicPeriodRepository;
import sa52.team03.adproject.repo.AttendanceRepository;
import sa52.team03.adproject.repo.EnrolmentRepository;
import sa52.team03.adproject.repo.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	EnrolmentRepository enrolmentRepo;

	@Autowired
	AttendanceRepository attendanceRepo;
	@Autowired
	ScheduleRepository scheRepo;

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	AcademicPeriodRepository apRepo;

	@Override
	public Student getStudentByUserName(String userName) {
		return studentRepo.findByUserName(userName);
	}

	@Override
	public List<Class> getStudentClasses(Student student) {
		LocalDate date = LocalDate.now();
		AcademicPeriod academicPeriod = getAcademicPeriodByDate(date);
		return enrolmentRepo.findClassesByStudent(student, academicPeriod);
	}

	@Override
	public List<Map<String, Object>> getStudentClassModuleScheduleAttendance(Student student) {

		List<Map<String, Object>> classModules = new ArrayList<>();

		List<Class> classes = getStudentClasses(student);
		for (Class c : classes) {
			Map<String, Object> classModuleMap = new HashMap<>();
			List<LocalDate> schedules = new ArrayList<>();
			classModuleMap.put("moduleCode", c.getModule().getCode());
			classModuleMap.put("moduleName", c.getModule().getName());
			if (c.getLecturer() != null)
				classModuleMap.put("lecturerName",
						c.getLecturer().getFirstName() + " " + c.getLecturer().getLastName());
			else
				classModuleMap.put("lecturerName", "");
			classModuleMap.put("minAttendance", c.getModule().getMinAttendance());
			classModuleMap.put("studentAttendance", calculateAttendanceRate(c, student));
			for (Schedule s : c.getSchedules()) {
				schedules.add(s.getDate());
			}
			classModuleMap.put("schedules", schedules);
			classModules.add(classModuleMap);
		}

		return classModules;
	}

	public Integer calculateAttendanceRate(Class _class, Student student) {
		Integer attendanceRate = 0;
		Integer presentCount = 0;

		List<Attendance> attendances = attendanceRepo.findAttendanceByClassAndStudent(_class, student);

		for (Attendance a : attendances) {
			if (a.getSignIn() == true && a.getSignOut() == true) {
				presentCount++;
			}
		}

		if (attendances.size() != 0) {
			attendanceRate = presentCount * 100 / attendances.size();
		}

		return attendanceRate;
	}

	public AcademicPeriod getAcademicPeriodByDate(LocalDate date) {
		return apRepo.findAcademicPeriodByDate(date);
	}

	@Override
	public Schedule getScheduleById(int scheduleId) {
		return scheRepo.getById(scheduleId);
	}

	@Override
	public Student findStudentByUserName(String userName) {
		return studentRepo.findByUserName(userName);
	}

	@Override
	public Attendance findAttendanceByScheduleAndStudent(Schedule schedule, Student student) {
		return attendanceRepo.findByScheduleAndStudent(schedule, student);
	}

	@Override
	public void saveAttendance(Attendance attendance) {
		attendanceRepo.save(attendance);
	}
}
