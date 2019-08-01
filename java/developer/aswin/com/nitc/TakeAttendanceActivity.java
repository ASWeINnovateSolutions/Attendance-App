package developer.aswin.com.nitc;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TakeAttendanceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_take_attendance);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        recyclerView = findViewById(R.id.recyclerView_student_list_for_taking_attendance);
        mAdapter3 = new RecyclerAttendanceStudentAdapter(this, RecyclerSubjectAdapter.mRollNo_attendances_downloaded);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerAttendanceStudentAdapter.checked_mArrayList_rollNo_attendance.clear();
        mAdapter3.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RecyclerAttendanceStudentAdapter.checked_mArrayList_rollNo_attendance.clear();
        mAdapter3.notifyDataSetChanged();
    }

    public void showAbsenteesAndCommit(View view) {
        if (RecyclerAttendanceStudentAdapter.checked_mArrayList_rollNo_attendance.size() <= 0) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("No students chosen")
                    .setMessage("Mark all students as absent")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Update the total no of classes that happened in the subject
                            DatabaseReference databaseReference2;

                            databaseReference2 = FirebaseDatabase.getInstance().getReference("TotalClasses")
                                    .child(RecyclerSubjectAdapter.subToTakeAttendance);

                            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String total_classes = dataSnapshot.child("total_classes").getValue().toString();
                                    int updatedTotalClasses = Integer.parseInt(total_classes);
                                    updatedTotalClasses++;
                                    dataSnapshot.getRef().child("total_classes").setValue(String.valueOf(updatedTotalClasses));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                    .getReference("Users")
                                    .child(FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid())
                                    .child("MySubjects")
                                    .child(RecyclerSubjectAdapter.subToTakeAttendance);


                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String total_classes_taken = dataSnapshot.child("total_classes_taken").getValue().toString();
                                    int upDatedAttendance = Integer.parseInt(total_classes_taken);
                                    upDatedAttendance++;
                                    dataSnapshot.getRef().child("total_classes_taken").setValue(String.valueOf(upDatedAttendance));

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }).show();
        } else {
            DatabaseReference databaseReference2;

            databaseReference2 = FirebaseDatabase.getInstance().getReference("TotalClasses")
                    .child(RecyclerSubjectAdapter.subToTakeAttendance);

            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String total_classes = dataSnapshot.child("total_classes").getValue().toString();
                    int updatedTotalClasses = Integer.parseInt(total_classes);
                    updatedTotalClasses++;
                    dataSnapshot.getRef().child("total_classes").setValue(String.valueOf(updatedTotalClasses));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(FirebaseAuth.getInstance()
                            .getCurrentUser().getUid())
                    .child("MySubjects")
                    .child(RecyclerSubjectAdapter.subToTakeAttendance);


            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String total_classes_taken = dataSnapshot.child("total_classes_taken").getValue().toString();
                    int upDatedAttendance = Integer.parseInt(total_classes_taken);
                    upDatedAttendance++;
                    dataSnapshot.getRef().child("total_classes_taken").setValue(String.valueOf(upDatedAttendance));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            for (int i = 0; i < RecyclerAttendanceStudentAdapter.checked_mArrayList_rollNo_attendance.size(); i++) {

                databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Subjects")
                        .child(RecyclerSubjectAdapter.subToTakeAttendance)
                        .child(RecyclerAttendanceStudentAdapter.checked_mArrayList_rollNo_attendance.get(i).getRollNo());

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String classes_attended = dataSnapshot.child("classes_attended").getValue().toString();
                        int updated_Classes_Attended = Integer.parseInt(classes_attended);
                        updated_Classes_Attended++;
                        dataSnapshot.getRef().child("classes_attended").setValue(String.valueOf(updated_Classes_Attended));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(this, "" + (i + 1) + " Attendances marked Successfully...!!!", Toast.LENGTH_SHORT).show();
                Log.e("PRESENT ROLL NO", RecyclerAttendanceStudentAdapter.checked_mArrayList_rollNo_attendance.size() + "");
            }
         //   listAllTheAbsenteesBeforeCommitting();
        }
    }

    private void upDateTotalAttendanceOfTeacher(DBSubjectPushData dbSubjectPushData) {

    }
}
