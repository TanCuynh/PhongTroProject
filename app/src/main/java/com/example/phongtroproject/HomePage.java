package com.example.phongtroproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class HomePage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        firebaseAuth = FirebaseAuth.getInstance();


        BottomNavigationView navigationView = findViewById(R.id.navigation);

        replaceFragment(new Home());

        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_home:
//                    actionBar.setTitle("HOME");
//                    Home homefr = new Home();
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.content, homefr, "");
//                    fragmentTransaction.commit();
                    replaceFragment(new Home());
                    break;
                case R.id.nav_users:
//                    actionBar.setTitle("Users");
//                    UserFragment userFragment = new UserFragment();
//                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction2.replace(R.id.content, userFragment, "");
//                    fragmentTransaction2.commit();
                    replaceFragment(new UserFragment());
                    break;
                case R.id.nav_add:
//                    actionBar.setTitle("ADDPOST");
//                    AddPost addPost = new AddPost();
//                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction1.replace(R.id.content, addPost, "");
//                    fragmentTransaction1.commit();
                    replaceFragment(new AddPost());
                    break;
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }


}