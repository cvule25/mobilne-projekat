package com.example.ma_projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ma_projekat.Model.Data;
import com.example.ma_projekat.gameFragments.AsocijacijeFragment;
import com.example.ma_projekat.gameFragments.KorakPoKorakFragment;
import com.example.ma_projekat.gameFragments.MojBrojFragment;
import com.example.ma_projekat.gameFragments.SpojniceFragment;

public class GameActivity extends AppCompatActivity {

    SpojniceFragment spojniceFragment = new SpojniceFragment();
    MojBrojFragment mojBrojFragment = new MojBrojFragment();
    KorakPoKorakFragment korakPoKorakFragment = new KorakPoKorakFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        String p2Name = intent.getStringExtra("p2Name");
//
//        KorakPoKorakFragment korakPoKorakFragment = new KorakPoKorakFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("p2name", p2Name); // Pass the data to the fragment using a bundle
//        korakPoKorakFragment.setArguments(bundle);
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frameLayout2, korakPoKorakFragment)
//                .commit();
        setContentView(R.layout.activity_game);

        LinearLayout parentLayout = findViewById(R.id.score1_parent) ;
        LinearLayout parentLayout2 = findViewById(R.id.score2_parent);
        TextView player1Name = parentLayout.findViewById(R.id.player1Name);
        TextView player2Name = parentLayout2.findViewById(R.id.player2Name);
        if(Data.user2 != null){
            player2Name.setText(Data.user2.getUsername());
        }
        else {
            player2Name.setText("Noone");
        }
        if(Data.loggedInUser != null){
            player1Name.setText(Data.loggedInUser.getUsername());
        }
        else {
            player1Name.setText("Guest");
        }

        if (savedInstanceState == null) {
            spojniceFragment.setIsOnline(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout2, korakPoKorakFragment)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }
}