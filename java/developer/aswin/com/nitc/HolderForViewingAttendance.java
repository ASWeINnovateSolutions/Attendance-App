package developer.aswin.com.nitc;

public class HolderForViewingAttendance {
    public String rollNo;
    public String classes_attended;

    public HolderForViewingAttendance(String rollNo, String classes_attended) {
        this.rollNo = rollNo;
        this.classes_attended = classes_attended;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getClasses_attended() {
        return classes_attended;
    }

    public void setClasses_attended(String classes_attended) {
        this.classes_attended = classes_attended;
    }
}
