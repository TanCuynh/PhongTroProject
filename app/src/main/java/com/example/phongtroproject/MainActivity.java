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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText et_Email, et_Pass;
    private Button btn_SignIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.tv_register);
        register.setOnClickListener(this);

        btn_SignIn = (Button) findViewById(R.id.btn_login);
        btn_SignIn.setOnClickListener(this);

        et_Email = (EditText) findViewById(R.id.et_email);
        et_Pass = (EditText) findViewById(R.id.et_pass);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.btn_login:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = et_Email.getText().toString().trim();
        String pass = et_Pass.getText().toString().trim();

        if(email.isEmpty())
        {
            et_Email.setError("Bạn chưa nhập email!");
            et_Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_Email.setError("Xin hãy nhập email hợp lệ!");
            et_Email.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            et_Pass.setError("Bạn chưa nhập mật khẩu!");
            et_Pass.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            et_Pass.setError("Mật khẩu phải chứa ít nhất 6 kí tự!");
            et_Pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(MainActivity.this, HomePage.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại! Xin hãy kiểm tra lại email và mật khẩu!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}



















