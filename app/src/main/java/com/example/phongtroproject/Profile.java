package com.example.phongtroproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private TextView editProfile, back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editProfile = (TextView) findViewById(R.id.tv_edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, EditProfile.class));
            }
        });

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, HomePage.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView tv_Name = (TextView) findViewById(R.id.tv_name);
        final TextView tv_Email = (TextView) findViewById(R.id.tv_email);
        final TextView tv_Phone_Number = (TextView) findViewById(R.id.tv_phone_number);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null)
                {
                    String Name = userProfile.name;
                    String Email = userProfile.email;
                    String Phone = userProfile.phone_number;

                    tv_Name.setText(Name);
                    tv_Email.setText(Email);
                    tv_Phone_Number.setText(Phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Có gì đó sai sai!", Toast.LENGTH_LONG).show();
            }
        });
    }
}