package com.example.pchrp.pro_1.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pchrp.pro_1.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DialogFragmentPutDev extends DialogFragment {

    private static final String TAG = "DialogFragmentPutDev";

    ImageView imageView_dev;
    TextView topics_dev,side_dev;
    EditText date;
    RadioGroup radioGroup;

    public DialogFragmentPutDev() {
        super();
    }

    public static DialogFragmentPutDev newInstance() {
        DialogFragmentPutDev fragment = new DialogFragmentPutDev();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_development, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        imageView_dev = (ImageView) rootView.findViewById(R.id.imageview_dev);
        topics_dev = (TextView) rootView.findViewById(R.id.topicsShow);
        side_dev = (TextView) rootView.findViewById(R.id.sideShow);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.check_dev);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
