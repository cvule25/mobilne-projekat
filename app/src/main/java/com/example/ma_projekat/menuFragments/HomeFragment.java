package com.example.ma_projekat.menuFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ma_projekat.GameActivity;
import com.example.ma_projekat.Model.Data;
import com.example.ma_projekat.R;
import com.example.ma_projekat.Utils.MqttHandler;
import com.example.ma_projekat.gameFragments.SpojniceFragment;

public class HomeFragment extends Fragment {

    View view;
    AppCompatActivity activity;
    SpojniceFragment spojniceFragment = new SpojniceFragment();
    MqttHandler mqttHandler;

    TextView tokenTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Button playButton = view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Data.loggedInUser == null) {
                    Toast.makeText(activity.getApplicationContext(), "Please log in/register", Toast.LENGTH_SHORT).show();
                } else {
                    spojniceFragment.setIsOnline(false);
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, new LoadingScreenFragment())
                            .setReorderingAllowed(true)
                            .commit();
                }

        };
        });

        Button soloButton = view.findViewById(R.id.soloButton);
        soloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spojniceFragment.setIsOnline(false);
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            };
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();
        mqttHandler = new MqttHandler();
    }
}