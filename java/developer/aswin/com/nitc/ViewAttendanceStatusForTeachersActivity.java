package developer.aswin.com.nitc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ViewAttendanceStatusForTeachersActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter4;
    private  RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance_status_for_teachers);


        recyclerView2 = findViewById(R.id.recyclerView_view_Attendance_teachers);
        mAdapter4 = new RecyclerViewAttendanceTeacherAdapter(TeacherHomeActivity.pushSubjectNameArrayList, ViewAttendanceStatusForTeachersActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(mAdapter4);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TeacherHomeActivity.retrieveAllStudents();
        mAdapter4.notifyDataSetChanged();
    }
}
