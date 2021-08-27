package sa52.team03.adproject.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sa52.team03.adproject.domain.Attendance;
import sa52.team03.adproject.domain.Class;
import sa52.team03.adproject.domain.Enrolment;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Schedule;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.domain.StudentLeave;
import sa52.team03.adproject.service.AdminService;
import sa52.team03.adproject.service.LecturerService;
import sa52.team03.adproject.utils.TokenUtil;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/lecturer/")
public class LecturerController {

	@Autowired
	private LecturerService lecturerService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private TokenUtil tokenUtil;



	@GetMapping("class/schedules")
	public List<Schedule> getSchedules(HttpServletRequest request) {
		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);
		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);		
		List<Class> classesID = lecturerService.getClassesByLecturerId(lecturer.getId());		
		List<Schedule> allSchedules = new ArrayList<>();
		List<Schedule> selectedSchedules = new ArrayList<>();

		for (Class c : classesID) {
			List<Schedule> a = lecturerService.findScheduleByClassID(c.getId());
			allSchedules.addAll(a);
		}
		
		for (Schedule s : allSchedules) {
			if (s.getSignInId() != null || s.getSignOutId() != null)
				selectedSchedules.add(s);
		}

		return selectedSchedules;

	}
	
	@GetMapping("maxschedule")
	public Map<String,Integer> getMaxSchedule(HttpServletRequest request) {
		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);
		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);
		List<Class> classesID = lecturerService.getClassesByLecturerId(lecturer.getId());		
		List<Schedule> allSchedules = new ArrayList<>();
		for (Class c : classesID) {
			List<Schedule> a = lecturerService.findScheduleByClassID(c.getId());
			allSchedules.addAll(a);
		}
		List<Integer> sIDs = new ArrayList<>();
		
		for (Schedule s : allSchedules) {
			if (s.getSignInId() != null || s.getSignOutId() != null)
				sIDs.add(s.getId());
		}
		
		int a = Collections.max(sIDs);
		
		
		Map<String, Integer> maxSchedule = new HashMap<>();
		maxSchedule.put("maxID", a);
		
		return maxSchedule;
	}
	
	@GetMapping (value = {"schedule/name/{ids}"})
	public Map<String, String> getNameandDate(@PathVariable int ids, HttpServletRequest request){
		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);
		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);		
		List<Class> classesID = lecturerService.getClassesByLecturerId(lecturer.getId());		
		List<Schedule> allSchedules = new ArrayList<>();
		for (Class c : classesID) {
			List<Schedule> a = lecturerService.findScheduleByClassID(c.getId());
			allSchedules.addAll(a);
		}
		
		Schedule selectedSchedule = lecturerService.getSchedule(ids);
		String moduleName = selectedSchedule.get_class().getModule().getName();
		String date = selectedSchedule.getDate().toString();
		Map<String, String> datesandname = new HashMap<>();
		datesandname.put("scheduleDate", date);
		datesandname.put("moduleName", moduleName);
		
		return datesandname;
		
	}



	@GetMapping(value = { "class/schedules/attendance/present/{ids}" })
	public List<Student> getAttendancePresent(@PathVariable int ids, HttpServletRequest request) {
		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);
		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);		
		List<Class> classesID = lecturerService.getClassesByLecturerId(lecturer.getId());		
		List<Schedule> allSchedules = new ArrayList<>();

		for (Class c : classesID) {
			List<Schedule> a = lecturerService.findScheduleByClassID(c.getId());
			allSchedules.addAll(a);
		}
		
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

	@GetMapping(value = { "class/schedules/attendance/absent/{ids}" })
	public List<Student> getAttendanceAbsent(@PathVariable int ids, HttpServletRequest request) {
		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);
		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);		
		List<Class> classesID = lecturerService.getClassesByLecturerId(lecturer.getId());		
		List<Schedule> allSchedules = new ArrayList<>();

		for (Class c : classesID) {
			List<Schedule> a = lecturerService.findScheduleByClassID(c.getId());
			allSchedules.addAll(a);
		}
		
		Schedule currentSchedule = lecturerService.getSchedule(ids);

		List<Student> absent = new ArrayList<>();
		List<Attendance> e = lecturerService.getAttendancebyScheudleID(ids);

		for (Attendance a : e) {
			if (a.getSignIn() == null || a.getSignOut() == null || a.getSignIn() == false || a.getSignOut() == false)
				absent.add(a.getStudent());
		}
		return absent;
	}

	@GetMapping(value = { "class/schedules/attendance/overview/{ids}" })
	public Map<String, Integer> showAttendanceRate(@PathVariable Integer ids, HttpServletRequest request) {
		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);
		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);		
		List<Class> classesID = lecturerService.getClassesByLecturerId(lecturer.getId());		
		List<Schedule> allSchedules = new ArrayList<>();

		for (Class c : classesID) {
			List<Schedule> a = lecturerService.findScheduleByClassID(c.getId());
			allSchedules.addAll(a);
		}
		

		List<Student> present = new ArrayList<>();
		List<Student> absent = new ArrayList<>();
		List<Attendance> e = lecturerService.getAttendancebyScheudleID(ids);
		Schedule schedule = lecturerService.getSchedule(ids);

		for (Attendance a : e) {
			if (a.getSignIn() == false|| a.getSignOut() == false)
				absent.add(a.getStudent());
			else
				present.add(a.getStudent());
		}

		int totalSize = present.size() + absent.size();
		System.out.println(totalSize);
		List<StudentLeave> sl = lecturerService.getAll();
		List<Student> absentwithValidReason = new ArrayList<>();
		List<StudentLeave> sl2 = new ArrayList<>();
		for (StudentLeave sls : sl) {
			if (sls.getStartDate().isBefore(schedule.getDate()) && sls.getEndDate().isAfter(schedule.getDate())
					|| sls.getStartDate().isBefore(schedule.getDate()) && sls.getEndDate().isEqual(schedule.getDate())
					|| sls.getStartDate().isEqual(schedule.getDate()) && sls.getEndDate().isAfter(schedule.getDate())
					|| sls.getStartDate().isEqual(schedule.getDate()) && sls.getEndDate().isEqual(schedule.getDate()))
				sl2.add(sls);
		}

		for (StudentLeave sl22 : sl2) {
			if (sl22.getStatus().toString() == "APPROVED") {
				absentwithValidReason.add(sl22.getStudent());
				absent.remove(sl22.getStudent());
			}
			
		}	
		
		String moduleName = schedule.get_class().getModule().getName();
		LocalDate scheduleDate = schedule.getDate();
		
		
		int absentwreason = absentwithValidReason.size()*100 / totalSize;
		int absentworeason = absent.size()*100/totalSize;
		int presentpercent = present.size()*100/totalSize;
		
		
		
		Map<String, Integer> overview = new HashMap();
		overview.put("AbsentwithvalidReason",absentwreason);
		overview.put("AbsentwithoutvalidReason",absentworeason);
		overview.put("Present",presentpercent);
		
		
		return overview;

	}
	@GetMapping(value = {"schedule/{ids}"})
	public Map<String, String> getnameanddate(@PathVariable Integer ids, HttpServletRequest request){
		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);
		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);
		Schedule schedule = lecturerService.getSchedule(ids);
		String moduleName = schedule.get_class().getModule().getName();
		String scheduleDate = schedule.getDate().toString();
		
		Map<String, String> overview = new HashMap();
		overview.put("moduleName",moduleName);
		overview.put("scheduleDate",scheduleDate);
		
		return overview;
	}

	@GetMapping("/schedules")
	public List<Schedule> getAllSchedules() {

		return lecturerService.getSchedules();
	}

	@GetMapping("/schedules/{id}")
	public Schedule getSchedulebyId(@PathVariable int id) {
		return lecturerService.getSchedulebyId(id);
	}

	@GetMapping("/schedules/qrcode/{id}/{option}")
	public Map<String, String> getQRCodeData(@PathVariable int id, @PathVariable String option) {

		// Create Attendance data if not created
		lecturerService.createAttendanceData(id);

		Schedule schedule = lecturerService.getSchedulebyId(id);
		Map<String, String> qrCodeData = new HashMap<String, String>();

		if (option.equals("signIn")) {
			if (schedule.getSignInId() == null) {
				String signInId = UUID.randomUUID().toString();
				long finishTime = Instant.now().toEpochMilli() + 90000;
				schedule.setSignInId(Long.toString(finishTime) + "_" + signInId);
				lecturerService.saveSchedule(schedule);
				qrCodeData.put("finishTime", Long.toString(finishTime));
				qrCodeData.put("qrCodeData", signInId + "_" + id + "_" + option);
			} else {
				String[] data = schedule.getSignInId().split("_");
				qrCodeData.put("finishTime", data[0].toString());
				qrCodeData.put("qrCodeData", data[1] + "_" + id + "_" + option);
			}
		} else if (option.equals("signOut")) {
			if (schedule.getSignOutId() == null) {
				String signOutId = UUID.randomUUID().toString();
				long finishTime = Instant.now().toEpochMilli() + 90000;
				schedule.setSignOutId(Long.toString(finishTime) + "_" + signOutId);
				lecturerService.saveSchedule(schedule);
				qrCodeData.put("finishTime", Long.toString(finishTime));
				qrCodeData.put("qrCodeData", signOutId + "_" + id + "_" + option);
			} else {
				String[] data = schedule.getSignOutId().split("_");
				qrCodeData.put("finishTime", data[0].toString());
				qrCodeData.put("qrCodeData", data[1] + "_" + id + "_" + option);
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

		for (Attendance atten : attendances) {

			if (option.equals("signIn")) {

				if (atten.getSignIn() != null) {
					attendance += atten.getSignIn() ? 1 : 0;
				}

			} else if (option.equals("signOut")) {

				if (atten.getSignOut() != null) {
					attendance += atten.getSignOut() ? 1 : 0;
				}
			}
		}

		double attendanceRate = 0;

		if (size != 0) {

			attendanceRate = (double) attendance / size * 100;
		}

		String attendRateData = attendance + "/" + size + ", " + (int) attendanceRate + "%";

		return attendRateData;

	}

	@GetMapping("/schedules/todaySchedule")
	public List<Schedule> getLecturerTodaySchedule(HttpServletRequest request) {

		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);

		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);

		return lecturerService.getLecturerTodaySchedules(lecturer);
	}

	@GetMapping("/schedules/schedulesByRange/{startDateinMs}/{endDateinMs}")
	public List<Schedule> getLecturerSchedulesByRange(@PathVariable long startDateinMs, @PathVariable long endDateinMs,
			HttpServletRequest request) {

		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);

		LocalDate startDate = Instant.ofEpochMilli(startDateinMs).atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = Instant.ofEpochMilli(endDateinMs).atZone(ZoneId.systemDefault()).toLocalDate();

		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);

		return lecturerService.getLecturerSchedulesByRange(lecturer, startDate, endDate);
	}

	@GetMapping("/classes")
	public List<Map<String, Object>> getClassInfoByLecturerId(HttpServletRequest request) {
		String token = request.getHeader("JwtToken");
		String userName = tokenUtil.getUsernameFromToken(token);

		Lecturer lecturer = lecturerService.getLecturerbyUsername(userName);
		
		List<Map<String, Object>> classMapList = new ArrayList<>();
		List<Class> classes = lecturerService.getClassesByLecturerId(lecturer.getId());

		for (Class c : classes) {
			Map<String, Object> classMap = lecturerService.createSideBarClassMap(c);
			classMapList.add(classMap);
		}
		
		return classMapList; 
	}	

	@GetMapping("/class/{classId}")
	public Map<String, Object> getClassInfoByClassId(@PathVariable int classId) throws Exception{	
		
		lecturerService.savePrediction(classId);
		Class c = lecturerService.getClassById(classId);
		return lecturerService.createClassMap(c);
	}

	@GetMapping("/classDates/{classId}")
	public List<Map<String, Object>> getClassAttendenceByClassId(@PathVariable int classId) {
		      
		
		List<Map<String, Object>> classAttendanceMapList = new ArrayList<>();
		List<Schedule> schedules = lecturerService.getSchedulesByClassId(classId);

		for (Schedule s : schedules) {
			Map<String, Object> classAttendanceMap = lecturerService.createClassAttendanceMap(s);
			classAttendanceMapList.add(classAttendanceMap);
		}
		return classAttendanceMapList;
	}

	@GetMapping("/prediction/{classId}/{index}")
	public List<Map<String, Object>> getStudentPredictedAttendance (@PathVariable int classId, @PathVariable int index) throws Exception {
		
		adminService.updateClassPredictedAttendanceRate(classId);

		List<Map<String, Object>> studentMapList = new ArrayList<>();
		List<Integer> studentIds = lecturerService.getStudentIdByPredictedPerformance(classId, String.valueOf(index));
		List<Schedule> schedules = lecturerService.getSchedulesByClassId(classId);

		for (int id : studentIds) {
			Map<String, Object> studentMap = lecturerService.createStudentMap(id, schedules);
			studentMapList.add(studentMap);
		}

		return studentMapList;
	}
	
	
	//save grade prediction
	@GetMapping("/prediction/savegrade")
	public void savePredictionforgrades() throws Exception{
		List<Integer> classid = lecturerService.getallClassID();
		for (Integer i: classid)
			lecturerService.savePrediction(i);
	}
	
	//save attendance prediction
	@GetMapping("/prediction/saveattendance")
	public void saveAttendance() throws Exception{
		List<Integer> classid = lecturerService.getallClassID();
		for (Integer i: classid)
			adminService.updateClassPredictedAttendanceRate(i);
	}
	
	

}
