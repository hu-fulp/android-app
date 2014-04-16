package com.fulp.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fulp.R;

/**
 * Created by Daantie on 4-4-14.
 */
public class SummaryInsuranceFragment extends Fragment {
    private View mRootView;

    public SummaryInsuranceFragment() {
        // Empty constructor required for fragment subclasses
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.summary, container, false);

        Button button = (Button)mRootView.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                addInsurance(v);
            }
        });

        getActivity().setTitle("Verzekeringen overzicht");

        return mRootView;
    }

    public void addInsurance(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        AddInsuranceFragment addInsuranceFragment = new AddInsuranceFragment();
        transaction.replace(R.id.content_frame, addInsuranceFragment);
        transaction.commit();
    }
}