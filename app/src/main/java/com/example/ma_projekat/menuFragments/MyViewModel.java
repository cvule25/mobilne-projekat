package com.example.ma_projekat.menuFragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Boolean> isFirstRoundLiveData = new MutableLiveData<>(true);

    public void setIsFirstRound(boolean isFirstRound) {
        isFirstRoundLiveData.setValue(isFirstRound);
    }

    public LiveData<Boolean> getIsFirstRoundLiveData() {
        return isFirstRoundLiveData;
    }
}
