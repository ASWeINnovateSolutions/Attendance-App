package developer.aswin.com.nitc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class ViewAttendanceOfStudentsForTeacherAfterRetrievingFromFirebase extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter4;
    TextView view;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance_of_students_for_teacher_after_retrieving_from_firebase);
        view = findViewById(R.id.textView_Total_Classes_Taken);

        recyclerView = findViewById(R.id.final_recycler_view_for_teacherToViewAttendance);
        if (RecyclerAdapterToDisplaySubjectsForStudents.TURN==false) {
            mAdapter4 = new Recycler_Final_Adapter_To_View_Attendance_List(RecyclerViewAttendanceTeacherAdapter.holderForViewingAttendanceArrayList, ViewAttendanceOfStudentsForTeacherAfterRetrievingFromFirebase.this);
            view.setText("Total Classes Taken: " + RecyclerViewAttendanceTeacherAdapter.TOTAL_ATTENDANCE);
        }else{
            mAdapter4 = new Recycler_Final_Adapter_To_View_Attendance_List(RecyclerAdapterToDisplaySubjectsForStudents.holderForViewingAttendanceArrayListForStudents, ViewAttendanceOfStudentsForTeacherAfterRetrievingFromFirebase.this);
            view.setText("Total Classes Taken: "+RecyclerAdapterToDisplaySubjectsForStudents.TOTAL_ATTENDANCE_FOR_STUDENTS);
        }
//        FirebaseUser user = FirebaseUser
//        for(int i=0; i<RecyclerViewAttendanceTeacherAdapter.holderForViewingAttendanceArrayList.size(); i++){
//
//        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter4);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
