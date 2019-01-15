package com.example.pchrp.pro_1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.pchrp.pro_1.R;
import com.example.pchrp.pro_1.fragment.FragmentHome;
import com.example.pchrp.pro_1.fragment.FragmentInsentKid;

public class AddBabyActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbaby);

        initInstances();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_addbaby, FragmentInsentKid.newInstance())
                    .commit();

        }
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_addbaby);
        setSupportActionBar(toolbar);
    }

}
