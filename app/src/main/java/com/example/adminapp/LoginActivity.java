package com.example.adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
    }

    public void PerformLogin(View view){

        final String emailtext = email.getText().toString();
        String passwordtext = password.getText().toString();


        mAuth.signInWithEmailAndPassword(emailtext, passwordtext)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Email Autentication","signInWithEmail:success");
                            FirebaseDatabase.getInstance().getReference().child("users_admin")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        int IsAdminFlag = 0;
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                ProfileUser profileuser = snapshot.getValue(ProfileUser.class);
                                                if(emailtext.equals(profileuser.getEmail())){
                                                    Intent routeMainActivity = new Intent(LoginActivity.this, Homepage.class);
                                                    startActivity(routeMainActivity);
                                                    IsAdminFlag = 1;
                                                    break;
                                                }
                                            }
                                            if(IsAdminFlag == 0) {
                                                Toast.makeText(LoginActivity.this, "Please enter administrator account credentials",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });

                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("Email authentication", "signInWithEmail:failure");
                            Toast.makeText(LoginActivity.this, "Email or Password is incorrect",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent routeMainActivity = new Intent(LoginActivity.this, Homepage.class);
            startActivity(routeMainActivity);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
