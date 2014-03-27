package com.fulp.fragments;

import java.util.Locale;

import com.fulp.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class InkomenToevoegenFragment extends Fragment implements OnItemSelectedListener, OnDateSetListener{
	private String mSelectedInkomstenType;
	private String mSelectedHerhaling;
	private View mRootView;
	private EditText mDateEdit;
	
	public InkomenToevoegenFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRootView = inflater.inflate(R.layout.inkomen_toevoegen, container, false);
        
        getActivity().setTitle(R.string.inkomen_toevoegen_title);
        //Setup spinners
        //Inkomstentype spinner
        Spinner inkomstenTypeSpinner = (Spinner)mRootView.findViewById(R.id.inkomsten_type_spinner);
		ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.inkomsten_types, android.R.layout.simple_spinner_item);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inkomstenTypeSpinner.setAdapter(typeAdapter);
		inkomstenTypeSpinner.setOnItemSelectedListener(this);
        //Herhaling spinner
		Spinner herhalingSpinner = (Spinner)mRootView.findViewById(R.id.inkomsten_herhaling_spinner);
		ArrayAdapter<CharSequence> herhalingAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.periodieke_herhaling, android.R.layout.simple_spinner_item);
		herhalingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		herhalingSpinner.setAdapter(herhalingAdapter);
		herhalingSpinner.setOnItemSelectedListener(this);
		
        return mRootView;
    }
    
    @Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    	Spinner spinner = (Spinner) parent;
    	if(spinner.getId() == R.id.inkomsten_type_spinner){
    		this.mSelectedInkomstenType = parent.getItemAtPosition(position).toString();
    	}
    	else if(spinner.getId() == R.id.inkomsten_herhaling_spinner){
    		this.mSelectedHerhaling = parent.getItemAtPosition(position).toString();
    	}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void selectDatum(View view){
		mDateEdit = (EditText)view;
		DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		monthOfYear++;
		mDateEdit.setText("" + monthOfYear + "-" + dayOfMonth + "-" + year);
	}
}
