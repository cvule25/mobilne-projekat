package com.example.ma_projekat.Utils;

import android.app.Activity;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ma_projekat.R;

public class ShowHideElements {
//    public static void showScoreBoard(Activity activity) {
//
//        activity.findViewById(R.id.score_board).setVisibility(View.VISIBLE);
//    }
//
//    public static void hideScoreBoard(Activity activity) {
//
//        activity.findViewById(R.id.score_board).setVisibility(View.GONE);
//    }

    public static void lockDrawerLayout(Activity activity) {

        DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public static void unlockDrawerLayout(Activity activity) {
        DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }
}
