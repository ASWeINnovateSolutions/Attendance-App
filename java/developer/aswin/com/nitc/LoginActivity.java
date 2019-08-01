package developer.aswin.com.nitc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button mSignInButton;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    TextView new_user, lost_password;
    EditText edt_username, edt_password;
    ProgressDialog mProgressDialog;
    DatabaseReference databaseReference;
    boolean teacherOrNot;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mProgressDialog = new ProgressDialog(this);
        edt_username = findViewById(R.id.textView_username);
        edt_password = findViewById(R.id.textView_password);
        mSignInButton = findViewById(R.id.btn_login);
        new_user = findViewById(R.id.txt_newUser);
        lost_password = findViewById(R.id.txt_lostPassword);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mAuth = FirebaseAuth.getInstance();

        lost_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final EditText input = new EditText(LoginActivity.this);

                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Forgot Password")
                        .setMessage("Type in your e-mail id.")
                        .setView(input)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Editable value = input.getText();
                                updateEmail(value.toString());
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();

            }
        });
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_username.getText().toString();
                String password = edt_password.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty Username or Password field !!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mProgressDialog.setMessage("Connecting to Server to Authenticate...");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressDialog.setMessage("Validating credentials..");
                                mProgressDialog.setCancelable(false);
                                mProgressDialog.show();

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    mProgressDialog.dismiss();
                                    user = mAuth.getCurrentUser();
                                    determineNextActivity();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    mProgressDialog.setCancelable(true);
                                    mProgressDialog.dismiss();
                                }
                            }
                        });
            }
        });

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CheckTeeacherOrStudentActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    public void determineNextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Resolving Activity");
        progressDialog.setMessage("Resolving whether student or teacher.");
        progressDialog.setCancelable(false);
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("" + user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacherOrNot = dataSnapshot.child("teacherFlag").getValue(Boolean.class);
                Toast.makeText(LoginActivity.this, "Teacher: " + teacherOrNot, Toast.LENGTH_SHORT).show();
                try {
                    progressDialog.dismiss();
                    if (teacherOrNot == true) {
                        Toast.makeText(LoginActivity.this, "Taking to Teacher Activity!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, TeacherHomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Taking to Student Activity!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    onDataChange(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void updateEmail(final String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //     Log.d(TAG, "Email sent.");
                            Toast.makeText(LoginActivity.this, "Reset link Sent to " + email, Toast.LENGTH_SHORT).show();
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Password Reset Successfull")
                                    .setMessage("Reset link has been sent to " + email)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    }
                });
    }
}
