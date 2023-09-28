package com.example.ma_projekat.menuFragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ma_projekat.GameActivity;
import com.example.ma_projekat.MainActivity;
import com.example.ma_projekat.Model.Data;
import com.example.ma_projekat.Model.User;
import com.example.ma_projekat.R;
import com.example.ma_projekat.Utils.MqttHandler;
import com.example.ma_projekat.Utils.ShowHideElements;
import com.example.ma_projekat.gameFragments.AsocijacijeFragment;
import com.example.ma_projekat.gameFragments.KorakPoKorakFragment;

import java.util.Objects;

public class LoadingScreenFragment extends Fragment {

    View view;
    AppCompatActivity activity;
    AppCompatActivity gameActivity;
//    HyphensFragment hyphensFragment = new HyphensFragment();
//    StepByStepFragment stepByStepFragment = new StepByStepFragment();
    AsocijacijeFragment associationsFragment = new AsocijacijeFragment();
    KorakPoKorakFragment korakPoKorakFragment = new KorakPoKorakFragment();
    MqttHandler mqttHandler;
    CountDownTimer countDownTimer;
    TextView p2Name, loadingText, tokenTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
//        gameActivity = (GameActivity) getActivity();
//        p2Name = gameActivity.findViewById(R.id.player2Name);
        mqttHandler = new MqttHandler();
        mqttHandler.connect();
        mqttHandler.startMatchmaking();
        mqttHandler.pointShareSubscribe();
        mqttHandler.decideTurnPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_loading_screen, container, false);
        Button btnCancel = view.findViewById(R.id.loading_btn);
        loadingText = view.findViewById(R.id.loading_text);
        MainActivity mainActivity = (MainActivity) getActivity();
        p2Name = activity.findViewById(R.id.player2Name);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .setReorderingAllowed(true)
                        .commit();
                mqttHandler.disconnect();
            }
        });

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                if (loadingText.getText().toString().equals( "Loading...")) {
                    loadingText.setText("Loading");
                } else {
                    loadingText.append(".");
                }
            }

            @Override
            public void onFinish() {
                User p2 = mqttHandler.getP2Username();
                if (p2 != null && !Objects.equals(p2.getUsername(), Data.loggedInUser.getUsername())) {
//                    p2Name.setText(p2.getUsername());
//                    mainActivity.subtractOneToken();
                    Data.isOnline = true;
                    Data.user2 = p2;
                    Intent intent = new Intent(getActivity(), GameActivity.class);
//                    String p2Name = p2.getUsername();
//                    intent.putExtra("p2Name", p2Name);
                    startActivity(intent);
                } else {
                    Toast.makeText(activity.getApplicationContext(), "No opponent found", Toast.LENGTH_SHORT).show();
                    btnCancel.performClick();
                }
            }
        }.start();

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        countDownTimer.cancel();
    }
}