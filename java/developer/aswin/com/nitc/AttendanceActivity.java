package developer.aswin.com.nitc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AttendanceActivity extends AppCompatActivity {
private RecyclerView.Adapter mAdapter2;
private  RecyclerView recyclerView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        recyclerView2 = findViewById(R.id.recyclerView_Subjects);
        mAdapter2 = new RecyclerSubjectAdapter(TeacherHomeActivity.pushSubjectNameArrayList, AttendanceActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(mAdapter2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TeacherHomeActivity.retrieveAllCreatedSubjects();
        mAdapter2.notifyDataSetChanged();
    }
}
