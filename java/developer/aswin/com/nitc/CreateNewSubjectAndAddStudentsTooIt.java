package developer.aswin.com.nitc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewSubjectAndAddStudentsTooIt extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    Button addStudentsToSubjectButton;
    EditText edt_NewSubName;
    DatabaseReference mFirebaseDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerAdapter.checked_mArrayList.clear();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_create_new_subject_and_add_students_too_it);
        recyclerView = findViewById(R.id.my_recycler_view);
        addStudentsToSubjectButton = findViewById(R.id.addStudents_toSubject);
        edt_NewSubName = findViewById(R.id.edt_newSubject);


        mAdapter = new RecyclerAdapter(TeacherHomeActivity.pushDataArrayListStudent);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("MySubjects").child("" + edt_NewSubName.getText().toString());


        addStudentsToSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_NewSubName.getText().toString().isEmpty()) {
                    Toast.makeText(CreateNewSubjectAndAddStudentsTooIt.this, "Please Enter a UNIQUE Subject name !!", Toast.LENGTH_SHORT).show();
                } else {
                    if (RecyclerAdapter.checked_mArrayList.size() <= 0) {
                        Toast.makeText(CreateNewSubjectAndAddStudentsTooIt.this, "Please add student(s) to the new subject!!", Toast.LENGTH_SHORT).show();
                    } else {
                        DBSubjectPushData dbSubjectPushData = new DBSubjectPushData("0");
                        TotalClassesTakenSoFarForParticularSubject mTotalClassesTakenSoFarForParticularSubject = new TotalClassesTakenSoFarForParticularSubject("0");
                        progressDialog = new ProgressDialog(CreateNewSubjectAndAddStudentsTooIt.this);

                        progressDialog.setCancelable(false);
                        progressDialog.setTitle("Connecting to server..");
                        progressDialog.setMessage("Don't disconnect from the internet !!");
                        progressDialog.show();
                        FirebaseDatabase.getInstance()
                                .getReference("Users")
                                .child(FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid())
                                .child("MySubjects")
                                .child("" + edt_NewSubName.getText().toString())
                                .setValue(dbSubjectPushData)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.setCancelable(true);
                                            progressDialog.dismiss();
                                            new AlertDialog.Builder(CreateNewSubjectAndAddStudentsTooIt.this)
                                                    .setTitle("Sucessfully Created")
                                                    .setMessage("" + edt_NewSubName.getText().toString() + " was created successfully !!")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    }).create().show();
                                            Toast.makeText(CreateNewSubjectAndAddStudentsTooIt.this, "" + edt_NewSubName.getText().toString() + " Created Successfully !!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        FirebaseDatabase.getInstance()
                                .getReference("TotalClasses")
                                .child(""+edt_NewSubName.getText().toString())
                                .setValue(mTotalClassesTakenSoFarForParticularSubject).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Log.e("IMPORTANT MESSAGE ","Successfully Created Total Classes under "+ edt_NewSubName.getText().toString());
                                }
                            }
                        });

                        for (int i = 0; i < RecyclerAdapter.checked_mArrayList.size(); i++) {
                            Log.e("CreateNewSubject  " + i + ") : ", RecyclerAdapter.checked_mArrayList.get(i).studentName);
                            ClassesAttended_PushedByTeacherToServer classesAttended_pushedByTeacherToServer = new ClassesAttended_PushedByTeacherToServer("0");
                            FirebaseDatabase.getInstance().getReference("Subjects")
                                    .child(edt_NewSubName.getText()
                                            .toString())
                                    .child("" + RecyclerAdapter.checked_mArrayList.get(i).studentRollNo)
                                    .setValue(classesAttended_pushedByTeacherToServer);
                        }
                        TeacherHomeActivity.retrieveAllCreatedSubjects();
                    }
                }
            }
        });
    }
}
