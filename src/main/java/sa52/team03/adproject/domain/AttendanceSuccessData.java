package sa52.team03.adproject.domain;

public class AttendanceSuccessData {
	
	private String studentId;
    private String submissionTime;
    private String moduleCode;
    private String classDateTime;

    public AttendanceSuccessData() {
    }

    public AttendanceSuccessData(String studentId, String submissionTime, String moduleCode, String classDateTime) {
        this.studentId = studentId;
        this.submissionTime = submissionTime;
        this.moduleCode = moduleCode;
        this.classDateTime = classDateTime;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getClassDateTime() {
        return classDateTime;
    }

    public void setClassDateTime(String classDateTime) {
        this.classDateTime = classDateTime;
    }

}
