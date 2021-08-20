package sa52.team03.adproject.domain;

public class QRCodeData {

	private String studentUserName;
    private String signInSignOutId;
    private int scheduleId;
    private String option;

    public QRCodeData(String studentUserName, String signInSignOutId, int scheduleId, String option) {
        this.studentUserName = studentUserName;
        this.signInSignOutId = signInSignOutId;
        this.scheduleId = scheduleId;
        this.option = option;
    }

    public String getStudentUserName() {
        return studentUserName;
    }

    public void setStudentUserName(String studentUserName) {
        this.studentUserName = studentUserName;
    }

    public String getSignInSignOutId() {
        return signInSignOutId;
    }

    public void setSignInSignOutId(String signInSignOutId) {
        this.signInSignOutId = signInSignOutId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
    
}
