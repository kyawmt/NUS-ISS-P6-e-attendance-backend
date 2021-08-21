package sa52.team03.adproject.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Enrolment;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.domain.StudentLeave;
import sa52.team03.adproject.service.AdminService;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.service.LecturerService;


@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/lecturer/")
public class LecturerController {
	
	@Autowired
	private LecturerService lecturerService;
	
	@Autowired
	private AdminService adminService;
	
	
	
	public List<Integer> getClassesID(){
		String username = "tan";
		Lecturer lec = lecturerService.getLecturerbyUsername(username); 
		return lecturerService.findClassIDbyLecID(lec.getId());
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
			List<Integer> s2 = lecturerService.findScheduleByClassID(classesID[j]);
			allSchedules.addAll(s2);
		}		
		return allSchedules;
	}
	
	@GetMapping("class/schedules")
	public List<Schedule> getSchedules(){
		List<Integer> allSchedules = getScheudleID();
		List<Schedule> allScheduleObject = new ArrayList<>();
		
		for (Integer i : allSchedules) {
			Schedule s = lecturerService.getSchedule(i);
			allScheduleObject.add(s);
		}
		
		return allScheduleObject;
		
	}
	
	
	@GetMapping(value = {"class/schedules/attendance/present/{ids}"})
	public List<Student> getAttendancePresent(@PathVariable int ids ){
		List<Schedule> allSchedules = getSchedules();
		Schedule selectedSchedule = lecturerService.getSchedule(ids);
		

		List<Student> present = new ArrayList<>();	
		List<Student> absent = new ArrayList<>();
		List<Attendance> e = lecturerService.getAttendancebyScheudleID(ids);
		
		for (Attendance a : e) {
			
			if (a.getSignIn() == null || a.getSignOut() == null || a.getSignIn() == false || a.getSignOut() == false)
				absent.add(a.getStudent());
			else
				present.add(a.getStudent());
			
		}
		return present;		
	}
	
	@GetMapping(value = {"class/schedules/attendance/absent/{ids}"})
	public List<Student> getAttendanceAbsent(@PathVariable int ids){
		List<Schedule> allSchedules = getSchedules();
		Schedule currentSchedule = lecturerService.getSchedule(ids);
		

		List<Student> absent = new ArrayList<>();	
		List<Attendance> e = lecturerService.getAttendancebyScheudleID(ids);
		
		for (Attendance a : e) {
			if (a.getSignIn() == null || a.getSignOut() == null || a.getSignIn() == false || a.getSignOut() == false)
				absent.add(a.getStudent());
		}
		return absent;		
	}
	
	
	
	
	@GetMapping(value = {"class/schedules/attendance/overview/{ids}"})
	public Map<String, Integer> showAttendanceRate(@PathVariable Integer ids){
		List<Schedule> allSchedules = getSchedules();
		
		List<Student> present = new ArrayList<>();
		List<Student> absent = new ArrayList<>();
		List<Attendance> e = lecturerService.getAttendancebyScheudleID(ids);
		Schedule schedule = lecturerService.getSchedule(ids);
		
		for (Attendance a : e) {
			if (a.getSignIn() == null || a.getSignOut() == null)
				absent.add(a.getStudent());
			else
				present.add(a.getStudent());
		}
		
		int totalSize = present.size() + absent.size();
		List<StudentLeave> sl = lecturerService.getAll();
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
	
	@GetMapping (value = {"/class/{classid}"})
	public void savePrediction (@PathVariable Integer classid) throws Exception {
		
		URL url = new URL(" http://127.0.0.1:5000/predict");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type","application/json; utf-8");
		con.setDoOutput(true);		
		
		List<Student> selectedStudents = adminService.getStudentsByClassId(classid);
		List<Integer> studentids = new ArrayList<>();
		for (Student s : selectedStudents) {
			studentids.add(s.getId());
		}
		
		Integer [] studentid = convertfromListToArray(studentids);
				
		List<Integer> studentAttendanceRate = new ArrayList<>();
		
		for (Integer a : studentids) {
			int b = adminService.calculateStudentAttendanceRate(classid, a);
			int c = 100-b;
			studentAttendanceRate.add(c);
		}
		
		JSONArray a1 = new JSONArray();
		for (Integer i : studentAttendanceRate) {
			a1.put(i);
		}
		
		try(OutputStream os = con.getOutputStream()) {
		    os.write(a1.toString().getBytes("UTF-8"));		
		}
		
		try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(con.getInputStream(), "utf-8"))) {
				  StringBuilder response = new StringBuilder();
				  String responseLine = null;
				  while ((responseLine = br.readLine()) != null) {
					  response.append(responseLine.trim());
				  }
				  String [] predict = response.toString().split(",");
				  
				  List<Enrolment> e = lecturerService.findEnrolmentByClassid(classid);
				  Enrolment [] es = (Enrolment []) e.toArray(new Enrolment[e.size()]);
				  
				  
				  for (int j = 0; j<predict.length; j++) {
					  if (es[j].getStudent().getId() == studentid[j]) {
						  String predict1 = predict[j].replaceAll("\\D+","");
						  if (es[j].getPredictedPerformance() == null) {
						  es[j].setPredictedPerformance(predict1);
						  lecturerService.saveEnrolment(es[j]);
					  }
					  }
				  }
		
		}
				
		
	}
	
	
	@GetMapping("/schedules")
	public List<Schedule> getAllSchedules(){
									
		return lecturerService.getSchedules();
	}
	
	@GetMapping("/schedules/{id}")
	public Schedule getSchedulebyId(@PathVariable int id) {
		return lecturerService.getSchedulebyId(id);
	}
	
	@GetMapping("/schedules/qrcode/{id}/{option}")
	public Map<String, String> getQRCodeData(@PathVariable int id, @PathVariable String option) {
		
		//Create Attendance data if not created
		lecturerService.createAttendanceData(id);
		
		Schedule schedule = lecturerService.getSchedulebyId(id);		
		Map<String, String> qrCodeData = new HashMap<String, String>();
							
		if(option.equals("signIn")) {			
			if(schedule.getSignInId()==null) {				
				String signInId =UUID.randomUUID().toString();
				long finishTime = Instant.now().toEpochMilli() + 90000;
				schedule.setSignInId(Long.toString(finishTime)+"_"+signInId);
				lecturerService.saveSchedule(schedule);
				qrCodeData.put("finishTime", Long.toString(finishTime));
				qrCodeData.put("qrCodeData", signInId+"_"+id+"_"+option);				
			}else {
				String[] data = schedule.getSignInId().split("_");				
				qrCodeData.put("finishTime", data[0].toString());
				qrCodeData.put("qrCodeData", data[1]+"_"+id+"_"+option);	
			}
		}else if(option.equals("signOut")) {
			if(schedule.getSignOutId()==null) {				
				String signOutId =UUID.randomUUID().toString();
				long finishTime = Instant.now().toEpochMilli() + 90000;
				schedule.setSignOutId(Long.toString(finishTime)+"_"+signOutId);
				lecturerService.saveSchedule(schedule);
				qrCodeData.put("finishTime", Long.toString(finishTime));
				qrCodeData.put("qrCodeData", signOutId+"_"+id+"_"+option);				
			}else {
				String[] data = schedule.getSignOutId().split("_");				
				qrCodeData.put("finishTime", data[0].toString());
				qrCodeData.put("qrCodeData", data[1]+"_"+id+"_"+option);	
			}
		}
				
		return qrCodeData;
		
	}
	
	@GetMapping("/schedules/attendance/{id}/{option}")
	public String getAttendanceForSchedule(@PathVariable int id, @PathVariable String option) {
		
		Schedule schedule = lecturerService.getSchedulebyId(id);
		
		ArrayList<Attendance> attendances = new ArrayList<Attendance>();
		
		attendances.addAll(schedule.getAttendances());
		
		int size = attendances.size();
		
		int attendance = 0;
		
		for(Attendance atten : attendances) {
			
			if(option.equals("signIn")) {
				
				if(atten.getSignIn()!=null) {
					attendance += atten.getSignIn()? 1:0;
				}
				
				
			}else if(option.equals("signOut")) {
				
				if(atten.getSignOut()!=null) {
					attendance += atten.getSignOut()? 1:0;
				}								
			}				
		}
		
		double attendanceRate = 0;
		
		if(size!=0) {
			
			attendanceRate = (double) attendance/size * 100;
		}
		
		String attendRateData = attendance + "/" + size + ", " + (int)attendanceRate + "%";		
				
		return attendRateData;
		
	}
	
	
	
	@GetMapping("/schedules/todaySchedule")
	public List<Schedule> getLecturerTodaySchedule(){
		
		Lecturer lecturer = lecturerService.getLecturerbyUsername("lecturer1@email.com");
									
		return lecturerService.getLecturerTodaySchedules(lecturer);
	}
	
	
	@GetMapping("/schedules/schedulesByRange/{startDateinMs}/{endDateinMs}")
	public List<Schedule> getLecturerSchedulesByRange(@PathVariable long startDateinMs, @PathVariable long endDateinMs){				
				
		LocalDate startDate = Instant.ofEpochMilli(startDateinMs).atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = Instant.ofEpochMilli(endDateinMs).atZone(ZoneId.systemDefault()).toLocalDate();
		
		Lecturer lecturer = lecturerService.getLecturerbyUsername("lecturer1@email.com");		
											
		return lecturerService.getLecturerSchedulesByRange(lecturer, startDate, endDate);
	}				
	
	@GetMapping("/classes/{lecturerId}")
	public List<Map<String,Object>> getClassInfoByLecturerId(@PathVariable int lecturerId){
		List<Map<String,Object>> classMapList = new ArrayList<>();
		List<Class> classes = lecturerService.getClassesByLecturerId(lecturerId);

		for(Class c : classes) {
				Map<String, Object> classMap = lecturerService.createClassMap(c);
				classMapList.add(classMap);
		}
		return classMapList;
	}
	
	@GetMapping("/class/{classId}")
	public Map<String, Object> getClassInfoByClassId(@PathVariable int classId) {
		Class c = lecturerService.getClassById(classId);
		return lecturerService.createClassMap(c);
	}
	
	@GetMapping("/classDates/{classId}")
	public List<Map<String, Object>> getClassAttendenceByClassId(@PathVariable int classId) {
		List<Map<String,Object>> classAttendanceMapList = new ArrayList<>();
		List<Schedule> schedules = lecturerService.getSchedulesByClassId(classId);
		
		for(Schedule s : schedules) {
			Map<String, Object> classAttendanceMap = lecturerService.createClassAttendanceMap(s);
			classAttendanceMapList.add(classAttendanceMap);
		}
		return classAttendanceMapList;
	}
	
}
