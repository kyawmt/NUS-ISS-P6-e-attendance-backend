package sa52.team03.adproject.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.AttendanceSuccessData;
import sa52.team03.adproject.domain.QRCodeData;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.service.StudentService;
import sa52.team03.adproject.utils.TokenUtil;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/student/")
public class StudentController {
	
    @Autowired
    TokenUtil tokenUtil;
    
    @Autowired
    StudentService studentService;
	
	@PostMapping("/scanQRCode")
	public ResponseEntity<HttpStatus> scanQRCodeData(@RequestBody QRCodeData qrCodeData) {		
		
        Schedule schedule = studentService.getScheduleById(qrCodeData.getScheduleId());        
        String signInOutId = qrCodeData.getSignInSignOutId();
        String option = qrCodeData.getOption();   
                        		
		if(option.equals("signIn")) {
			if(schedule.getSignInId()!=null) {
                if( signInOutId.equals(schedule.getSignInId().split("_")[1]) && Long.parseLong(schedule.getSignInId().split("_")[0]) > Instant.now().toEpochMilli()){                
                    					
					return new ResponseEntity<>(HttpStatus.OK);					
				}			
			}				
		}else if(option.equals("signOut")) {			
			if(schedule.getSignOutId()!=null) {				
				if(signInOutId.equals(schedule.getSignOutId().split("_")[1]) && Long.parseLong(schedule.getSignOutId().split("_")[0]) > Instant.now().toEpochMilli()){
						
					return new ResponseEntity<>(HttpStatus.OK);					
				}			
			}			
		}		
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);				
	}
	
	@PostMapping("/takeAttendance")
	public ResponseEntity<HttpStatus> takeAttendance(@RequestBody QRCodeData qrCodeData) {		
		
        Schedule schedule = studentService.getScheduleById(qrCodeData.getScheduleId());        
        String signInOutId = qrCodeData.getSignInSignOutId();
		String option = qrCodeData.getOption();
				
        Attendance attendance = studentService.findAttendanceByScheduleAndStudent(schedule, studentService.findStudentByUserName(qrCodeData.getStudentUserName()));        
        
		if(option.equals("signIn")) {
			if(schedule.getSignInId()!=null) {
				if( signInOutId.equals(schedule.getSignInId().split("_")[1]) && Long.parseLong(schedule.getSignInId().split("_")[0]) > Instant.now().toEpochMilli()){	
					attendance.setSignIn(true);
                    studentService.saveAttendance(attendance);                    
                    return new ResponseEntity<>(HttpStatus.OK);					
				}			
			}				
		}else if(option.equals("signOut")) {			
			if(schedule.getSignOutId()!=null) {				
				if(signInOutId.equals(schedule.getSignOutId().split("_")[1]) && Long.parseLong(schedule.getSignOutId().split("_")[0]) > Instant.now().toEpochMilli()){
					attendance.setSignOut(true);
                    studentService.saveAttendance(attendance);                    
                    return new ResponseEntity<>(HttpStatus.OK);					
				}			
			}			
		}		
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);				
	}
	
    @GetMapping("class-module")
    public List<Map<String, Object>> getStudentClassModuleScheduleAttendance(HttpServletRequest request){
        String token = request.getHeader("JwtToken");
        String userName = tokenUtil.getUsernameFromToken(token);
        Student student = studentService.getStudentByUserName(userName);
        return studentService.getStudentClassModuleScheduleAttendance(student);
    }
    
    @GetMapping("/getAttendanceSuccess/{qrCodeData}")
	public AttendanceSuccessData getAttendanceSuccessData(HttpServletRequest request, @PathVariable String qrCodeData) {
    	  	 
    	String token = request.getHeader("JwtToken");
        String userName = tokenUtil.getUsernameFromToken(token);
        String[] qrCode = qrCodeData.split("_");
    	
    	Schedule schedule = studentService.getScheduleById(Integer.parseInt(qrCode[1]));
    	Student student = studentService.findStudentByUserName(userName);   	
    	
    	String studentId = "Student ID: " + student.getStudentId();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm:ss a");
    	String submissionTime = "Submission Time: " + LocalDateTime.now().format(formatter);   
    	String moduleCode = "Module Code: " + schedule.get_class().getModule().getCode();
    	String classDateTime = "Class Date & Time: " + schedule.getDate() + ", 09:00AM to 05:00PM";      	   	
    			
    	return new AttendanceSuccessData(studentId, submissionTime, moduleCode, classDateTime);
    	
    }
    
    
}
