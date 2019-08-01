package developer.aswin.com.nitc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherRegistrationActivity extends AppCompatActivity {
    EditText edt_teacher_name, edt_personal_phone2, edt_teacherEmail, edt_department, edt_personal_phone1, edt_passwordTeacher, edt_confirmPasswordTeacher;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_teacher_registration);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edt_teacher_name = findViewById(R.id.edt_teacherName);
        edt_teacherEmail = findViewById(R.id.edt_teacherEmail);
        edt_personal_phone1 = findViewById(R.id.edt_teacherPhoneNumber);
        edt_personal_phone2 = findViewById(R.id.edt_teacherOptionalPhone);
        edt_department = findViewById(R.id.edt_teacherDepartment);
        edt_passwordTeacher = findViewById(R.id.passwordTeacher);
        edt_confirmPasswordTeacher = findViewById(R.id.confirmPasswordTeacher);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(TeacherRegistrationActivity.this);

    }

    public void requestAccount(View view) {
        final String name = edt_teacher_name.getText().toString();
        final String email = edt_teacherEmail.getText().toString();
        final String personal_phone1 = edt_personal_phone1.getText().toString();
        String personal_phone2 = edt_personal_phone2.getText().toString();
        final String facultyDepartment = edt_department.getText().toString();
        final String password = edt_passwordTeacher.getText().toString();
        final String confirmPassword = edt_confirmPasswordTeacher.getText().toString();

        if (personal_phone2.isEmpty()) {
            personal_phone2 = "no number";
        }

        if (name.isEmpty()) {
            edt_teacher_name.requestFocus();
            Toast.makeText(this, "Empty Name Field!!", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            edt_teacherEmail.requestFocus();
            Toast.makeText(this, "Empty e-mail Field!!", Toast.LENGTH_SHORT).show();
        } else if (personal_phone1.isEmpty()) {
            edt_personal_phone1.requestFocus();
            Toast.makeText(this, "Empty Phone no. field!!", Toast.LENGTH_SHORT).show();
        } else if (facultyDepartment.isEmpty()) {
            edt_department.requestFocus();
            Toast.makeText(this, "Empty department field(s)!!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() || confirmPassword.isEmpty()) {
            edt_passwordTeacher.setText("");
            edt_confirmPasswordTeacher.setText("");
            edt_passwordTeacher.requestFocus();
            Toast.makeText(this, "Empty password field(s)!!", Toast.LENGTH_SHORT).show();
        } else {
            if (password.length() < 8) {
                Toast.makeText(this, "Min 8 characters required!!", Toast.LENGTH_SHORT).show();
            } else if (password.equals(confirmPassword)) {
                // Toast.makeText(this, "Registration Successfull!", Toast.LENGTH_SHORT).show();
                if (password.equals(confirmPassword)) {
                    progressDialog.setMessage("Connecting to database!!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    final String finalPersonal_phone = personal_phone2;
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.setMessage("Checking database status!!");
                            progressDialog.setCancelable(false);
                            if (task.isSuccessful()) {
                                progressDialog.setMessage("Connected Successfully!!");
                                progressDialog.setCancelable(false);
                                Teacher teacher = new Teacher(name, facultyDepartment, email, personal_phone1, finalPersonal_phone, password, confirmPassword);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance()
                                                .getCurrentUser().getUid()).setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(TeacherRegistrationActivity.this, "Teacher successfully added to database!!", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(TeacherRegistrationActivity.this, LoginActivity.class);
                                            FirebaseAuth.getInstance().signOut();
                                         //   Toast.makeText(this, "Successfully Logged out!!", Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(TeacherRegistrationActivity.this, LoginActivity.class));
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(TeacherRegistrationActivity.this, "Failed transacting with database", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(TeacherRegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                progressDialog.setMessage("Disconnecting from Database");
                                progressDialog.dismiss();
                            }
                        }
                    });
                } else {
                    edt_passwordTeacher.setText("");
                    edt_confirmPasswordTeacher.setText("");
                    edt_passwordTeacher.requestFocus();

                    Toast.makeText(this, "Password doesn't match!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
