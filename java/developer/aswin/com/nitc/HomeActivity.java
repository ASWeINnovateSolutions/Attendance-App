package developer.aswin.com.nitc;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout dl;
    ProgressDialog progressDialog;
    private android.support.v7.app.ActionBarDrawerToggle t;
    private NavigationView nv;
    Button antiRaggingTextView;
    String name, faculty_phone;
    DatabaseReference databaseReference, databaseReference2;
    public static String ROLLNO_OF_STUDENT;

    FusedLocationProviderClient mFusedLocationProviderClient;
    public static ArrayList<SubjectName> mArrayListSubjectName;
    long then;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activiry);

        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(HomeActivity.this, dl, R.string.open, R.string.close);
        antiRaggingTextView = findViewById(R.id.textView_antiRRagging);

        LoadSubjectsForStudents();

        requestPermissions();

        dl.addDrawerListener(t);
        t.syncState();
        then = 0;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("" + user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ROLLNO_OF_STUDENT = dataSnapshot.child("rollNo").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        antiRaggingTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    then = (Long) System.currentTimeMillis();
                    antiRaggingTextView.setBackgroundColor(Color.RED);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    antiRaggingTextView.setBackgroundColor(Color.GREEN);
                    if((Long) System.currentTimeMillis() - then>1200){
                        antiRagging();
                        return true;
                    }
                }
                return false;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(HomeActivity.this);

        nv = findViewById(R.id.nv);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.account:
                        Toast.makeText(HomeActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nitc_website:
                        Toast.makeText(HomeActivity.this, "Forwarding request to website", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        logout_user();
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void logout_user() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Successfully Logged out!!", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    public void antiRagging() {
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Data request initiated.");
        progressDialog.show();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("" + user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.setTitle("Fetching Data from server..!!");
                progressDialog.setMessage("Please ensure your internet connection is turned on!!");
                progressDialog.setCancelable(false);
                progressDialog.show();
                name = dataSnapshot.child("name").getValue().toString();
                faculty_phone = dataSnapshot.child("faculty_phone_no").getValue().toString();
                if (name != null) {
                    Toast.makeText(HomeActivity.this, "Message ready to be sent!!", Toast.LENGTH_SHORT).show();
                }
                if (faculty_phone == null) {
                    progressDialog.setTitle("Fetching Data from server..!!");
                    progressDialog.setMessage("Please ensure your internet connection is turned on!!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                } else {
                    sendSMS_Emergency(faculty_phone, name);
                    progressDialog.setCancelable(true);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void sendSMS_Emergency(final String phone_no, final String name) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    try {
                        // Get the default instance of the SmsManager
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone_no,
                                null,
                                "I'm " + name + ", I'm in trouble, My location tracking co-ordinates are http://maps.google.com/maps?daddr=" + latitude + "," + longitude,
                                null,
                                null);
                        Toast.makeText(getApplicationContext(), "Your sms was successfully sent!",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Your sms has failed...",
                                Toast.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void locationServices(View view) {
        startActivity(new Intent(HomeActivity.this, LocationListActivity.class));
    }

    public void LoadSubjectsForStudents() {
        DatabaseReference mDatabaseReference2 = FirebaseDatabase.getInstance().getReference("Subjects");
        mArrayListSubjectName = new ArrayList<SubjectName>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String subName = ds.getKey();
                    SubjectName subjectName = new SubjectName(subName);
                    mArrayListSubjectName.add(subjectName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mDatabaseReference2.addListenerForSingleValueEvent(valueEventListener);
        mDatabaseReference2.removeEventListener(valueEventListener);
    }

    public void CheckAttendanceStudents(View view) {
      //  LoadSubjectsForStudents();
        startActivity(new Intent(HomeActivity.this, DisplaySubjectsForStudentsToChooseToViewAttendance.class));
    }
}
