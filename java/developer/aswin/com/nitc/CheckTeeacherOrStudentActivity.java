package developer.aswin.com.nitc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class CheckTeeacherOrStudentActivity extends AppCompatActivity implements View.OnClickListener {

    public static boolean TEACHER = false;
    public static boolean STUDENT = false;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_teacher_or_student);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_student:
                if (checked) {
                    // Pirates are the best
                    STUDENT = true;
                    TEACHER = false;
                    break;
                }
            case R.id.radio_teacher:
                if (checked) {
                    STUDENT = false;
                    TEACHER = true;
                    break;
                }
        }
        if (TEACHER)
            Toast.makeText(this, "TEACHER: " + TEACHER + " STUDENT: " + STUDENT, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "STUDENT: " + STUDENT + " TEACHER: " + TEACHER, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
      //  finish();
        if (TEACHER == false) {
            Intent intent = new Intent(CheckTeeacherOrStudentActivity.this, StudentRegistrationActivity.class);
         //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else{
            Intent intent = new Intent(CheckTeeacherOrStudentActivity.this, TeacherRegistrationActivity.class);
           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
