package com.example.pchrp.pro_1.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pchrp.pro_1.R;
import com.example.pchrp.pro_1.manager.Provinsi;

import java.util.List;

class MyAdapter extends ArrayAdapter<Provinsi> {

    private List<Provinsi> provinsiList;
    Context mContext;

    public MyAdapter(List<Provinsi> p,Context context) {
        super(context, R.layout.list_development,p);
        this.provinsiList = p;
        this.mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_development,null,true);

           TextView topics = (TextView) view.findViewById(R.id.topics);
           TextView side = (TextView) view.findViewById(R.id.side);

           Provinsi provinsi = provinsiList.get(position);

           topics.setText(provinsi.getTopics());
           side.setText(provinsi.getSide());



        return view;
    }

}
