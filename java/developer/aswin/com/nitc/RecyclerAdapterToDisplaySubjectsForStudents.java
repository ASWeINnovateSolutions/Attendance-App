package developer.aswin.com.nitc;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerAdapterToDisplaySubjectsForStudents extends RecyclerView.Adapter<RecyclerAdapterToDisplaySubjectsForStudents.MyClassViewHolderForSubjectsForStudents> {

    Context mContext;
    public static boolean TURN = false;
    public static String SELECTED_SUBJECT_TO_VIEW_ATTENDANCE;
    public static ArrayList<HolderForViewingAttendance> holderForViewingAttendanceArrayListForStudents;
    public static String TOTAL_ATTENDANCE_FOR_STUDENTS;


    public ArrayList<SubjectName> subjectNames;

    public RecyclerAdapterToDisplaySubjectsForStudents(Context mContext, ArrayList<SubjectName> subjectNames) {
        this.mContext = mContext;
        this.subjectNames = subjectNames;
    }

    @NonNull
    @Override
    public MyClassViewHolderForSubjectsForStudents onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_subject_itemrow, viewGroup, false);
        return new MyClassViewHolderForSubjectsForStudents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyClassViewHolderForSubjectsForStudents myClassViewHolderForSubjectsForStudents, int i) {
        myClassViewHolderForSubjectsForStudents.tv.setText(subjectNames.get(i).getSubName());
        myClassViewHolderForSubjectsForStudents.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holderForViewingAttendanceArrayListForStudents = new ArrayList<>();
                Toast.makeText(mContext, "" + myClassViewHolderForSubjectsForStudents.tv.getText().toString(), Toast.LENGTH_SHORT).show();
                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance()
                        .getReference("TotalClasses")
                        .child("" + myClassViewHolderForSubjectsForStudents.tv.getText().toString());
                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        TOTAL_ATTENDANCE_FOR_STUDENTS = dataSnapshot.child("total_classes").getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                SELECTED_SUBJECT_TO_VIEW_ATTENDANCE = myClassViewHolderForSubjectsForStudents.tv.getText().toString();

                DatabaseReference databaseReference3 = FirebaseDatabase.getInstance()
                        .getReference("Subjects")
                        .child(myClassViewHolderForSubjectsForStudents.tv.getText().toString());
                holderForViewingAttendanceArrayListForStudents = new ArrayList<>();
                databaseReference3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String rollNo = ds.getKey();
                            String classes_attended = ds.child("classes_attended").getValue(String.class);
                            if (rollNo.equals(HomeActivity.ROLLNO_OF_STUDENT)) {
                                HolderForViewingAttendance holderForViewingAttendance = new HolderForViewingAttendance(rollNo, "Classes Attended " + classes_attended);
                                holderForViewingAttendanceArrayListForStudents.add(holderForViewingAttendance);
                            }
                        }
                        rollNoDataReadyForViewingAttendance();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    private void rollNoDataReadyForViewingAttendance() {
        Toast.makeText(mContext, "All Set!!", Toast.LENGTH_SHORT).show();
        TURN = true;
        RecyclerViewAttendanceTeacherAdapter.TURN = false;

        mContext.startActivity(new Intent(mContext, ViewAttendanceOfStudentsForTeacherAfterRetrievingFromFirebase.class));
    }

    @Override
    public int getItemCount() {
        return subjectNames.size();
    }

    public class MyClassViewHolderForSubjectsForStudents extends RecyclerView.ViewHolder {
        TextView tv;

        public MyClassViewHolderForSubjectsForStudents(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView_Subject);
        }
    }
}
