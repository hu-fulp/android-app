package com.fulp.fragments;

import com.fulp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VerzekeringenToevoegenFragment extends Fragment {
	private View mRootView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRootView = inflater.inflate(R.layout.verzekering_toevoegen, container, false);
        
        getActivity().setTitle(R.string.verzekering_toevoegen_title);
        
        return mRootView;
	}
}
