package com.fulp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fulp.R;

public class DashboardFragment extends Fragment {
private View mRootView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRootView = inflater.inflate(R.layout.dashboard, container, false);
        
        getActivity().setTitle(R.string.dashboard_title);
        
        return mRootView;
	}
}
