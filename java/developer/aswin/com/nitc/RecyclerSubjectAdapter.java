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

public class RecyclerSubjectAdapter extends RecyclerView.Adapter<RecyclerSubjectAdapter.MySubjectViewHolder> {
    public ArrayList<SubjectName> mSubjectNamesArrayList;
    DatabaseReference databaseReference;
    public static ArrayList<RollNo_Attendance> mRollNo_attendances_downloaded;
    public static String subToTakeAttendance;
    ValueEventListener valueEventListener;
    Context mContext;

    public RecyclerSubjectAdapter(ArrayList<SubjectName> mSubjectNamesArrayList, Context mContext) {
        this.mContext = mContext;
        this.mSubjectNamesArrayList = mSubjectNamesArrayList;
    }

    @NonNull
    @Override
    public MySubjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_subject_itemrow, viewGroup, false);
        return new MySubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MySubjectViewHolder mySubjectViewHolder, int i) {
        final SubjectName subjectName = mSubjectNamesArrayList.get(i);
        mySubjectViewHolder.txt_subName.setText("" + subjectName.subName);
        subToTakeAttendance = null;
        mySubjectViewHolder.txt_subName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subToTakeAttendance = mySubjectViewHolder.txt_subName.getText().toString();
                mRollNo_attendances_downloaded = new ArrayList<>();

                databaseReference = FirebaseDatabase.getInstance().getReference("Subjects")
                        .child(subToTakeAttendance);
                valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String rollNo = ds.getKey();
                            RollNo_Attendance rollNo_attendance = new RollNo_Attendance(rollNo);
                            mRollNo_attendances_downloaded.add(rollNo_attendance);
                        }
                        rollNoDataReadyForAttendance();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                databaseReference.addListenerForSingleValueEvent(valueEventListener);
            }
        });
    }

    private void rollNoDataReadyForAttendance() {
        if (valueEventListener != null) {
            databaseReference.removeEventListener(valueEventListener);
        }
        Toast.makeText(mContext, subToTakeAttendance, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, TakeAttendanceActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mSubjectNamesArrayList.size();
    }

    public class MySubjectViewHolder extends RecyclerView.ViewHolder {
        TextView txt_subName;

        public MySubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_subName = itemView.findViewById(R.id.textView_Subject);
        }
    }
}