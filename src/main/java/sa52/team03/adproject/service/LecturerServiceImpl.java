package sa52.team03.adproject.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Enrolment;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.StudentLeave;
import sa52.team03.adproject.repo.AttendanceRepository;
import sa52.team03.adproject.repo.ClassRepository;
import sa52.team03.adproject.repo.LecturerRepository;
import sa52.team03.adproject.repo.ScheduleRepository;
import sa52.team03.adproject.repo.StudentLeaveRepository;
import sa52.team03.adproject.repo.StudentRepository;
import sa52.team03.adproject.repo.ModuleRepository;

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
	public List<Integer> findClassIDbyLecID(int id){
		return crepo.findClassIDbyLecID(id);
	}
	
	@Override
	public List<Integer> findScheduleByClassID(int id){
		return srepo.findScheduleIDByClassID(id);
	}
	
	@Override	
	public List<Integer> getAttendanceIDbyScheduleID (int id) {
		return arepo.getAttendanceIDbyScheduleID(id);
	}
	
	@Override
	public List<Attendance> getAttendancebyScheudleID (int id){
		return arepo.getAttendancebyScheduleID(id);
	}
	
	@Override
	public List<StudentLeave> getAll(){
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
	public List<Schedule> getSchedules(){
		return scheRepo.findAll();
	}
	
	@Override
	public Schedule getSchedulebyId(int id){
		return scheRepo.getById(id);
	}
	
	@Override
	public Schedule saveSchedule(Schedule schedule) {
		return scheRepo.save(schedule);
	}
			
	@Override
	public void createAttendanceData(int scheduleId) {
		
		Schedule schedule = scheRepo.getById(scheduleId);
		
		if(schedule.getAttendances().size()==0) {					
			
			Collection<Enrolment> enrolments = schedule.get_class().getEnrolments();
			
			for(Enrolment enrolment: enrolments) {
				
				Attendance attendance = new Attendance();
				attendance.setSchedule(schedule);
				attendance.setStudent(enrolment.getStudent());	
				attendance.setSignIn(false);
				attendance.setSignOut(false);
				attendRepo.save(attendance);
			}
						
		}
	}

}
