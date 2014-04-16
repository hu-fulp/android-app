package com.fulp.fragments;

import com.fulp.R;
import com.fulp.listeners.DateSelectionListener;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

public class AddInsuranceFragment extends Fragment implements OnDateSetListener, DateSelectionListener {
	private View mRootView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRootView = inflater.inflate(R.layout.add_insurance, container, false);
        
        getActivity().setTitle(R.string.add_insurance_title);
        
        return mRootView;
	}

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {

    }

    @Override
    public void selectDatum(View view) {

    }
}
