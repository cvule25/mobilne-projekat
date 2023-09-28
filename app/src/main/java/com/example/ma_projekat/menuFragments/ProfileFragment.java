package com.example.ma_projekat.menuFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ma_projekat.Model.Data;
import com.example.ma_projekat.R;

public class ProfileFragment extends Fragment {

    View view;
    TextView userName, email, partije, pobede, porazi, koZnaZna, spojnice, asocijacije, skocko, korakPoKorak, mojBroj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.userName);
        email = view.findViewById(R.id.email);
        koZnaZna = view.findViewById(R.id.koznaznaStats);
        spojnice = view.findViewById(R.id.spojniceStats);
        asocijacije = view.findViewById(R.id.asocijacijeStats);
        skocko = view.findViewById(R.id.skockoStats);
        korakPoKorak = view.findViewById(R.id.korakpokorakStats);
        mojBroj = view.findViewById(R.id.mojbrojStats);
        partije = view.findViewById(R.id.totalGames);
        pobede = view.findViewById(R.id.totalWins);
        porazi = view.findViewById(R.id.totaLosses);
        if(Data.loggedInUser != null){
            userName.setText(Data.loggedInUser.getUsername());
            email.setText(Data.loggedInUser.getEmail());
            koZnaZna.setText(Data.loggedInUser.getKoZnaZna()+"");
            spojnice.setText(Data.loggedInUser.getSpojnice()+"");
            asocijacije.setText(Data.loggedInUser.getAsocijacije()+"");
            skocko.setText(Data.loggedInUser.getSkocko()+"");
            korakPoKorak.setText(Data.loggedInUser.getKorakPoKorak()+"");
            mojBroj.setText(Data.loggedInUser.getMojBroj()+"");
            partije.setText(Data.loggedInUser.getPartije()+"");
            pobede.setText(Data.loggedInUser.getPobede()+"");
            porazi.setText(Data.loggedInUser.getPorazi()+"");
        }
        return view;
    }
}

