package developer.aswin.com.nitc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherHomeActivity extends AppCompatActivity {

    private Button button;
    final Context context = this;
    public static DatabaseReference mDatabaseReference;
    public static ArrayList<DBStudentPushData> pushDataArrayListStudent;
    public static ArrayList<SubjectName> pushSubjectNameArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        button = findViewById(R.id.btn_viewAttendance);
        retrieveAllCreatedSubjects();
    }

    public void teacherLogout(View view) {
        Teacher.logoutTeacher();
        finish();
        startActivity(new Intent(TeacherHomeActivity.this, LoginActivity.class));
    }


    public void addStudent(View view) {
        // get custom.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.custom, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final EditText name = (EditText) promptView.findViewById(R.id.edtTextName);
        final EditText rollNo = (EditText) promptView.findViewById(R.id.edtTextRollno);

        // setup a dialog window
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        // get user input and set it to result
                        final String name_student = name.getText().toString();
                        String roll_student = rollNo.getText().toString();
                        if (name_student.isEmpty() || roll_student.isEmpty()) {
                            Toast.makeText(context, "Both name and roll no fields are mandatory !!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            final ProgressDialog progressDialog = new ProgressDialog(context);
                            progressDialog.setTitle("Connecting to App Database...");
                            progressDialog.setMessage("Ensure internet connection.");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            DBStudentPushData pushData = new DBStudentPushData(name_student, roll_student);
                            FirebaseDatabase.getInstance().getReference("Students").child("" + roll_student.toUpperCase())
                                    .setValue(pushData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.setCancelable(true);
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Succeessfully Added " + name_student + " to App DB", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        Toast.makeText(context, "Hi its working !!" + name_student, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    public void teacherHome(View view) {

    }

    public void createSubject(View view) {
        //Toast.makeText(context, "When Creating Subject try to add", Toast.LENGTH_SHORT).show();
        retrieveAllStudents();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setMessage("Please try to add year and semester to the subject name to maintain uniqueness to subject!!")
                .setTitle("Maintain Uniqueness")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(TeacherHomeActivity.this, CreateNewSubjectAndAddStudentsTooIt.class));
                    }
                }).show();

    }

    public static void retrieveAllStudents() {
        pushDataArrayListStudent = new ArrayList<DBStudentPushData>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Students");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String student_name = ds.child("studentName").getValue().toString();
                    String roll_no = ds.child("studentRollNo").getValue().toString();
                    DBStudentPushData dbStudentPushData = new DBStudentPushData(student_name, roll_no);
                    pushDataArrayListStudent.add(dbStudentPushData);
                    Log.e("ArrayList: ", student_name + " inserted");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void retrieveAllCreatedSubjects() {
        pushSubjectNameArrayList = new ArrayList<>();
        DatabaseReference mDatabaseReference2 = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("MySubjects");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String subName = ds.getKey();
                    SubjectName subjectName = new SubjectName(subName);
                    pushSubjectNameArrayList.add(subjectName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference2.addListenerForSingleValueEvent(valueEventListener);
        mDatabaseReference2.removeEventListener(valueEventListener);
    }

    public void takeAttendanceActivity(View view) {
        startActivity(new Intent(TeacherHomeActivity.this, AttendanceActivity.class));
    }

    public void viewAttendanceForTeacher(View view) {
        startActivity(new Intent(TeacherHomeActivity.this, ViewAttendanceStatusForTeachersActivity.class));
    }

    public void takeToLocationActivity(View view) {
        startActivity(new Intent(TeacherHomeActivity.this, LocationListActivity.class));
    }
}