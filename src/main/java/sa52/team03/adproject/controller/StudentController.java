package sa52.team03.adproject.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.QRCodeData;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.repo.AttendanceRepository;
import sa52.team03.adproject.repo.ScheduleRepository;
import sa52.team03.adproject.repo.StudentRepository;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/student/")
public class StudentController {
	
	@Autowired
	AttendanceRepository attendRepo;
	
	@Autowired
	ScheduleRepository scheRepo;
	
	@Autowired
	StudentRepository studRepo;
	
	@PostMapping("/scanQRCode")
	public ResponseEntity<HttpStatus> scanQRCodeData(@RequestBody QRCodeData qrCodeData) {		
				
		Schedule schedule = scheRepo.getById(qrCodeData.getScheduleId());		
		String signInOutId = qrCodeData.getSignInSignOutId();
		String option = qrCodeData.getOption();
				
		//Attendance attendance = attendRepo.findByScheduleAndStudent(schedule, studRepo.findByUserName(qrCodeData.getStudentUserName()));		
		
		if(option.equals("signIn")) {
			if(schedule.getSignInId()!=null) {
				if( signInOutId.equals(schedule.getSignInId().split("_")[1]) && Long.parseLong(schedule.getSignInId().split("_")[0]) > Instant.now().toEpochMilli()){					
					//attendance.setSignIn(true);
					//attendRepo.save(attendance);					
					return new ResponseEntity<>(HttpStatus.OK);					
				}			
			}				
		}else if(option.equals("signOut")) {			
			if(schedule.getSignOutId()!=null) {				
				if(signInOutId.equals(schedule.getSignOutId().split("_")[1]) && Long.parseLong(schedule.getSignOutId().split("_")[0]) > Instant.now().toEpochMilli()){
					//attendance.setSignOut(true);
					//attendRepo.save(attendance);					
					return new ResponseEntity<>(HttpStatus.OK);					
				}			
			}			
		}		
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);				
	}
	
	@PostMapping("/takeAttendance")
	public ResponseEntity<HttpStatus> takeAttendance(@RequestBody QRCodeData qrCodeData) {		
				
		Schedule schedule = scheRepo.getById(qrCodeData.getScheduleId());		
		String signInOutId = qrCodeData.getSignInSignOutId();
		String option = qrCodeData.getOption();
				
		Attendance attendance = attendRepo.findByScheduleAndStudent(schedule, studRepo.findByUserName(qrCodeData.getStudentUserName()));		
		
		if(option.equals("signIn")) {
			if(schedule.getSignInId()!=null) {
				if( signInOutId.equals(schedule.getSignInId().split("_")[1]) && Long.parseLong(schedule.getSignInId().split("_")[0]) > Instant.now().toEpochMilli()){					
					attendance.setSignIn(true);
					attendRepo.save(attendance);					
					return new ResponseEntity<>(HttpStatus.OK);					
				}			
			}				
		}else if(option.equals("signOut")) {			
			if(schedule.getSignOutId()!=null) {				
				if(signInOutId.equals(schedule.getSignOutId().split("_")[1]) && Long.parseLong(schedule.getSignOutId().split("_")[0]) > Instant.now().toEpochMilli()){
					attendance.setSignOut(true);
					attendRepo.save(attendance);					
					return new ResponseEntity<>(HttpStatus.OK);					
				}			
			}			
		}		
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);				
	}
}
