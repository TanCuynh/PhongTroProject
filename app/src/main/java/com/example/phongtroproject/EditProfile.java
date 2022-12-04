package com.example.phongtroproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity{

    private TextView save, back;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        EditText etName = (EditText) findViewById(R.id.et_name);
        EditText etEmail = (EditText) findViewById(R.id.et_email);
        EditText etPhone = (EditText) findViewById(R.id.et_phone_number);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null)
                {
                    String Name = userProfile.name;
                    String Email = userProfile.email;
                    String Phone = userProfile.phone_number;

                    etName.setText(Name);
                    etEmail.setText(Email);
                    etPhone.setText(Phone);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfile.this, "Có gì đó sai sai!", Toast.LENGTH_LONG).show();
            }
        });

        save = (TextView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = etName.getText().toString();
                String newEmail = etEmail.getText().toString();
                String newPhone = etPhone.getText().toString();

                if(newName.isEmpty())
                {
                    etName.setError("Không được để trống tên người dùng!");
                    etName.requestFocus();
                    return;
                }
                if(newEmail.isEmpty())
                {
                    etEmail.setError("Không được để trống địa chỉ Email!");
                    etEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
                {
                    etEmail.setError("Xin hãy nhập email hợp lệ!");
                    etEmail.requestFocus();
                    return;
                }
                if(newPhone.isEmpty())
                {
                    etPhone.setError("Không được để trống số điện thoại!");
                    etPhone.requestFocus();
                    return;
                }
                if(newPhone.length() != 10)
                {
                    etPhone.setError("Số điện thoại phải có 10 chữ số!");
                    etPhone.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                editProfile(userID, newName, newEmail, newPhone);
            }
        });

        back = (TextView) findViewById(R.id.close);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void editProfile(String userID, String userName, String userEmail, String userPhone) {
        HashMap User = new HashMap();
        User.put("name", userName);
        User.put("phone_number", userPhone);
        User.put("email", userEmail);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userID).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(EditProfile.this, "Chỉnh sửa tài khoản thành công!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(EditProfile.this, Profile.class));
                }
                else
                {
                    Toast.makeText(EditProfile.this, "Chỉnh sửa tài khoản không thành công!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}





















