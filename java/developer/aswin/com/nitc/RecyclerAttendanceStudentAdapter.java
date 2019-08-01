package developer.aswin.com.nitc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAttendanceStudentAdapter extends RecyclerView.Adapter<RecyclerAttendanceStudentAdapter.MyAttendanceStudentListViewHolder> {

    public Context mContext;
    public ArrayList<RollNo_Attendance> mRollNo_attendances;
    public static ArrayList<RollNo_Attendance> checked_mArrayList_rollNo_attendance = new ArrayList<>();
//    public static ArrayList<RollNo_Attendance> unchecked_mArrayList_rollNo_attendance = new ArrayList<>();

    public RecyclerAttendanceStudentAdapter(Context mContext, ArrayList<RollNo_Attendance> mRollNo_attendances) {
        this.mContext = mContext;
        this.mRollNo_attendances = mRollNo_attendances;
 //       unchecked_mArrayList_rollNo_attendance = mRollNo_attendances;
    }

    @NonNull
    @Override
    public MyAttendanceStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_take_attendance_name_and_checkbox, viewGroup, false);
        return new MyAttendanceStudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAttendanceStudentListViewHolder myAttendanceStudentListViewHolder, final int i) {
        final RollNo_Attendance rollNo_attendance = mRollNo_attendances.get(i);
        myAttendanceStudentListViewHolder.checkBox.setOnCheckedChangeListener(null);
        myAttendanceStudentListViewHolder.textView.setText(rollNo_attendance.getRollNo());

        if (mRollNo_attendances.get(i).isSelected()) {
            myAttendanceStudentListViewHolder.checkBox.setChecked(true);
        } else {
            myAttendanceStudentListViewHolder.checkBox.setChecked(false);
        }

        myAttendanceStudentListViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myAttendanceStudentListViewHolder.checkBox.isChecked()) {
                    mRollNo_attendances.get(i).setSelected(true);
                    checked_mArrayList_rollNo_attendance.add(mRollNo_attendances.get(i));
                  //  unchecked_mArrayList_rollNo_attendance.remove(mRollNo_attendances.get(i));
                } else {
                    mRollNo_attendances.get(i).setSelected(false);
                    checked_mArrayList_rollNo_attendance.remove(mRollNo_attendances.get(i));
                //    unchecked_mArrayList_rollNo_attendance.add(mRollNo_attendances.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRollNo_attendances.size();
    }

    public class MyAttendanceStudentListViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;

        public MyAttendanceStudentListViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_student_rollNo_attendance);
            checkBox = itemView.findViewById(R.id.checkBox_attendance);
        }
    }
}