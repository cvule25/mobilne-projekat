package com.example.ma_projekat.Model;

import com.google.gson.annotations.SerializedName;

public class MojBroj {
    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String text;
    @SerializedName("isFirstRound")
    private boolean isFirstRound;
    @SerializedName("isSecondPlayerTurn")
    private boolean isSecondPlayerTurn;
    @SerializedName("userName")
    private String userName;
    public MojBroj(int id, String text, boolean isFirstRound , boolean isSecondPlayerTurn, String userName) {
        this.id = id;
        this.text = text;
        this.isFirstRound = isFirstRound;
        this.isSecondPlayerTurn = isSecondPlayerTurn;
        this.userName = userName;
    }
    public MojBroj() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean isFirstRound() {
        return isFirstRound;
    }
    public void setFirstRound(boolean firstRound) {
        isFirstRound = firstRound;
    }
    public boolean isSecondPlayerTurn() {
        return isSecondPlayerTurn;
    }
    public void setSecondPlayerTurn(boolean secondPlayerTurn) {
        isSecondPlayerTurn = secondPlayerTurn;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public String toString() {
        return "MojBroj{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isFirstRound=" + isFirstRound +
                ", isSecondPlayerTurn=" + isSecondPlayerTurn +
                ", userName='" + userName + '\'' +
                '}';
    }
}
