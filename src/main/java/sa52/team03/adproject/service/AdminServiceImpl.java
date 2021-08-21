package sa52.team03.adproject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Enrolment;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Module;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
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
	ClassRepository classRepo;
	
	@Autowired
	ScheduleRepository scheduleRepo;
	
	@Autowired
	AttendanceRepository attendanceRepo;
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	EnrolmentRepository enrolmentRepo;
	
	@Override 
	public List<Class> getClasses(){
		return classRepo.findAll();
	}
	
	@Override
	public List<Lecturer> getLecturers(){
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
	public List<Class> getClassByModuleId(int id) {
		List<Class> selectedClasses=new ArrayList<>();
		Module module=moduleRepo.getById(id);
		for(Class c: classRepo.findAll()) {
			if(c.getModule().equals(module))
				selectedClasses.add(c);
		}
		return selectedClasses;
	}
	
	@Override
	public List<Student> getStudentsByClassId(int id){
		List<Integer> selectedStudentId=new ArrayList<>();
		List<Student> selectedStudents=new ArrayList<>();
		
		for(Enrolment e:enrolmentRepo.findAll()) {
			if(e.get_class().getId()==id)
				selectedStudentId.add(e.getStudent().getId());
		}
		
		for(Integer i:selectedStudentId) {
			for(Student s:studentRepo.findAll()) {
				if(s.getId()==i)
					selectedStudents.add(s);
			}
		}
		return selectedStudents;
	}
	
	@Override
	public List<Schedule> getClassScheduleUntilNow(int classId){
		List<Schedule> classScheduleUntilNow=new ArrayList<>();
		
		for(Schedule s:scheduleRepo.findAll()) {
			if(s.get_class().getId()==classId && s.getDate().isBefore(LocalDate.now()))
				classScheduleUntilNow.add(s);
		}
		
		return classScheduleUntilNow;
	}
		
	@Override
	public String calculateClassAttendanceRate(int classId) {
		
		List<Student> classSize=new ArrayList<>();
		// Use class size, or calculate list in Enrolment Entity?
		// actually, no need attribute--size in Class Entity?
		List<Attendance> attendedStudent=new ArrayList<>();
		List<Schedule> classScheduleUntilNow=this.getClassScheduleUntilNow(classId);
		double classAttendanceRate=0;
		
		for(Enrolment e:enrolmentRepo.findAll()) {
			if(e.get_class().getId()==classId)
				classSize.add(e.getStudent());
		}
				
		for(Schedule s:classScheduleUntilNow) {
			for(Attendance a:attendanceRepo.findAll()) {
				if(a.getSchedule().getId()==s.getId()) {
					if(a.getSignIn()==true&&a.getSignOut()==true)
						attendedStudent.add(a);
				}
			}
		}
		
		if(classSize.size()!=0)
			classAttendanceRate=(double)attendedStudent.size()/(classSize.size()*classScheduleUntilNow.size())*100;
		
		return String.valueOf((int)classAttendanceRate)+"%";
		
	}
	
	@Override
	public int calculateStudentAttendanceRate(int classId,int studentId) {
		List<Schedule> classScheduleUntilNow=this.getClassScheduleUntilNow(classId);
		int attendanceTimes=0;
		double studentAttendanceRate=0;
		
		for(Attendance a:attendanceRepo.findAll()) {
			for(Schedule s:classScheduleUntilNow) {
				if(a.getSchedule().getId()==s.getId()&&a.getStudent().getId()==studentId
						&&a.getSignIn()==true &&a.getSignOut()==true)
					attendanceTimes++;
			}
		}
		
		if(classScheduleUntilNow.size()!=0)
			studentAttendanceRate=(double)attendanceTimes/classScheduleUntilNow.size()*100;
		
		return (int)studentAttendanceRate;
	}
	 
	@Override
	public Map<String,Object> createClassMap(Class c) {
		String classAttendanceRate=calculateClassAttendanceRate(c.getId());
		
		Map<String,Object> classMap=new HashMap<>();
		classMap.put("id", c.getId());
		classMap.put("modulecode", c.getModule().getCode());
		classMap.put("moduleid", c.getModule().getId());
		classMap.put("code", c.getCode());
		classMap.put("year", c.getAcademicPeriod().getYear());
		classMap.put("semester", c.getAcademicPeriod().getSemester());
		classMap.put("rate",classAttendanceRate);
		
		return classMap;
	}
	
	@Override
	public Map<String,Object> createStudentMap(Student s,int classId){
		int attendanceRate=calculateStudentAttendanceRate(classId, s.getId());
		int reachMinAttendanceOrNot=0;
		
		if(attendanceRate>=classRepo.getById(classId).getModule().getMinAttendance())
			reachMinAttendanceOrNot=1;
		
		Map<String,Object> studentMap=new HashMap<>();
		studentMap.put("id", s.getId());
		studentMap.put("studentId", s.getStudentId());
		studentMap.put("firstName", s.getFirstName());
		studentMap.put("lastName", s.getFirstName());
		studentMap.put("rate", String.valueOf(attendanceRate)+"%");
		studentMap.put("reachMinAttendanceOrNot", reachMinAttendanceOrNot);
		
		return studentMap;
	}
	
	
	
	
	// For MAchine Learning Model 1--predict class student pass or not
	@Override
	public int getStudentAbsenceTimes(int classId,int studentId) {
		List<Schedule> classScheduleUntilNow=this.getClassScheduleUntilNow(classId);
		int studentAbsenceTimes=(1-calculateStudentAttendanceRate(classId,studentId)/100)*classScheduleUntilNow.size();
		return studentAbsenceTimes;
	}
	
	@Override
	public int predictStudentPassOrNot(int classId,int studentId) {
		// get studentAbsenceTimes from last method,pass into python
		// get the python predicted result and save as predictStudentPassOrNot.
		int predictStudentPassOrNot=0;
		return predictStudentPassOrNot;
	}
	
	@Override
	public void updateClassStudentPredictedGrade(int classId) {
		List<Student> classStudents=getStudentsByClassId(classId);
		for(Enrolment e: enrolmentRepo.findAll()) {
			for(Student s:classStudents) {
				if(e.get_class().getId()==classId && e.getStudent().getId()==s.getId()) {
					if( predictStudentPassOrNot(classId,s.getId())==1)
						e.setPredictedPerformance("pass");
					else
						e.setPredictedPerformance("fail");
				}
			}
		}
	}
	
	// For MAchine Learning Model 2 -- predict class attendance rate
	@Override
	public List<Schedule> getClassFutureSchedule(int classId){
		List<Schedule> classFutureSchedule=new ArrayList<>();
		
		for(Schedule s:scheduleRepo.findAll()) {
			if(s.get_class().getId()==classId && s.getDate().isAfter(LocalDate.now()))
				classFutureSchedule.add(s);
		}
		
		return classFutureSchedule;
	}
	
	@Override
	public void updateClassPredictedAttendanceRate(int classId) {
		
		for(Schedule s: getClassFutureSchedule(classId)) {
			// pass s.getdate() into python and retrieve the prediction attendance rate
			// store in predictedAtteandanceRate;
			int predictedAtteandanceRate=0;
			s.setPredictedAttendance(predictedAtteandanceRate);
		}
		
	}

}
