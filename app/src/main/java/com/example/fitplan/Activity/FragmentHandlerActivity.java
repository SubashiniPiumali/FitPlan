package com.example.fitplan.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.fitplan.Fragment.BMIFragment;
import com.example.fitplan.Fragment.SelectWorkoutWeekFragment;
import com.example.fitplan.Fragment.HistoryFragment;
import com.example.fitplan.Fragment.HomeFragment;
import com.example.fitplan.Fragment.ProfileFragment;
import com.example.fitplan.R;
import com.example.fitplan.databinding.ActivityBottomNavigationBarBinding;
import com.example.fitplan.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class FragmentHandlerActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private ActivityBottomNavigationBarBinding binding;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomNavigationBarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        bottomNavigationView = binding.bottomNavigationView;
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navView;
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

               // loadNavProfilePic(currentUser.getUid());
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();

        if (savedInstanceState == null) {
                replaceFragment(new HomeFragment());



            navigationView.setCheckedItem(R.id.nav_home);
        }

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home){

                    replaceFragment(new HomeFragment());

                return  true;
            }
            if(item.getItemId() == R.id.history){

                    replaceFragment(new HistoryFragment());

                return  true;
            }
            if(item.getItemId() == R.id.createWorkoutPlan){
                replaceFragment(new SelectWorkoutWeekFragment());
                return  true;

            }
            if(item.getItemId() == R.id.profile){
                replaceFragment(new ProfileFragment());
                return  true;
            }

            if(item.getItemId() == R.id.bmiCal){
                replaceFragment(new BMIFragment());
                return  true;
            }
            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.frameLayout.getId(), fragment);
        fragmentTransaction.commit();

    }

}