package com.example.ma_projekat.menuFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ma_projekat.Model.User;
import com.example.ma_projekat.R;
import com.example.ma_projekat.Service.UserService;
import com.google.android.material.navigation.NavigationView;

import java.util.UUID;

public class RegisterFragment extends Fragment {

    View view;
    AppCompatActivity activity;
    UserService userService;
//    private NavigationView navigationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

//        navigationView = activity.findViewById(R.id.nav_view);

        userService = new UserService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register, container, false);

        EditText emailText = view.findViewById(R.id.email_register);
        EditText usernameText = view.findViewById(R.id.username_register);
        EditText passwordText = view.findViewById(R.id.password_register);
        EditText passwordAgainText = view.findViewById(R.id.passwordRepeat_register);
        Button btnRegister = view.findViewById(R.id.register_btn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailText.getText().toString().equals("") &&
                        !usernameText.getText().toString().equals("") &&
                        !passwordText.getText().toString().equals("") &&
                        !passwordAgainText.getText().toString().equals("")) {

                    if (passwordText.getText().toString().equals(passwordAgainText.getText().toString())) {
                        User newUser = new User(UUID.randomUUID().toString(),
                                usernameText.getText().toString(),
                                passwordText.getText().toString(),
                                emailText.getText().toString(), 0, 0, 0, 0, 0, 0, 0, 0, 0 );
                        userService.addUser(newUser);
                        Toast.makeText(activity.getApplicationContext(), "Successful registration", Toast.LENGTH_SHORT).show();

                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, new HomeFragment())
                                .setReorderingAllowed(true)
                                .commit();

//                        navigationView.setCheckedItem(R.id.home);
                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Passwords must match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Please fill in all information", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}