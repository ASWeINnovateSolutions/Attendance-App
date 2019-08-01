package developer.aswin.com.nitc;

public class RollNo_Attendance {
    public String rollNo;
    private boolean isSelected;

    public RollNo_Attendance(String rollNo) {
        this.rollNo = rollNo;
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
