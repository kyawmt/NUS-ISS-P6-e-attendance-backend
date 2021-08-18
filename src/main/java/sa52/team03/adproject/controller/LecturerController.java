package sa52.team03.adproject.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.domain.StudentLeave;
import sa52.team03.adproject.service.LecturerService;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Enrolment;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/lecturer/")
public class LecturerController {
	
	@Autowired
	private LecturerService lservice;
	
	
	public List<Integer> getClassesID(){
		String username = "tan";
		Lecturer lec = lservice.getLecturerbyUsername(username); 
		return lservice.findClassIDbyLecID(lec.getId());
	}
	
	public Integer [] convertfromListToArray(List<Integer> o) {
		Integer [] id = (Integer []) o.toArray(new Integer[o.size()]);
		return id;
	}
	
	public List<Integer> getScheudleID(){
		List<Integer> classIDs = getClassesID();
		Integer [] classesID = convertfromListToArray(classIDs);
		List<Integer> allSchedules = new ArrayList<>();		
		for (int j = 0; j<classesID.length; j++) {
			List<Integer> s2 = lservice.findScheduleByClassID(classesID[j]);
			allSchedules.addAll(s2);
		}		
		return allSchedules;
	}
	
	@GetMapping("class/schedules")
	public List<Schedule> getSchedules(){
		List<Integer> allSchedules = getScheudleID();
		List<Schedule> allScheduleObject = new ArrayList<>();
		
		for (Integer i : allSchedules) {
			Schedule s = lservice.getSchedule(i);
			allScheduleObject.add(s);
		}
		return allScheduleObject;
		
	}
	
	
	@GetMapping(value = {"class/schedules/attendance/present","class/schedules/attendance/present/{ids}"})
	public List<Student> getAttendancePresent(@PathVariable Optional <Integer> ids){
		List<Schedule> allSchedules = getSchedules();
		int maxScheduleid = lservice.getmaxScheduleid();
		Schedule maxSchedule = lservice.getSchedule(maxScheduleid);
		int id = 0;
		
		if (ids.isPresent()) {
			id = ids.get();
		}
		else {
			id = maxScheduleid;
		}	

		List<Student> present = new ArrayList<>();	
		List<Student> absent = new ArrayList<>();
		List<Attendance> e = lservice.getAttendancebyScheudleID(id);
		
		for (Attendance a : e) {
			
			if (a.getSignIn() == null || a.getSignOut() == null || a.getSignIn() == false || a.getSignOut() == false)
				absent.add(a.getStudent());
			else
				present.add(a.getStudent());
			
		}
		return present;		
	}
	
	@GetMapping(value = {"class/schedules/attendance/absent","class/schedules/attendance/absent/{ids}"})
	public List<Student> getAttendanceAbsent(@PathVariable Optional <Integer> ids){
		List<Schedule> allSchedules = getSchedules();
		int maxScheduleid = lservice.getmaxScheduleid();
		Schedule maxSchedule = lservice.getSchedule(maxScheduleid);
		int id = 0;
		
		if (ids.isPresent()) {
			id = ids.get();
		}
		else {
			id = maxScheduleid;
		}	

		List<Student> absent = new ArrayList<>();	
		List<Attendance> e = lservice.getAttendancebyScheudleID(id);
		
		for (Attendance a : e) {
			if (a.getSignIn() == null || a.getSignOut() == null || a.getSignIn() == false || a.getSignOut() == false)
				absent.add(a.getStudent());
		}
		return absent;		
	}
	
	
	
	
	@GetMapping(value = {"class/schedules/attendance/overview","class/schedules/attendance/overview/{ids}"})
	public Map<String, Integer> showAttendanceRate(@PathVariable Optional <Integer> ids){
		List<Schedule> allSchedules = getSchedules();
		int maxScheduleid = lservice.getmaxScheduleid();
		Schedule maxSchedule = lservice.getSchedule(maxScheduleid);
		int id = 0;
		
		if (ids.isPresent()) {
			id = ids.get();
		}
		else {
			id = maxScheduleid;
		}
		List<Student> present = new ArrayList<>();
		List<Student> absent = new ArrayList<>();
		List<Attendance> e = lservice.getAttendancebyScheudleID(id);
		Schedule schedule = lservice.getSchedule(id);
		
		for (Attendance a : e) {
			if (a.getSignIn() == null || a.getSignOut() == null)
				absent.add(a.getStudent());
			else
				present.add(a.getStudent());
		}
		
		int totalSize = present.size() + absent.size();
		List<StudentLeave> sl = lservice.getAll();
		List<Student> absentwithValidReason = new ArrayList<>();
		List<StudentLeave> sl2 = new ArrayList<>();
		for (StudentLeave sls : sl) {
			if (sls.getStartDate().isBefore(schedule.getDate()) && sls.getEndDate().isAfter(schedule.getDate()) ||
					sls.getStartDate().isBefore(schedule.getDate()) && sls.getEndDate().isEqual(schedule.getDate()) || 
					sls.getStartDate().isEqual(schedule.getDate()) && sls.getEndDate().isAfter(schedule.getDate())||
					sls.getStartDate().isEqual(schedule.getDate()) && sls.getEndDate().isEqual(schedule.getDate()))
				sl2.add(sls);
		}
		
		for (StudentLeave sl22 : sl2) {
			if (sl22.getStatus().toString() == "APPROVED") {
				absentwithValidReason.add(sl22.getStudent());
				absent.remove(sl22.getStudent());
			}
			
		}	
		
		
		
		int absentwreason = absentwithValidReason.size()*100 / totalSize;
		int absentworeason = absent.size()*100/totalSize;
		int presentpercent = present.size()*100/totalSize;
		
		Map<String, Integer> overview = new HashMap();
		overview.put("AbsentwithvalidReason",absentwreason);
		overview.put("AbsentwithoutvalidReason",absentworeason);
		overview.put("Present",presentpercent);
		
		return overview;
		
	}

}
