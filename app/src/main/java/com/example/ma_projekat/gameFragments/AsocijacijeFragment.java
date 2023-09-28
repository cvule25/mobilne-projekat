package com.example.ma_projekat.gameFragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ma_projekat.Model.Asocijacije;
import com.example.ma_projekat.Model.Data;
import com.example.ma_projekat.Model.StrDTO;
import com.example.ma_projekat.Model.UserDTO;
import com.example.ma_projekat.R;
import com.example.ma_projekat.Repository.UserRepository;
import com.example.ma_projekat.Utils.MqttHandler;
import com.example.ma_projekat.Utils.TempGetData;
import com.example.ma_projekat.menuFragments.HomeFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class AsocijacijeFragment extends Fragment {
    View view;
    TextView a1,a2,a3,a4,b1,b2,b3,b4,c1,c2,c3,c4,d1,d2,d3,d4,e,player1Score, scoreTimer, player2Score;
    TextView whoseTurn, a_answer,b_answer,c_answer, d_answer, final_answer, p1UserName, player2UserName;
    Dialog dialog;
    CountDownTimer countDownTimer;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> aFields = new ArrayList<>();
    ArrayList<String> bFields = new ArrayList<>();
    ArrayList<String> cFields = new ArrayList<>();
    ArrayList<String> dFields = new ArrayList<>();
    ArrayList<TextView> textViewsa = new ArrayList<>();
    ArrayList<TextView> textViewsb = new ArrayList<>();
    ArrayList<TextView> textViewsc = new ArrayList<>();
    ArrayList<TextView> textViewsd = new ArrayList<>();
    boolean field_a = false;
    boolean field_b = false;
    boolean field_c = false;
    boolean field_d = false;
    boolean field_e = false;
    boolean isTruea = false;
    boolean isTrueb = false;
    boolean isTruec = false;
    boolean isTrued = false;
    boolean isAAnswered = false, isBAnswered = false, isCAnswered = false, isDAnswered = false;
    AppCompatActivity activity;
    MqttHandler mqttHandler = new MqttHandler();
    boolean isClicked = false;
    int poinst = 0;
    int score1 = 0;
    int score2 = 0;
    boolean isMyTurn;
    boolean canEBeClicked = false;
    boolean isOnline = Data.isOnline;
    TempGetData tempGetData = new TempGetData();
    public void setIsOnline(boolean bool){
        isOnline = bool;
    }
    UserRepository userRepository = new UserRepository();
    public void setAFields(){
        a1.setText(aFields.get(0));
        a2.setText(aFields.get(1));
        a3.setText(aFields.get(2));
        a4.setText(aFields.get(3));
        a_answer.setText(aFields.get(4));
        if(isOnline){
            mqttHandler.asocijacijePublish(a1,"a");
            mqttHandler.asocijacijePublish(a2,"a");
            mqttHandler.asocijacijePublish(a3,"a");
            mqttHandler.asocijacijePublish(a4,"a");
            mqttHandler.asocijacijePublish(a_answer,"a");
        }
    }
    public void setBFields(){
        b1.setText(bFields.get(0));
        b2.setText(bFields.get(1));
        b3.setText(bFields.get(2));
        b4.setText(bFields.get(3));
        b_answer.setText(bFields.get(4));
        if(isOnline){
            mqttHandler.asocijacijePublish(b1,"b");
            mqttHandler.asocijacijePublish(b2,"b");
            mqttHandler.asocijacijePublish(b3,"b");
            mqttHandler.asocijacijePublish(b4,"b");
            mqttHandler.asocijacijePublish(b_answer,"b");
        }
    }
    public void setCFields(){
        c1.setText(cFields.get(0));
        c2.setText(cFields.get(1));
        c3.setText(cFields.get(2));
        c4.setText(cFields.get(3));
        c_answer.setText(cFields.get(4));
        if(isOnline){
            mqttHandler.asocijacijePublish(c1,"c");
            mqttHandler.asocijacijePublish(c2,"c");
            mqttHandler.asocijacijePublish(c3,"c");
            mqttHandler.asocijacijePublish(c4,"c");
            mqttHandler.asocijacijePublish(c_answer,"c");
        }
    }
    public void setDFields(){
        d1.setText(dFields.get(0));
        d2.setText(dFields.get(1));
        d3.setText(dFields.get(2));
        d4.setText(dFields.get(3));
        d_answer.setText(dFields.get(4));
        if(isOnline){
            mqttHandler.asocijacijePublish(d1,"d");
            mqttHandler.asocijacijePublish(d2,"d");
            mqttHandler.asocijacijePublish(d3,"d");
            mqttHandler.asocijacijePublish(d4,"d");
            mqttHandler.asocijacijePublish(d_answer,"d");
        }
    }
    public static AsocijacijeFragment newInstance(boolean round, boolean bool) {

        Bundle args = new Bundle();
        args.putBoolean("isFirstRound", round);
        args.putBoolean("isOnline", bool);

        AsocijacijeFragment fragment = new AsocijacijeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void setIsMyTurn(){
        if(isMyTurn == true){
            isMyTurn = false;
        }else if(isMyTurn == false){
            isMyTurn = true;
        }
    }
    public void setPoints(int poinst){
        score1 = Integer.parseInt((String) player1Score.getText());
        score1+=poinst;
//        asocijacije.setText(score+"");
        player1Score.setText(score1 + "");
        if(isOnline){
            Data.loggedInUser.setAsocijacije(Data.loggedInUser.getAsocijacije()+score1);
            userRepository.updateAsocijacije(Data.loggedInUser, Data.loggedInUser.getAsocijacije()+score1);
            mqttHandler.pointPublish(score1);
        }
    }
    public void TempGetDataMethod(String runda){
        TempGetData.getAsocijacije(new TempGetData.FireStoreCallback() {
            @Override
            public void onCallBack(ArrayList<String> list) {
                arrayList.addAll(list);

                textViewsa.add(a1);
                textViewsa.add(a2);
                textViewsa.add(a3);
                textViewsa.add(a4);
                textViewsa.add(a_answer);
                textViewsb.add(b1);
                textViewsb.add(b2);
                textViewsb.add(b3);
                textViewsb.add(b4);
                textViewsb.add(b_answer);
                textViewsc.add(c1);
                textViewsc.add(c2);
                textViewsc.add(c3);
                textViewsc.add(c4);
                textViewsc.add(c_answer);
                textViewsd.add(d1);
                textViewsd.add(d2);
                textViewsd.add(d3);
                textViewsd.add(d4);
                textViewsd.add(d_answer);

                for(int i = 0; i< arrayList.size(); i++){
                    if(i >= 0 && i <=4){
                        aFields.add(arrayList.get(i));
                    }else if(i>4 && i<=9){
                        bFields.add(arrayList.get(i));
                    }else if(i>9 && i<=14){
                        cFields.add(arrayList.get(i));
                    }else if(i>14 && i<=19){
                        dFields.add(arrayList.get(i));
                    }
                }

                if(getArguments() != null){
                    isOnline = getArguments().getBoolean("isOnline", false);
                }

                if(isOnline){
                    isMyTurn = mqttHandler.getTurnPlayer();
                    if (getArguments() != null) {
                        Log.i("mqtt","Ako getArgument != null i treba menjati red: "+ isMyTurn + " Ulogovani korisnik: " + Data.loggedInUser.getUsername());
                        setIsMyTurn();
                    }
                }
            }
        }, runda);
    }
    public void setEFields(){
        final_answer.setText(arrayList.get(20));
        if(isOnline){
            mqttHandler.asocijacijePublish(final_answer,"e");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_asocijacije, container, false);
        a1 = view.findViewById(R.id.asocijacije_A1);
        a2 = view.findViewById(R.id.asocijacije_A2);
        a3 = view.findViewById(R.id.asocijacije_A3);
        a4 = view.findViewById(R.id.asocijacije_A4);
        b1 = view.findViewById(R.id.asocijacije_B1);
        b2 = view.findViewById(R.id.asocijacije_B2);
        b3 = view.findViewById(R.id.asocijacije_B3);
        b4 = view.findViewById(R.id.asocijacije_B4);
        c1 = view.findViewById(R.id.asocijacije_C1);
        c2 = view.findViewById(R.id.asocijacije_C2);
        c3 = view.findViewById(R.id.asocijacije_C3);
        c4 = view.findViewById(R.id.asocijacije_C4);
        d1 = view.findViewById(R.id.asocijacije_D1);
        d2 = view.findViewById(R.id.asocijacije_D2);
        d3 = view.findViewById(R.id.asocijacije_D3);
        d4 = view.findViewById(R.id.asocijacije_D4);
        final_answer = view.findViewById(R.id.asocijacije_answerAll);
        a_answer = view.findViewById(R.id.asocijacije_answerA);
        b_answer = view.findViewById(R.id.asocijacije_answerB);
        c_answer = view.findViewById(R.id.asocijacije_answerC);
        d_answer = view.findViewById(R.id.asocijacije_answerD);

        whoseTurn = view.findViewById(R.id.whose_turn);
//        asocijacije = view.findViewById(R.id.textViewAsocijacije);

        Button confirm = view.findViewById(R.id.asocijacije_confirm);

        if(getArguments() == null){
            TempGetDataMethod("runda1");
        }else{
            TempGetDataMethod("runda2");
        }

        scoreTimer = activity.findViewById(R.id.time);
        player1Score = activity.findViewById(R.id.player1Score);
        player2Score = activity.findViewById(R.id.player2Score);

        p1UserName = activity.findViewById(R.id.player1Name);
        player2UserName = activity.findViewById(R.id.player2Name);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editAnswerA = a_answer.getText().toString();
                String editAnswerB = b_answer.getText().toString();
                String editAnswerC = c_answer.getText().toString();
                String editAnswerD = d_answer.getText().toString();

                String a = aFields.get(4);
                String b = bFields.get(4);
                String c = cFields.get(4);
                String d = dFields.get(4);
                String e = arrayList.get(20);
                    if(!editAnswerA.equals("") && !isTruea){
                        if(editAnswerA.equals(a)){
                            int counter_a = 0;
                            isAAnswered = true;
                            for(TextView textView : textViewsa){
                                if(textView.getText() == ""){
                                    if(counter_a == 4){
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_a++;
                            }
                            poinst += 2;
                            setPoints(poinst);
                            setAFields();
                            a_answer.setEnabled(false);
                            a_answer.setFocusable(false);
                            isTruea = true;
                            isClicked = false;
                            canEBeClicked = true;
                        }else{
                            if(isOnline){
                                StrDTO strDTO = new StrDTO("a",editAnswerA, Data.loggedInUser.getUsername());
                                mqttHandler.StringPublish(strDTO);
                                //                            Toast.makeText(getActivity(), "Column A try : " + editText1+"", Toast.LENGTH_LONG).show();
                                Log.i("mqtt","Column A try (Before setting turn ) Turn is : "+isMyTurn + " and User: " + Data.loggedInUser.getUsername());
                                setIsMyTurn();
                                Log.i("mqtt","Column A try (After setting turn ) Turn is: "+isMyTurn+" and User: "+Data.loggedInUser.getUsername());
                                Log.i("mqtt", "isClickedA before: "+isClicked);
                                isClicked = false;
                                Log.i("mqtt", "isClickedA after: "+isClicked);
                            }else {
                                isClicked = false;
                            }
                        }

                } else if(!editAnswerB.equals("")){
                        if(editAnswerB.equals(b)){
                            int counter_b = 0;
                            for(TextView textView : textViewsb){
                                if(textView.getText() == ""){
                                    if(counter_b == 4){
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_b++;
                            }
                            poinst += 2;
                            setPoints(poinst);
                            setBFields();
                            isTrueb = true;
                            b_answer.setEnabled(false);
                            b_answer.setFocusable(false);
                            isClicked = false;
                            canEBeClicked = true;
                        }else{
                            if(isOnline){
                                StrDTO strDTO = new StrDTO("b", editAnswerB, Data.loggedInUser.getUsername());
                                mqttHandler.StringPublish(strDTO);
                                //                            Toast.makeText(getActivity(), "Column B try : " + editText1+"", Toast.LENGTH_LONG);
                                Log.i("mqtt","Column B try (Before setting turn ) Turn is : "+isMyTurn + " and User: " + Data.loggedInUser.getUsername());
                                setIsMyTurn();
                                Log.i("mqtt","Column B try (After setting turn ) Turn is: "+isMyTurn+" and User: "+Data.loggedInUser.getUsername());
                                Log.i("mqtt", "isClickedB before: "+isClicked);
                                isClicked = false;
                                Log.i("mqtt", "isClickedB after: "+isClicked);
                            }else {
                                isClicked = false;
                            }
                        }
                } else if(!editAnswerC.equals("")){
                        if(editAnswerC.equals(c)){
                            int counter_c = 0;
                            for(TextView textView : textViewsc){
                                if(textView.getText() == ""){
                                    if(counter_c == 4){
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_c++;
                            }
                            poinst += 2;
                            setPoints(poinst);
                            setCFields();
                            isTruec = true;
                            c_answer.setEnabled(false);
                            c_answer.setFocusable(false);
                            isClicked = false;
                            canEBeClicked = true;
                        }else{
                            if(isOnline){
                                StrDTO strDTO = new StrDTO("c", editAnswerC, Data.loggedInUser.getUsername());
                                mqttHandler.StringPublish(strDTO);
                                //                            Toast.makeText(getActivity(), "Column C try : " + editText1+"", Toast.LENGTH_LONG);
                                Log.i("mqtt","Column C try (Before setting turn ) Turn is : "+isMyTurn + " and User: " + Data.loggedInUser.getUsername());
                                setIsMyTurn();
                                Log.i("mqtt","Column C try (After setting turn ) Turn is: "+isMyTurn+" and User: "+Data.loggedInUser.getUsername());
                                Log.i("mqtt", "isClickedC before: "+isClicked);
                                isClicked = false;
                                Log.i("mqtt", "isClickedC after: "+isClicked);
                            }else{
                                isClicked = false;
                            }
                        }
                    }
                    else if(!editAnswerD.equals("")){
                        if(editAnswerD.equals(d)){
                            int counter_d = 0;
                            for(TextView textView : textViewsd){
                                if(textView.getText() == ""){
                                    if(counter_d == 4){
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_d++;
                            }
                            poinst += 2;
                            setPoints(poinst);
                            setDFields();
                            isTrued = true;
                            d_answer.setEnabled(false);
                            d_answer.setFocusable(false);
                            isClicked = false;
                            canEBeClicked = true;
                        }else{
                            if(isOnline){
                                StrDTO strDTO = new StrDTO("d", editAnswerD, Data.loggedInUser.getUsername());
                                mqttHandler.StringPublish(strDTO);
                                //                            Toast.makeText(getActivity(), "Column D try : " + editText1+"", Toast.LENGTH_LONG);
                                Log.i("mqtt","Column D try (Before setting turn ) Turn is : "+isMyTurn + " and User: " + Data.loggedInUser.getUsername());
                                setIsMyTurn();
                                Log.i("mqtt","Column D try (After setting turn ) Turn is: "+isMyTurn+" and User: "+Data.loggedInUser.getUsername());
                                Log.i("mqtt", "isClickedD before: "+isClicked);
                                isClicked = false;
                                Log.i("mqtt", "isClickedD after: "+isClicked);
                            }else{
                                isClicked = false;
                            }
                        }
                } else if(!final_answer.getText().toString().equals("")){
                        String editAnswerFinal = final_answer.getText().toString();
                    if(editAnswerFinal != ""){
                        if(editAnswerFinal.equals(e)){
                            int counter_a = 0;
                            for(TextView textView : textViewsa){
                                if(textView.getText() == ""){
                                    if(counter_a == 4){
                                        if(textView.getText() == ""){
                                            poinst += 2;
                                        }
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_a++;
                            }
                            int counter_b = 0;
                            for(TextView textView : textViewsb){
                                if(textView.getText() == ""){
                                    if(counter_b == 4){
                                        if(textView.getText() == ""){
                                            poinst += 2;
                                        }
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_b++;
                            }
                            int counter_c = 0;
                            for(TextView textView : textViewsc){
                                if(textView.getText() == ""){
                                    if(counter_c == 4){
                                        if(textView.getText() == ""){
                                            poinst += 2;
                                        }
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_c++;
                            }
                            int counter_d = 0;
                            for(TextView textView : textViewsd){
                                if(textView.getText() == ""){
                                    if(counter_d == 4){
                                        if(textView.getText() == ""){
                                            poinst += 2;
                                        }
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_d++;
                            }
                            poinst += 7;
                            setPoints(poinst);
                            setAFields();
                            setBFields();
                            setCFields();
                            setDFields();
//                            setEFields();
                            isTrued = true;
                            if(isOnline){
                                if(getArguments() == null){
                                    boolean isFirstPlayerWinner = false;
                                    if(Integer.parseInt(player1Score.getText().toString()) > Integer.parseInt(player2Score.getText().toString())){
                                        isFirstPlayerWinner = true;
                                        userRepository.updatePobede(Data.loggedInUser, Data.loggedInUser.getPobede()+1);
                                    }else {
                                        isFirstPlayerWinner = false;
                                        userRepository.updatePorazi(Data.loggedInUser, Data.loggedInUser.getPorazi()+1);
                                    }
                                    getParentFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.frameLayout2, AsocijacijeFragment.newInstance(true, true))
                                            .setReorderingAllowed(true)
                                            .commit();
                                }else{
                                    getParentFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.frameLayout2, new SpojniceFragment())
                                            .setReorderingAllowed(true)
                                            .commit();
                                }
                            }else {
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frameLayout2, new SpojniceFragment())
                                        .setReorderingAllowed(true)
                                        .commit();
                            }
                        }else{
                            if(isOnline){
                                StrDTO strDTO = new StrDTO("e", editAnswerFinal, Data.loggedInUser.getUsername());
                                mqttHandler.StringPublish(strDTO);
                                //                            Toast.makeText(getActivity(), "Field E try : " + editText1+"", Toast.LENGTH_LONG);
                                setIsMyTurn();
                                isClicked = false;
                            }
                        }
                        countDownTimer.cancel();
                    }
                }
                field_a = false;
                field_b = false;
                field_c = false;
                field_d = false;
                field_e = false;
//                editAnswerFinal.setText("");
            }
        });


//        a_answer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isOnline){
//                    if(isMyTurn == true){
//                        field_a = true;
//                        int y = 0;
//                        for(TextView textView : textViewsa){
//                            if(textView.getText() == ""){
//                                y++;
//                            }
//                        }
//                        if(!isTruea && y != 5){
//                            y=0;
//                        }
//                    }
//                }else{
//                    field_a = true;
//                    int y = 0;
//                    for(TextView textView : textViewsa){
//                        if(textView.getText() == ""){
//                            y++;
//                        }
//                    }
//                    if(!isTruea && y != 5){
//                        y=0;
//                    }
//                }
//            }
//        });

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn && !isClicked){
                        isClicked = true;
                        a1.setText(aFields.get(0));
                        mqttHandler.asocijacijePublish(a1,"a");
                    }
                }else{
                    if(!isClicked){
                        isClicked = true;
                        a1.setText(aFields.get(0));
                    }
                }
            }
        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn && !isClicked){
                        isClicked = true;
                        a2.setText(aFields.get(1));
                        mqttHandler.asocijacijePublish(a2, "a");
                    }
                }else{
                    if(!isClicked){
                        isClicked = true;
                        a2.setText(aFields.get(1));
                    }
                }
            }
        });

        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn && !isClicked){
                        isClicked = true;
                        a3.setText(aFields.get(2));
                        mqttHandler.asocijacijePublish(a3, "a");
                    }
                }else {
                    if(!isClicked){
                        isClicked = true;
                        a3.setText(aFields.get(2));
                    }
                }
            }
        });

        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn && !isClicked){
                        isClicked = true;
                        a4.setText(aFields.get(3));
                        mqttHandler.asocijacijePublish(a4, "a");
                    }else{
                        if (!isClicked){
                            isClicked = true;
                            a4.setText(aFields.get(3));
                        }
                    }
                }
            }
        });
        b_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    if(isMyTurn == true) {
                        //                    b_answer.setClickable(true);
                        field_b = true;
                        int y = 0;
                        for(TextView textView : textViewsb){
                            if(textView.getText() == ""){
                                y++;
                            }
                        }
                        if(!isTrueb && y != 5){
                            y=0;
                        }
                    }
                }else{
                    field_b = true;
                    int y = 0;
                    for(TextView textView : textViewsb){
                        if(textView.getText() == ""){
                            y++;
                        }
                    }
                    if(!isTrueb && y != 5){
                        y=0;
                    }
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        b1.setText(bFields.get(0));
                        mqttHandler.asocijacijePublish(b1,"b");
                    }
                }else {
                    if(!isClicked){
                        isClicked = true;
                        b1.setText(bFields.get(0));
                    }
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        b2.setText(bFields.get(1));
                        mqttHandler.asocijacijePublish(b2, "b");
                    }
                }else{
                    if(!isClicked){
                        isClicked = true;
                        b2.setText(bFields.get(1));
                    }
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        b3.setText(bFields.get(2));
                        mqttHandler.asocijacijePublish(b3,"b");
                    }
                }else{
                    if(!isClicked){
                        isClicked = true;
                        b3.setText(bFields.get(2));
                    }
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        b4.setText(bFields.get(3));
                        mqttHandler.asocijacijePublish(b4,"b");
                    }
                }else{
                    if(!isClicked){
                        isClicked = true;
                        b4.setText(bFields.get(3));
                    }
                }
            }
        });
        c_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    if(isMyTurn == true){
                        //                    c_answer.setClickable(true);

                        field_c = true;
                        int y = 0;
                        for(TextView textView : textViewsc){
                            if(textView.getText() == ""){
                                y++;
                            }
                        }
                        if(!isTruec && y != 5){
                            y=0;
                        }
                    }
                }else{
                    field_c = true;
                    int y = 0;
                    for(TextView textView : textViewsc){
                        if(textView.getText() == ""){
                            y++;
                        }
                    }
                    if(!isTruec && y != 5){
                        y=0;
                    }
                }
            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        c1.setText(cFields.get(0));
                        mqttHandler.asocijacijePublish(c1,"c");
                    }
                }else{
                    if(!isClicked){
                        isClicked = true;
                        c1.setText(cFields.get(0));
                    }
                }
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        c2.setText(cFields.get(1));
                        mqttHandler.asocijacijePublish(c2,"c");
                    }
                }else {
                    if(!isClicked){
                        isClicked = true;
                        c2.setText(cFields.get(1));
                    }
                }
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        c3.setText(cFields.get(2));
                        mqttHandler.asocijacijePublish(c3,"c");
                    }
                }else {
                    if(!isClicked){
                        isClicked = true;
                        c3.setText(cFields.get(2));
                    }
                }
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false) {
                        isClicked = true;
                        c4.setText(cFields.get(3));
                        mqttHandler.asocijacijePublish(c4, "c");
                    }
                }else {
                    if(!isClicked){
                        isClicked = true;
                        c4.setText(cFields.get(3));
                    }
                }
            }
        });
        d_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    if(isMyTurn == true){
                        //                    d_answer.setClickable(true);
                        field_d = true;
                        int y = 0;
                        for(TextView textView : textViewsd){
                            if(textView.getText() == ""){
                                y++;
                            }
                        }
                        if(!isTrued && y != 5){
                            y=0;
                        }
                    }
                }else{
                    field_d = true;
                    int y = 0;
                    for(TextView textView : textViewsd){
                        if(textView.getText() == ""){
                            y++;
                        }
                    }
                    if(!isTrued && y != 5){
                        y=0;
                    }
                }
            }
        });
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        d1.setText(dFields.get(0));
                        mqttHandler.asocijacijePublish(d1,"d");
                    }
                }else {
                    if(!isClicked){
                        isClicked = true;
                        d1.setText(dFields.get(0));
                    }
                }
            }
        });
        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        d2.setText(dFields.get(1));
                        mqttHandler.asocijacijePublish(d2,"d");
                    }
                }else {
                    if(!isClicked){
                        isClicked = true;
                        d2.setText(dFields.get(1));
                    }
                }
            }
        });
        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn == true && isClicked == false){
                        isClicked = true;
                        d3.setText(dFields.get(2));
                        mqttHandler.asocijacijePublish(d3,"d");
                    }
                }else{
                    if(!isClicked){
                        isClicked = true;
                        d3.setText(dFields.get(2));
                    }
                }
            }
        });
        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline){
                    Log.i("mqtt", "isMyTurn before click: "+isMyTurn+" "+"isClicked: "+isClicked+"User: "+Data.loggedInUser.getUsername());
                    if(isMyTurn && !isClicked){
                        isClicked = true;
                        d4.setText(dFields.get(3));
                        mqttHandler.asocijacijePublish(d4,"d");
                    }
                }else {
                    if(!isClicked){
                        isClicked = true;
                        d4.setText(dFields.get(3));
                    }
                }
            }
        });

//        e.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isOnline){
//                    if(isMyTurn){
//                        int y = 0;
//                        for(TextView textView : textViewsa){
//                            if(textView.getText() == ""){
//                                y++;
//                            }
//                        }
//                        for(TextView textView : textViewsb){
//                            if(textView.getText() == ""){
//                                y++;
//                            }
//                        }
//                        for(TextView textView : textViewsc){
//                            if(textView.getText() == ""){
//                                y++;
//                            }
//                        }
//                        for(TextView textView : textViewsd){
//                            if(textView.getText() == ""){
//                                y++;
//                            }
//                        }
//
//                        if(y != 20 && canEBeClicked){
//                            field_e = true;
////                            dialog.show();
//                        }
//                    }
//                }else{
//                    int y = 0;
//                    for(TextView textView : textViewsa){
//                        if(textView.getText() == ""){
//                            y++;
//                        }
//                    }
//                    for(TextView textView : textViewsb){
//                        if(textView.getText() == ""){
//                            y++;
//                        }
//                    }
//                    for(TextView textView : textViewsc){
//                        if(textView.getText() == ""){
//                            y++;
//                        }
//                    }
//                    for(TextView textView : textViewsd){
//                        if(textView.getText() == ""){
//                            y++;
//                        }
//                    }
//
//                    if(y != 20 && canEBeClicked){
//                        field_e = true;
////                        dialog.show();
//                    }
//                }
//            }
//        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();
        scoreTimer = activity.findViewById(R.id.time);

        player1Score = activity.findViewById(R.id.player1Score);
        player2Score = activity.findViewById(R.id.player2Score);

        p1UserName = activity.findViewById(R.id.player1Name);
        player2UserName = activity.findViewById(R.id.player2Name);

        if(Data.loggedInUser != null && !player2UserName.getText().toString().equals("Guest")){
            mqttHandler.asocijacijeSubscribe(new MqttHandler.AsocijacijeCallback() {
                @Override
                public void onCallBack(Asocijacije asocijacije) {
                    if(asocijacije != null){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!isMyTurn){
                                    Log.i("mqtt","Column subcribe: "+asocijacije.getColumnName());
                                    if(asocijacije.getColumnName().equals("a")){
                                        Log.i("mqtt","Column A subcribe: "+asocijacije.getColumnName());
                                        int answerA_count = 0;
                                        for (TextView textView : textViewsa){
                                            if(textView.getId() == asocijacije.getId()){
                                                if (answerA_count == 4){
                                                    a_answer.setEnabled(false);
                                                    a_answer.setFocusable(false);
                                                }
                                                else {
                                                    answerA_count += 1;
                                                }
                                                textView.setText(asocijacije.getText());
                                                textView.invalidate();
                                            }
                                        }
                                    }
                                    if(asocijacije.getColumnName().equals("b")){
                                        Log.i("mqtt","Column B subcribe: "+asocijacije.getColumnName());
                                        for (TextView textView : textViewsb){
                                            if(textView.getId() == asocijacije.getId()){
                                                textView.setText(asocijacije.getText());
//                                                b_answer.setEnabled(false);
//                                                b_answer.setFocusable(false);
                                                textView.invalidate();
                                            }
                                        }
                                    }
                                    if(asocijacije.getColumnName().equals("c")) {
                                        Log.i("mqtt","Column C subcribe: "+asocijacije.getColumnName());
                                        for (TextView textView : textViewsc) {
                                            if (textView.getId() == asocijacije.getId()) {
                                                textView.setText(asocijacije.getText());
//                                                c_answer.setEnabled(false);
//                                                c_answer.setFocusable(false);
                                                textView.invalidate();
                                            }
                                        }
                                    }
                                    if(asocijacije.getColumnName().equals("d")){
                                        Log.i("mqtt","Column D subcribe: "+asocijacije.getColumnName());
                                        for (TextView textView : textViewsd){
                                            if(textView.getId() == asocijacije.getId()){
                                                textView.setText(asocijacije.getText());
//                                                d_answer.setEnabled(false);
//                                                d_answer.setFocusable(false);
                                                textView.invalidate();
                                            }
                                        }
                                    }
                                    if(asocijacije.getColumnName().equals("e")){
                                        Log.i("mqtt","Column e subcribe: "+asocijacije.getColumnName());
                                        if(getArguments() == null){
                                            getParentFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.frameLayout2, AsocijacijeFragment.newInstance(true, true))
                                                    .setReorderingAllowed(true)
                                                    .commit();
                                        }else{
                                            getParentFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.frameLayout2, new HomeFragment())
                                                    .setReorderingAllowed(true)
                                                    .commit();
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            });

            mqttHandler.StringSubscribe(new MqttHandler.StringCallBack() {
                @Override
                public void OnCallBack(StrDTO strDTO) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if(strDTO != null && isMyTurn == false){
                                Toast.makeText(getActivity(), strDTO.getColumnName().toUpperCase(Locale.ROOT)
                                        +" try:"+strDTO.getText()+"", Toast.LENGTH_LONG).show();
                                setIsMyTurn();
                                Log.i("mqtt", "MyTurn: "+ isMyTurn+", " + "User: "+ strDTO.getUserName());
                            }
                        }
                    });
                }
            });

            mqttHandler.pointSubscribe(new MqttHandler.PointCallback() {
                @Override
                public void onCallback(UserDTO userDTO) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            player2Score.setText(userDTO.getPoints()+"");
                        }
                    });
                }
            });
        }


        countDownTimer = new CountDownTimer(1000000, 1000) {
            @Override
            public void onTick(long l) {
                Long min = ((l / 1000) % 3600) / 60;
                Long sec = (l / 1000) % 60;
                String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                if(isMyTurn){
                    whoseTurn.setText("Your Turn");
                }
                else {
                    whoseTurn.setText("Opponent Turn");
                }
                scoreTimer.setText(format);
            }

            @Override
            public void onFinish() {
                scoreTimer.setText("00:00");
                if(isOnline){
                    if(getArguments() == null){
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout2, AsocijacijeFragment.newInstance(true, true))
                                .setReorderingAllowed(true)
                                .commit();
                    }else{
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout2, new MojBrojFragment())
                                .setReorderingAllowed(true)
                                .commit();
                    }
                }else{
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout2, new MojBrojFragment())
                            .setReorderingAllowed(true)
                            .commit();
                }
            }
        }.start();

    }

    @Override
    public void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }
}