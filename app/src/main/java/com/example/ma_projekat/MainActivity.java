package com.example.ma_projekat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.ma_projekat.menuFragments.FriendsFragment;
import com.example.ma_projekat.menuFragments.HomeFragment;
import com.example.ma_projekat.menuFragments.LeaderboardFragment;
import com.example.ma_projekat.menuFragments.LoginFragment;
import com.example.ma_projekat.menuFragments.ProfileFragment;
import com.example.ma_projekat.menuFragments.RegisterFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.login){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    fragmentR(new LoginFragment());
                } else if (id == R.id.register) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    fragmentR(new RegisterFragment());
                } else if (id == R.id.profile) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    fragmentR(new ProfileFragment());
                } else if (id == R.id.friends) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    fragmentR(new FriendsFragment());
                } else if (id == R.id.leaderboard) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    fragmentR(new LeaderboardFragment());
                } else if (id == R.id.home) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    fragmentR(new HomeFragment());
                }
                return true;
            }
        });

    }

    private void fragmentR(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}