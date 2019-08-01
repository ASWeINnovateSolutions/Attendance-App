package developer.aswin.com.nitc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Recycler_Final_Adapter_To_View_Attendance_List extends RecyclerView.Adapter<Recycler_Final_Adapter_To_View_Attendance_List.AttendanceItemViewHolder> {
    public ArrayList<HolderForViewingAttendance> mHolder;
    Context mContext;

    public Recycler_Final_Adapter_To_View_Attendance_List(ArrayList<HolderForViewingAttendance> mHolder, Context mContext) {
        this.mHolder = mHolder;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AttendanceItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_view_attendance_of_students, viewGroup, false);
        return new AttendanceItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceItemViewHolder attendanceItemViewHolder, int i) {
        attendanceItemViewHolder.txtViewClassesAttended.setText(mHolder.get(i).classes_attended+"");
        attendanceItemViewHolder.txtViewRollNoStudent.setText(mHolder.get(i).rollNo+"");
    }

    @Override
    public int getItemCount() {
        return mHolder.size();
    }

    public class AttendanceItemViewHolder extends RecyclerView.ViewHolder{
        TextView txtViewRollNoStudent, txtViewClassesAttended;
        public AttendanceItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewClassesAttended = itemView.findViewById(R.id.textView_classes_attended_ViewStudentAttendance);
            txtViewRollNoStudent = itemView.findViewById(R.id.textView_rollNo_ViewStudentAttendance);
        }
    }
}
