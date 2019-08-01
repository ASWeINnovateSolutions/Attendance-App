package developer.aswin.com.nitc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class DisplaySubjectsForStudentsToChooseToViewAttendance extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_subjects_for_students_to_choose_to_view_attendance);
        recyclerView = findViewById(R.id.recyclerView_view_Subjects_for_Attendance_Checking);

        mAdapter4 = new RecyclerAdapterToDisplaySubjectsForStudents(DisplaySubjectsForStudentsToChooseToViewAttendance.this ,HomeActivity.mArrayListSubjectName);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter4);
    }
}
