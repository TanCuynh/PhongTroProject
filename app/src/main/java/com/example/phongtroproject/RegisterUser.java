package com.example.phongtroproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser, loginUser;
    private EditText etName, etPhone, etEmail, etPass;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.tv_banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.btn_register);
        registerUser.setOnClickListener(this);

        loginUser = (TextView) findViewById(R.id.tv_login);
        loginUser.setOnClickListener(this);


        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_contact_number);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPass = (EditText) findViewById(R.id.et_pass);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String phone_number = etPhone.getText().toString().trim();

        if(name.isEmpty())
        {
            etName.setError("B???n ch??a nh???p t??n ng?????i d??ng!");
            etName.requestFocus();
            return;
        }
        if(phone_number.isEmpty())
        {
            etPhone.setError("B???n ch??a nh???p s??? ??i???n tho???i!");
            etPhone.requestFocus();
            return;
        }
        if(phone_number.length() != 10)
        {
            etPhone.setError("S??? ??i???n tho???i ph???i c?? 10 ch??? s???!");
            etPhone.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            etEmail.setError("B???n ch??a nh???p email!");
            etEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etEmail.setError("Xin h??y nh???p email h???p l???!");
            etEmail.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            etPass.setError("B???n ch??a nh???p m???t kh???u!");
            etPass.requestFocus();
            return;
        }
        if(pass.length() < 6)
        {
            etPass.setError("M???t kh???u ph???i ch???a ??t nh???t 6 k?? t???!");
            etPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user = new User(name, phone_number, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(RegisterUser.this, "????ng k?? ng?????i d??ng th??nh c??ng!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                            else
                                            {
                                                Toast.makeText(RegisterUser.this, "????ng k?? ng?????i d??ng kh??ng th??nh c??ng. Xin h??y th??? l???i!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(RegisterUser.this, "????ng k?? ng?????i d??ng kh??ng th??nh c??ng!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterUser.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }
}