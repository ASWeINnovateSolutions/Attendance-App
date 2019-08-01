package developer.aswin.com.nitc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseUser mAuth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    ValueEventListener valueEventListener;
    boolean teacherOrNot = Boolean.parseBoolean(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        progressDialog = new ProgressDialog(SplashScreenActivity.this);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();


        if (mAuth == null) {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Users")
                    .child("" + user.getUid());
            valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressDialog.setTitle("Fetching Data from server..!!");
                    progressDialog.setMessage("Please ensure your internet connection is turned on!!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    teacherOrNot = dataSnapshot.child("teacherFlag").getValue(Boolean.class);
                    takeToNextActivity();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    private void takeToNextActivity() {
        Toast.makeText(SplashScreenActivity.this, "Teacher: " + teacherOrNot, Toast.LENGTH_SHORT).show();
        if(valueEventListener!=null){
            databaseReference.removeEventListener(valueEventListener);
        }
        try {
            progressDialog.dismiss();
            if (teacherOrNot == true) {

                Toast.makeText(SplashScreenActivity.this, "Taking to Teacher Activity!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashScreenActivity.this, TeacherHomeActivity.class);
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(SplashScreenActivity.this, "Taking to Student Activity!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(valueEventListener!=null){
            databaseReference.removeEventListener(valueEventListener);
        }
    }
}
