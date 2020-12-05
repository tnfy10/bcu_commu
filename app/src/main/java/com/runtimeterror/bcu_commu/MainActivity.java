package com.runtimeterror.bcu_commu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    SetFragment setFragment;
    ScheduleFragment scheduleFragment;
    OnBackPressedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        homeFragment = new HomeFragment();
        setFragment = new SetFragment();

        if(getIntent().getBooleanExtra("schd", false)){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ScheduleFragment()).commit();
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.tabSchedule).setChecked(true);
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tabHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.tabSchedule:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ScheduleFragment()).commit();
                        return true;
                    case R.id.tabSet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, setFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener = listener;
    }

    @Override
    public void onBackPressed() {
        if(listener!=null){
            listener.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }
}