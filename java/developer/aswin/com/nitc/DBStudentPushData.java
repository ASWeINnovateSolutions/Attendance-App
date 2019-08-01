package developer.aswin.com.nitc;

public class DBStudentPushData {
    public String studentName;
    public String studentRollNo;
    private boolean isSelected;


    public DBStudentPushData(String studentName, String studentRollNo) {
        this.studentName = studentName;
        this.studentRollNo = studentRollNo;
        isSelected = false;
    }

    public DBStudentPushData() {

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }
}