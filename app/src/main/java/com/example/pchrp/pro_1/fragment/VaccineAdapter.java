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
import com.example.pchrp.pro_1.manager.ProvinsiVaccine;

import java.util.List;

import static com.example.pchrp.pro_1.R.layout.list_vaccine;

class VaccineAdapter extends ArrayAdapter<ProvinsiVaccine> {

    private List<ProvinsiVaccine> provinsiList;
    Context mContext;

    public VaccineAdapter(List<ProvinsiVaccine> p, Context context) {
        super(context, R.layout.list_vaccine,p);
        this.provinsiList = p;
        this.mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_vaccine,null,true);

           TextView vaccine = (TextView) view.findViewById(R.id.vaccine);
           TextView type = (TextView) view.findViewById(R.id.type);

           ProvinsiVaccine provinsi = provinsiList.get(position);

           vaccine.setText(provinsi.getTopics());
           type.setText(provinsi.getSide());

        return view;
    }

}
