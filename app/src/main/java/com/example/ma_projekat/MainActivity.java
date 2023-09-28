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
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ma_projekat.Model.Data;
import com.example.ma_projekat.menuFragments.FriendsFragment;
import com.example.ma_projekat.menuFragments.HomeFragment;
import com.example.ma_projekat.menuFragments.LeaderboardFragment;
import com.example.ma_projekat.menuFragments.LoginFragment;
import com.example.ma_projekat.menuFragments.MyViewModel;
import com.example.ma_projekat.menuFragments.ProfileFragment;
import com.example.ma_projekat.menuFragments.RegisterFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    private MyViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///TOKEN M
        sharedViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Get the current time in milliseconds
        long currentTime = System.currentTimeMillis();

        // Get the last time the player received tokens (0 if it's their first time playing)
        long lastTokenTime = sharedPref.getLong("lastTokenTime", 0);

        // Get the current number of tokens the player has (0 if it's their first time playing)
        int currentTokens = sharedPref.getInt("currentTokens", 0);

        // Check if a day has passed since the last time the player received tokens
        if (currentTime - lastTokenTime >= 86400000) {
            // Add 5 tokens to the player's current token count
            currentTokens += 5;

            // Update the stored values
            editor.putLong("lastTokenTime", currentTime);
            editor.putInt("currentTokens", currentTokens);
            editor.apply();
        }

        ///TOKEN M
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        Menu menu1 = navigationView.getMenu();
        MenuItem logout = menu1.findItem(R.id.logout);
        MenuItem login = menu1.findItem(R.id.login);
        MenuItem register = menu1.findItem(R.id.register);

        if (Data.loggedInUser == null) {
            logout.setVisible(false);
            login.setVisible(true);
            register.setVisible(true);
        } else {
            login.setVisible(false);
            register.setVisible(false);
            logout.setVisible(true);
        }
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
                else if (id == R.id.logout) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Data.loggedInUser = null;
                    Menu menu1 = navigationView.getMenu();
                    MenuItem logout = menu1.findItem(R.id.logout);
                    MenuItem login = menu1.findItem(R.id.login);
                    MenuItem register = menu1.findItem(R.id.register);
                    MenuItem profile = menu1.findItem(R.id.profile);
                    MenuItem friends = menu1.findItem(R.id.friends);
                    MenuItem leaderboard = menu1.findItem(R.id.leaderboard);
                    if (Data.loggedInUser == null) {
                        logout.setVisible(false);
                        login.setVisible(true);
                        register.setVisible(true);
                    } else {
                        profile.setVisible(true);
                        login.setVisible(false);
                        register.setVisible(false);
                        logout.setVisible(true);
                        friends.setVisible(true);
                        leaderboard.setVisible(true);
                    }
                    fragmentR(new LoginFragment());
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        Menu menu1 = navigationView.getMenu();
        MenuItem logout = menu1.findItem(R.id.logout);
        MenuItem login = menu1.findItem(R.id.login);
        MenuItem register = menu1.findItem(R.id.register);

        if (Data.loggedInUser == null) {
            logout.setVisible(false);
            login.setVisible(true);
            register.setVisible(true);
        } else {
            login.setVisible(false);
            register.setVisible(false);
            logout.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public int getCurrentTokens() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt("currentTokens", 0);
    }

    public void subtractOneToken() {
        // Get the current number of tokens
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int currentTokens = sharedPref.getInt("currentTokens", 0);

        // Subtract one token from the current token count
        currentTokens -= 1;

        if(currentTokens == 0){
            Toast.makeText(this, "Nemate dovoljno tokena", Toast.LENGTH_SHORT).show();
            currentTokens = 0;
        }

        // Update the stored value with the new token count
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("currentTokens", currentTokens);
        editor.apply();
    }
}