package com.example.ma_projekat.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ma_projekat.Model.Data;
import com.example.ma_projekat.Model.User;
import com.example.ma_projekat.R;
import com.example.ma_projekat.menuFragments.LoadingScreenFragment;

import java.util.ArrayList;
import java.util.List;

public class ProgramAdapter extends ArrayAdapter<String> {
    AppCompatActivity activity;

    View view;
    Context context;
    String[] title;
    String[] description;

    String[] title1;
    String[] description1;
    LayoutInflater layoutInflater;

    List<User> users = new ArrayList<>();
    public ProgramAdapter(AppCompatActivity activity, Context context, String[] title, String[] description) {
        super(context, R.layout.single_item, R.id.titleId, title);
        this.activity = activity;
        this.context = context;
        this.title = title;
        this.description = description;
    }

    public ProgramAdapter(AppCompatActivity activity, Context context, String[] title, String[] description, boolean bool) {
        super(context, R.layout.single_item, R.id.rank_title_id, title);
        this.activity = activity;
        this.context = context;
        this.title1 = title;
        this.description1 = description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View singleitem = convertView;

        if(singleitem == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleitem = layoutInflater.inflate(R.layout.single_item, null);
            Log.d("Adapter", "Inflated single_item layout");
        }

        if(title != null && description != null){
            TextView title = singleitem.findViewById(R.id.titleId);
            TextView description = singleitem.findViewById(R.id.descriptionId);



            title.setText(this.title[position]);
            description.setText(this.description[position]);

        }
        else{
            TextView title = singleitem.findViewById(R.id.titleId);
            TextView description = singleitem.findViewById(R.id.descriptionId);

            title.setText(this.title1[position]);
            description.setText(this.description1[position]);
        }


        return singleitem;
    }
}
