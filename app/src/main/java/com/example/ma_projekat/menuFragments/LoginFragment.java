package com.example.ma_projekat.menuFragments;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ma_projekat.Model.Data;
import com.example.ma_projekat.Model.User;
import com.example.ma_projekat.R;
import com.example.ma_projekat.Repository.UserRepository;
import com.example.ma_projekat.Service.UserService;
import com.google.android.material.navigation.NavigationView;

public class LoginFragment extends Fragment {

    View view;
    EditText emailText, passwordText;
    UserService userService;
    AppCompatActivity activity;
    private NavigationView navigationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userService = new UserService();

        activity = (AppCompatActivity) getActivity();

        navigationView = activity.findViewById(R.id.nav);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);

        emailText = view.findViewById(R.id.email_login);
        passwordText = view.findViewById(R.id.password_login);
        Button btnLogin = view.findViewById(R.id.login_button);
//        MenuItem login = view.findViewById(R.id.login);
//        MenuItem register = view.findViewById(R.id.register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailText.getText().toString().equals("")
                        && !passwordText.getText().toString().equals("")) {

                    userService.getUser(emailText.getText().toString(),
                            passwordText.getText().toString(),
                            new UserRepository.FireStoreCallback() {
                                @Override
                                public void onCallBack(User user) {
                                    if (user != null) {
                                        Data.loggedInUser = user;

                                        getParentFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.frameLayout, new HomeFragment())
                                                .setReorderingAllowed(true)
                                                .commit();

                                        navigationView.setCheckedItem(R.id.home);
                                        activity.invalidateOptionsMenu();

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

                                        Log.i("loggin", Data.loggedInUser.getUsername() + " " + Data.loggedInUser.getEmail());
                                        Toast.makeText(activity.getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                    }
                                    else {
                                        Toast.makeText(activity.getApplicationContext(), "Wrong username/password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Please fill in all information", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}