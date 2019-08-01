package developer.aswin.com.nitc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegistrationActivity extends AppCompatActivity {

    EditText edt_student_name, edt_rollNo, edt_email, edt_personalStudentPhoneNUmber, edt_facultyAdvisorName, edt_facultyPhone, edt_emergency1, edt_emergency2;
    EditText edt_password, edt_confirmPassword;
    Button btn_submit;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration_student);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        edt_student_name = findViewById(R.id.edt_studentName);
        edt_email = findViewById(R.id.edt_email);
        edt_rollNo = findViewById(R.id.edt_roll);
        edt_facultyPhone = findViewById(R.id.edt_facultyAdvisorNumber);
        edt_facultyAdvisorName = findViewById(R.id.edt_facultyAdvisorName);
        edt_emergency1 = findViewById(R.id.emergency1);
        edt_emergency2 = findViewById(R.id.emergency2);
        edt_password = findViewById(R.id.password);
        edt_personalStudentPhoneNUmber = findViewById(R.id.edt_personalNumber);
        edt_confirmPassword = findViewById(R.id.confirmPassword);
        btn_submit = findViewById(R.id.btn_submit_student_reg);
        progressDialog = new ProgressDialog(this);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            //handle the already logged in user
        }
    }

    private void registerUser() {
        final String name = edt_student_name.getText().toString();
        final String email = edt_email.getText().toString();
        final String rollNo = edt_rollNo.getText().toString();
        final String facultyPhone = edt_facultyPhone.getText().toString();
        final String personal_no = edt_personalStudentPhoneNUmber.getText().toString();
        String facultyAdvisorName = edt_facultyAdvisorName.getText().toString();
        final String password = edt_password.getText().toString();
        final String confirmPassword = edt_confirmPassword.getText().toString();
        final String emergencyPhone1 = edt_emergency1.getText().toString();
        final String emergencyPhone2 = edt_emergency2.getText().toString();

        if (name.isEmpty()) {
            edt_student_name.requestFocus();
            Toast.makeText(this, "Empty Name Field!!", Toast.LENGTH_SHORT).show();
        } else if (personal_no.isEmpty()) {
            edt_personalStudentPhoneNUmber.requestFocus();
            Toast.makeText(this, "Empty Personal Field!!", Toast.LENGTH_SHORT).show();
        } else if (rollNo.isEmpty()) {
            edt_rollNo.requestFocus();
            Toast.makeText(this, "Empty rollNo Field!!", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            edt_email.requestFocus();
            Toast.makeText(this, "Empty e-mail Field!!", Toast.LENGTH_SHORT).show();
        } else if (facultyAdvisorName.isEmpty()) {
            edt_facultyAdvisorName.requestFocus();
            Toast.makeText(this, "Empty FA name field!!", Toast.LENGTH_SHORT).show();
        } else if (facultyPhone.isEmpty() && emergencyPhone1.isEmpty() && emergencyPhone2.isEmpty()) {
            Toast.makeText(this, "Atleast two emergency Ph. numbers are mandatory!!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() || confirmPassword.isEmpty()) {
            edt_password.setText("");
            edt_confirmPassword.setText("");
            edt_password.requestFocus();
            Toast.makeText(this, "Empty password field(s)!!", Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals(confirmPassword)) {
                progressDialog.setMessage("Connecting to database!!");
                progressDialog.setCancelable(false);
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.setMessage("Checking database status!!");
                        progressDialog.setCancelable(false);
                        if (task.isSuccessful()) {
                            progressDialog.setMessage("Connected Successfully!!");
                            progressDialog.setCancelable(false);
                            Student student = new Student(name, rollNo, email, personal_no, facultyPhone, emergencyPhone1, emergencyPhone2, password, confirmPassword);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(StudentRegistrationActivity.this, "Student successfully added to database!!", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        FirebaseAuth.getInstance().signOut();
                                        //   Toast.makeText(this, "Successfully Logged out!!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(StudentRegistrationActivity.this, LoginActivity.class));
                                        Intent intent = new Intent(StudentRegistrationActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(StudentRegistrationActivity.this, "Failed transacting with database", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(StudentRegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.setMessage("Disconnecting from Database");
                            progressDialog.dismiss();
                        }
                    }
                });
            } else {
                edt_password.setText("");
                edt_confirmPassword.setText("");
                edt_password.requestFocus();
                Toast.makeText(this, "Invalid Password!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}