package developer.aswin.com.nitc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    ArrayList<DBStudentPushData> mArrayList;
    public static ArrayList<DBStudentPushData> checked_mArrayList = new ArrayList<>();


    public RecyclerAdapter(ArrayList<DBStudentPushData> studentsList) {
        this.mArrayList = studentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_item_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter.MyViewHolder myViewHolder, final int i) {
        final DBStudentPushData student = mArrayList.get(i);
        myViewHolder.checkBox.setOnCheckedChangeListener(null);
        myViewHolder.studentName.setText(student.getStudentName());
        myViewHolder.studentRollNo.setText(student.getStudentRollNo());
        myViewHolder.checkBox.setSelected(mArrayList.get(i).isSelected());

        if(mArrayList.get(i).isSelected()){
            myViewHolder.checkBox.setChecked(true);
        }else{
            myViewHolder.checkBox.setChecked(false);
        }

        myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.checkBox.isChecked()){
                    mArrayList.get(i).setSelected(true);
                    checked_mArrayList.add(mArrayList.get(i));
                }else{
                    mArrayList.get(i).setSelected(false);
                    checked_mArrayList.remove(mArrayList.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView studentName, studentRollNo;
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            studentName = (TextView) view.findViewById(R.id.student_retrieved_name);
            studentRollNo = (TextView) view.findViewById(R.id.student_retrieved_rollNo);
            checkBox = view.findViewById(R.id.checkBox);
            checkBox.setChecked(false);
        }
    }
}

