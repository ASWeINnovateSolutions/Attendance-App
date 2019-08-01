package developer.aswin.com.nitc;

public class Student {
    public final boolean teacherFlag;
    public String name;
    public String rollNo;
    // public String phone_no;
    public String email;
    public String personal_phone_no;
    public String faculty_phone_no;
    public String emergecy_phone_no1;
    public String emergecy_phone_no2;
    public String password;
    public String confirmPassword;

    public Student(String name, String rollNo, String email, String personal_phone_no, String faculty_phone_no, String emergecy_phone_no1, String emergecy_phone_no2, String passsword, String confirmPassword) {
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.personal_phone_no = personal_phone_no;
        this.faculty_phone_no = faculty_phone_no;
        this.emergecy_phone_no1 = emergecy_phone_no1;
        this.emergecy_phone_no2 = emergecy_phone_no2;
        this.password = passsword;
        this.teacherFlag = false;
        this.confirmPassword = confirmPassword;

    }

    public boolean isTeacherFlag() {
        return teacherFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonal_phone_no() {
        return personal_phone_no;
    }

    public void setPersonal_phone_no(String personal_phone_no) {
        this.personal_phone_no = personal_phone_no;
    }

    public String getFaculty_phone_no() {
        return faculty_phone_no;
    }

    public void setFaculty_phone_no(String faculty_phone_no) {
        this.faculty_phone_no = faculty_phone_no;
    }

    public String getEmergecy_phone_no1() {
        return emergecy_phone_no1;
    }

    public void setEmergecy_phone_no1(String emergecy_phone_no1) {
        this.emergecy_phone_no1 = emergecy_phone_no1;
    }

    public String getEmergecy_phone_no2() {
        return emergecy_phone_no2;
    }

    public void setEmergecy_phone_no2(String emergecy_phone_no2) {
        this.emergecy_phone_no2 = emergecy_phone_no2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
