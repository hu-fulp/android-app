package com.fulp.fragments;

import com.fulp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddInsuranceFragment extends Fragment {
	private View mRootView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRootView = inflater.inflate(R.layout.add_insurance, container, false);
        
        getActivity().setTitle(R.string.add_insurance_title);
        
        return mRootView;
	}
}
