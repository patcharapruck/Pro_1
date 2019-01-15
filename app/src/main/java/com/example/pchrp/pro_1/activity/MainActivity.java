package com.example.pchrp.pro_1.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.pchrp.pro_1.R;
import com.example.pchrp.pro_1.fragment.FragmentLogin;

public class MainActivity extends AppCompatActivity {

    public Activity act = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameContainer, FragmentLogin.newInstance())
                    .commit();

        }
    }
}
