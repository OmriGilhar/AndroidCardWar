package com.example.msogcardwar.gamelogic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msogcardwar.R;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private String countryList[];
    private int icons[];
    private LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] countryList, int[] flags) {
        this.context = context;
        this.countryList = countryList;
        this.icons = flags;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.listview_activity, null);
        TextView country = (TextView) view.findViewById(R.id.winners_LBL_scores);
        ImageView icon = (ImageView) view.findViewById(R.id.icons_IMG_winners);
        country.setText(countryList[i]);
        icon.setImageResource(icons[i]);
        return view;
    }
}
