package developer.aswin.com.nitc;

import android.annotation.SuppressLint;
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

public class RecyclerViewAttendanceTeacherAdapter extends RecyclerView.Adapter<RecyclerViewAttendanceTeacherAdapter.ViewHolderViewAttendance> {
    public ArrayList<SubjectName> mSubjectNamesArrayList;
    public static ArrayList<HolderForViewingAttendance> holderForViewingAttendanceArrayList;
    public static String subToViewAttendance;
    public static String TOTAL_ATTENDANCE;
    public static boolean TURN = false;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    Context mContext;

    public RecyclerViewAttendanceTeacherAdapter(ArrayList<SubjectName> mSubjectNamesArrayList, Context mContext) {
        this.mSubjectNamesArrayList = mSubjectNamesArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override

    public ViewHolderViewAttendance onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_subject_itemrow, viewGroup, false);
        return new ViewHolderViewAttendance(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderViewAttendance viewHolderViewAttendance, int i) {
        final SubjectName subjectName = mSubjectNamesArrayList.get(i);
        viewHolderViewAttendance.textViewViewSub.setText(subjectName.getSubName());
        subToViewAttendance = null;

        viewHolderViewAttendance.textViewViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holderForViewingAttendanceArrayList = new ArrayList<>();
                Toast.makeText(mContext, "" + viewHolderViewAttendance.textViewViewSub.getText().toString(), Toast.LENGTH_SHORT).show();
                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance()
                        .getReference("TotalClasses")
                        .child("" + viewHolderViewAttendance.textViewViewSub.getText().toString());
                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        TOTAL_ATTENDANCE = dataSnapshot.child("total_classes").getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                subToViewAttendance = viewHolderViewAttendance.textViewViewSub.getText().toString();
                DatabaseReference databaseReference3 = FirebaseDatabase.getInstance()
                        .getReference("Subjects")
                        .child(viewHolderViewAttendance.textViewViewSub.getText().toString());

                databaseReference3.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String rollNo = ds.getKey();
                            String classes_attended = ds.child("classes_attended").getValue(String.class);
                            HolderForViewingAttendance holderForViewingAttendance = new HolderForViewingAttendance(rollNo, "Classes Attended "+ classes_attended);
                            holderForViewingAttendanceArrayList.add(holderForViewingAttendance);
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
        if (valueEventListener != null) {
            databaseReference.removeEventListener(valueEventListener);
        }
        TURN = true;
        RecyclerAdapterToDisplaySubjectsForStudents.TURN = false;
        mContext.startActivity(new Intent(mContext, ViewAttendanceOfStudentsForTeacherAfterRetrievingFromFirebase.class));
    }

    @Override
    public int getItemCount() {
        return mSubjectNamesArrayList.size();
    }

    public class ViewHolderViewAttendance extends RecyclerView.ViewHolder {
        public TextView textViewViewSub;

        public ViewHolderViewAttendance(@NonNull View itemView) {
            super(itemView);
            textViewViewSub = itemView.findViewById(R.id.textView_Subject);
        }
    }
}