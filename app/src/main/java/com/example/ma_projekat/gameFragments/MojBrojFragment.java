package com.example.ma_projekat.gameFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ma_projekat.Model.Data;
import com.example.ma_projekat.Model.KorakPoKorak;
import com.example.ma_projekat.Model.MojBroj;
import com.example.ma_projekat.Model.UserDTO;
import com.example.ma_projekat.R;
import com.example.ma_projekat.Repository.UserRepository;
import com.example.ma_projekat.Utils.MqttHandler;
import com.example.ma_projekat.menuFragments.HomeFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class MojBrojFragment extends Fragment {
    View view;
    CountDownTimer countDownTimer;
    AppCompatActivity activity;
    MqttHandler mqttHandler = new MqttHandler();
    TextView scoreTimer, targetNumber;
    UserRepository userRepository = new UserRepository();
    int deleteStart = 0;
    boolean isTargetSet = false, isAllStopped = false;
    Button btnDelete, number1, number2, number3, number4, number5, number6, symbolBracketLeft, symbolBracketRight, playerAttempt;
    Button symbolAdd, symbolSub, symbolMulti, symbolDivide, btnStop;
    TextView displayResult, player1Score, player2Score;
    Double player1attempt, player2attempt;
    double result, player2Attempt = 0.0;
    boolean isMyTurn, isFirstRound = true, isOnline, gameDone;
    boolean isAnswerCorrect = false;
    ArrayList<String> expressionList = new ArrayList<>();
    public void setIsMyTurn(){
        if(isMyTurn){
            isMyTurn = false;
        }else{
            isMyTurn = true;
        }
    }
    public static MojBrojFragment newInstance(boolean round, boolean bool) {

        Bundle args = new Bundle();
        args.putBoolean("isFirstRound", round);
        args.putBoolean("isOnline", bool);

        MojBrojFragment fragment = new MojBrojFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mojbroj, container, false);

        targetNumber = view.findViewById(R.id.mojbroj_dobijenBroj);
        btnDelete = view.findViewById(R.id.mojbroj_ponisti);

        displayResult = view.findViewById(R.id.mojbroj_rezultat);
        btnStop = view.findViewById(R.id.mojbroj_generisibroj);
        number1 = view.findViewById(R.id.mojbroj_broj1);
        number2 = view.findViewById(R.id.mojbroj_broj2);
        number3 = view.findViewById(R.id.mojbroj_broj3);
        number4 = view.findViewById(R.id.mojbroj_broj4);
        number5 = view.findViewById(R.id.mojbroj_broj5);
        number6 = view.findViewById(R.id.mojbroj_broj6);
        symbolBracketLeft = view.findViewById(R.id.mojbroj_levazagrada);
        symbolBracketRight = view.findViewById(R.id.mojbroj_desnazagrada);
        symbolAdd = view.findViewById(R.id.mojbroj_plus);
        symbolSub = view.findViewById(R.id.mojbroj_minus);
        symbolMulti = view.findViewById(R.id.mojbroj_mnozenje);
        symbolDivide = view.findViewById(R.id.mojbroj_deljenje);
        scoreTimer = activity.findViewById(R.id.time);

        player1Score = activity.findViewById(R.id.player1Score);
        player2Score = activity.findViewById(R.id.player2Score);
        playerAttempt = view.findViewById(R.id.playerAttempt);
        isOnline = Data.isOnline;

        StringBuffer stringBuffer = new StringBuffer();
        isOnline = Data.isOnline;
        if(isOnline){
            isMyTurn = mqttHandler.getTurnPlayer();
            if (getArguments() != null) {
                setIsMyTurn();
            }
        }

        Button btnCheck = view.findViewById(R.id.mojbroj_potrvdi);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMyTurn || !isOnline){
                    try {
                        Expression expression = new ExpressionBuilder(TextUtils.join("", expressionList)).build();
                        result = expression.evaluate();
                    } catch (Exception e) {
                        Toast.makeText(activity.getApplicationContext(), "Incorrect expression", Toast.LENGTH_SHORT).show();
                    }

                    if (result != 0.00) {
                        displayResult.setText(result + "");
                        if (result == Double.parseDouble((String) targetNumber.getText())) {
                            int score = Integer.parseInt((String) player1Score.getText());
                            score += 20;
                            player1Score.setText(score + "");
                            Toast.makeText(activity.getApplicationContext(), "Correct! Points: +20", Toast.LENGTH_SHORT).show();
                            if (isOnline) {
                                Data.loggedInUser.setKorakPoKorak(Data.loggedInUser.getKorakPoKorak() + score);
                                userRepository.updateKorakPoKorak(Data.loggedInUser, Data.loggedInUser.getKorakPoKorak() + score);
                                mqttHandler.pointPublish(score);
                            }
                            if (!isOnline) {
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frameLayout2, new AsocijacijeFragment())
                                        .setReorderingAllowed(true)
                                        .commit();
                            }
                                if(isOnline){
                                    if(isFirstRound){
                                        getParentFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.frameLayout2, MojBrojFragment.newInstance(true, true))
                                                .setReorderingAllowed(true)
                                                .commit();
                                    }
                                    else {
                                        getParentFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.frameLayout2, new AsocijacijeFragment())
                                                .setReorderingAllowed(true)
                                                .commit();
                                    }
                                }
                                else {
                                    getParentFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.frameLayout2, new AsocijacijeFragment())
                                            .setReorderingAllowed(true)
                                            .commit();
                                     }
                        } else {
                            if(isOnline){
                                setIsMyTurn();
                            }
                            if(isOnline){
                                if(isFirstRound){
                                    isFirstRound = false;
                                    getParentFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.frameLayout2, MojBrojFragment.newInstance(true, true))
                                            .setReorderingAllowed(true)
                                            .commit();
                                }
                                else {
                                    getParentFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.frameLayout2, new AsocijacijeFragment())
                                            .setReorderingAllowed(true)
                                            .commit();
                                }
                            }
                            else {
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frameLayout2, new AsocijacijeFragment())
                                        .setReorderingAllowed(true)
                                        .commit();
                            }
                            Toast.makeText(activity.getApplicationContext(), "Incorrect! Points: +0", Toast.LENGTH_SHORT).show();
                            }
                        if(isOnline){
                            playerAttempt.setText(result + "");
                            mqttHandler.mojBrojPublish(playerAttempt);
                        }
                        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                            @Override
                            public void onTick(long l) {
                                Long min = ((l / 1000) % 3600) / 60;
                                Long sec = (l / 1000);
                            }

                            @Override
                            public void onFinish() {
//                                if(isOnline){
//                                    if(isFirstRound){
//                                        getParentFragmentManager()
//                                                .beginTransaction()
//                                                .replace(R.id.frameLayout2, MojBrojFragment.newInstance(true, true))
//                                                .setReorderingAllowed(true)
//                                                .commit();
//                                    }
//                                }
//                                else {
//                                    getParentFragmentManager()
//                                            .beginTransaction()
//                                            .replace(R.id.frameLayout2, new SpojniceFragment())
//                                            .setReorderingAllowed(true)
//                                            .commit();
//                                }
                            }
                        }.start();
                        btnCheck.setOnClickListener(null);
                    }
                }
            }

        });

//        btnStop = view.findViewById(R.id.numbers_stop);
        if(isMyTurn || !isOnline){
            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!isTargetSet) {
                        int randomTarget = ThreadLocalRandom.current().nextInt(100, 999 + 1);
                        targetNumber.setText(randomTarget + "");
                        if(isOnline){
                            mqttHandler.mojBrojPublish((Button) targetNumber);
                        }
                        isTargetSet = true;
                    } else {
                        int randomNum1 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                        number1.setText(randomNum1 + "");
                        if(isOnline){
                            mqttHandler.mojBrojPublish(number1);
                        }
                        int randomNum2 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                        number2.setText(randomNum2 + "");
                        if(isOnline){
                            mqttHandler.mojBrojPublish(number2);
                        }
                        int randomNum3 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                        number3.setText(randomNum3 + "");
                        if(isOnline){
                            mqttHandler.mojBrojPublish(number3);
                        }

                        int randomNum4 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                        number4.setText(randomNum4 + "");
                        if(isOnline){
                            mqttHandler.mojBrojPublish(number4);
                        }
                        int randomNum5 = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                        int randomNum6 = ThreadLocalRandom.current().nextInt(1, 4 + 1);
                        switch (randomNum5) {
                            case 1:
                                number5.setText(10 + "");
                                if(isOnline){
                                    mqttHandler.mojBrojPublish(number5);
                                }
                                break;
                            case 2:
                                number5.setText(15 + "");
                                if(isOnline){
                                    mqttHandler.mojBrojPublish(number5);
                                }
                                break;
                            case 3:
                                number5.setText(20 + "");
                                if(isOnline){
                                    mqttHandler.mojBrojPublish(number5);
                                }
                                break;
                        }
                        switch (randomNum6) {
                            case 1:
                                number6.setText(25 + "");
                                if(isOnline){
                                    mqttHandler.mojBrojPublish(number6);
                                }
                                break;
                            case 2:
                                number6.setText(50 + "");
                                if(isOnline){
                                    mqttHandler.mojBrojPublish(number6);
                                }
                                break;
                            case 3:
                                number6.setText(75 + "");
                                if(isOnline){
                                    mqttHandler.mojBrojPublish(number6);
                                }
                                break;
                            case 4:
                                number6.setText(100 + "");
                                if(isOnline){
                                    mqttHandler.mojBrojPublish(number6);
                                }
                                break;
                        }
                        isAllStopped = true;
                    }
                    if (isAllStopped) {
                        btnStop.setOnClickListener(null);
                    }
                }
            });
        }
        number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) number1.getText());
                    display();
                }
            }
        });
        number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) number2.getText());
                    display();
                }
            }
        });
        number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) number3.getText());
                    display();
                }
            }
        });
        number4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) number4.getText());
                    display();
                }
            }
        });
        number5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) number5.getText());
                    display();
                }
            }
        });
        number6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) number6.getText());
                    display();
                }
            }
        });
        symbolBracketLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) symbolBracketLeft.getText());
                    display();
                }
            }
        });

        symbolBracketRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) symbolBracketRight.getText());
                    display();
                }
            }
        });
        symbolAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) symbolAdd.getText());
                    display();
                }
            }
        });
        symbolSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) symbolSub.getText());
                    display();
                }
            }
        });
        symbolMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) symbolMulti.getText());
                    display();
                }
            }
        });
        symbolDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped || !isMyTurn) {
                    expressionList.add((String) symbolDivide.getText());
                    display();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((isAllStopped || !isMyTurn) && expressionList.size() > 0) {
                    expressionList.remove(expressionList.size() - 1);
                    display();
                }
            }
        });
        return view;
    }

    public void isFragment(boolean bool) {
        if (isAdded()) {
            if(bool){
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout2, MojBrojFragment.newInstance(true, true))
                        .setReorderingAllowed(true)
                        .commit();
            }else{
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout2, new AsocijacijeFragment())
                        .setReorderingAllowed(true)
                        .commit();
            }
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();


        String p2name = "";
        if(isOnline){
            p2name = Data.user2.getUsername();
        }

        if(Data.loggedInUser != null && !p2name.equals("Guest")){
            mqttHandler.mojBrojSubscribe(new MqttHandler.MojBrojCallback() {
                @Override
                public void onCallBack(MojBroj mojBroj) {
                    if(mojBroj != null){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(mojBroj.getId() == number1.getId()){
                                    number1.setText(mojBroj.getText());
                                }
                                else if (mojBroj.getId() == number2.getId()){
                                    number2.setText(mojBroj.getText());
                                }
                                else if (mojBroj.getId() == number3.getId()){
                                    number3.setText(mojBroj.getText());
                                }
                                else if (mojBroj.getId() == number4.getId()){
                                    number4.setText(mojBroj.getText());
                                }
                                else if (mojBroj.getId() == number5.getId()){
                                    number5.setText(mojBroj.getText());
                                }
                                else if (mojBroj.getId() == number6.getId()){
                                    number6.setText(mojBroj.getText());
                                }
                                else if (mojBroj.getId() == targetNumber.getId()){
                                    targetNumber.setText(mojBroj.getText());
                                }
//                                else if (mojBroj.getId() == playerAttempt.getId()){
//                                    player2Attempt = Double.parseDouble(playerAttempt.toString());
//                                }
//                                if(gameDone){
//                                    if(savedInstanceState == null){
//                                        getParentFragmentManager().beginTransaction().replace(R.id.frameLayout2, new KorakPoKorakFragment().newInstance(true, true)).commit();
//                                        isFirstRound = false;
//                                    }else{
////                                   associationsFragment.setIsOnline(true);
//                                        getParentFragmentManager().beginTransaction().replace(R.id.frameLayout2, new AsocijacijeFragment()).commit();
//                                    }
//                                }

                            }
                        });
                    }
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
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
//                int score = Integer.parseInt((String) player1Score.getText());
//                int score2 = Integer.parseInt((String) player2Score.getText());
//                if(isOnline && (result != 0.0 && player2Attempt != 0.0)){
//                    if(isFirstRound){
//                        if(result == Double.parseDouble((String) targetNumber.getText())){
//                            score += 20;
//                        }
//                        else if (player2Attempt == Double.parseDouble((String) targetNumber.getText())){
//                            score2 += 20;
//                        }
//                        else {
//                            Double odg1razlika = Double.parseDouble((String) targetNumber.getText()) - result;
//                            Double odg2razlika = Double.parseDouble((String) targetNumber.getText()) - player2Attempt;
//                            if(odg2razlika > odg1razlika){
//                                score += 10;
//                            }
//                            else {
//                                score2 += 10;
//                            }
//                        }
//                        player1Score.setText(score + "");
//                        player2Score.setText(score + "");
//                    }
//                    else {
//                        if(player2Attempt == Double.parseDouble((String) targetNumber.getText())){
//                            score2 +=20;
//                        }
//                        else if (result == Double.parseDouble((String) targetNumber.getText())){
//                            score += 20;
//                        }
//                        else {
//                            Double odg1razlika = Double.parseDouble((String) targetNumber.getText()) - result;
//                            Double odg2razlika  = Double.parseDouble((String) targetNumber.getText()) - player2Attempt;
//                            if(odg2razlika > odg1razlika){
//                                score += 10;
//                            }
//                            else {
//                                score2 += 10;
//                            }
//                        }
//                        player1Score.setText(score + "");
//                        player2Score.setText(score + "");
//                    }
//                }
                Long min = ((l / 1000) % 3600) / 60;
                Long sec = (l / 1000) % 60;
                String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                scoreTimer.setText(format);
                if (scoreTimer.getText().equals("00:55")) {
                    btnStop.performClick();
                    btnStop.performClick();
                }
            }
            @Override
            public void onFinish() {
                scoreTimer.setText("00:00");
            }
        }.start();
    }
    @Override
    public void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }
    public void display() {
        displayResult.setText(TextUtils.join(" ", expressionList));
    }
}