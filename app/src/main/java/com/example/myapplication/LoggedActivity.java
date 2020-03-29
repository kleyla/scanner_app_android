package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoggedActivity extends AppCompatActivity {

    private Button mBtnSignout;
    private FirebaseAuth mAuth;
    private TextView mTextViewName;
    private TextView mTextViewEmail;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mBtnSignout = (Button) findViewById(R.id.btnSignout);
        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewEmail = (TextView) findViewById(R.id.textViewEmail);

        mToolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(mToolbar);

        mBtnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(LoggedActivity.this, MainActivity.class));
                finish();
            }
        });

        getUserInfo();

        mNavigationView = (NavigationView) findViewById(R.id.navView);
        mNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getUserInfo(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();

                    mTextViewName.setText(name);
                    mTextViewEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
