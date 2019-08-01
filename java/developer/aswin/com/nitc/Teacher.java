package developer.aswin.com.nitc;

import com.google.firebase.auth.FirebaseAuth;

public class Teacher {
    public final boolean teacherFlag;
    public String name;
    public String department;
    public String email;
    public String personal_phone_no1;
    public String personal_phone_no2;
    public String password;
    public String confirmPassword;

    public Teacher(String name, String department, String email, String personal_phone_no1, String personal_phone_no2, String password, String confirmPassword) {
        this.teacherFlag = true;
        this.name = name;
        this.email = email;
        this.department = department;
        this.personal_phone_no1 = personal_phone_no1;
        this.personal_phone_no2 = personal_phone_no2;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public static void logoutTeacher(){
        FirebaseAuth.getInstance().signOut();
    }
}
