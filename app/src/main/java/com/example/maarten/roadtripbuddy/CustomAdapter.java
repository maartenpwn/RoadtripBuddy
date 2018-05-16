package com.example.maarten.roadtripbuddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(@NonNull Context context, String[] citys) {
        super(context, R.layout.custom_row, citys);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row, parent, false);

        String singleCity = getItem(position);
        TextView myText = (TextView) customView.findViewById(R.id.myTitle);
        ImageView myImage = (ImageView) customView.findViewById(R.id.myImage);

        myText.setText(singleCity);
        myImage.setImageResource(R.mipmap.city);
        return customView;
    }
}
