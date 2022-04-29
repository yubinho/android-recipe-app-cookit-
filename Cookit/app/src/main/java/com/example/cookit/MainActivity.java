package com.example.cookit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity  {
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference reference;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.nav5:
                    transaction.replace(R.id.LinearLayout_nav,new LoginFragment()).commit();
                    return true;
                case R.id.nav3:
                    transaction.replace(R.id.LinearLayout_nav,new IndexNewsFragment()).commit();
                    return true;
                case R.id.nav2:
                    transaction.replace(R.id.LinearLayout_nav,new FavoriteFragment()).commit();
                    return true;
                case R.id.nav1:
                    transaction.replace(R.id.LinearLayout_nav,new UploadFragment()).commit();
                    return true;
                case R.id.nav4:
                    transaction.replace(R.id.LinearLayout_nav,new RecipeEditFragment()).commit();
                    return true;
            }
            return false;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //初始顯示
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.LinearLayout_nav,new IndexNewsFragment()).commit();
        //會員更改密碼頁面所傳回的引數判斷
        int id = getIntent().getIntExtra("id",0);
        if(id == 1){
            fragmentManager=getSupportFragmentManager();
            transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.LinearLayout_nav,new LoginFragment()).commit();
        }
        //會員忘記密碼頁面所傳回的引數判斷
        if(id == 2){
            fragmentManager=getSupportFragmentManager();
            transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.LinearLayout_nav,new LoginFragment()).commit();
        }
        if(id == 3){
            fragmentManager=getSupportFragmentManager();
            transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.LinearLayout_nav,new UserManageFragment()).commit();
        }

    }



}