package com.fulp.fragments;

import com.fulp.R;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class AddIncomeFragment extends Fragment implements OnItemSelectedListener, OnDateSetListener{
	private String mSelectedInkomstenType;
	private String mSelectedHerhaling;
	private View mRootView;
	private EditText mDateEdit;
	
	public AddIncomeFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRootView = inflater.inflate(R.layout.add_income, container, false);
        
        getActivity().setTitle(R.string.add_income_title);
        //Setup spinners
        //Incometype spinner
        Spinner incomeTypeSpinner = (Spinner)mRootView.findViewById(R.id.inkomsten_type_spinner);
		ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.income_types, android.R.layout.simple_spinner_item);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		incomeTypeSpinner.setAdapter(typeAdapter);
		incomeTypeSpinner.setOnItemSelectedListener(this);
        //Interval spinner
		Spinner intervalSpinner = (Spinner)mRootView.findViewById(R.id.inkomsten_herhaling_spinner);
		ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.intervals, android.R.layout.simple_spinner_item);
		intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		intervalSpinner.setAdapter(intervalAdapter);
		intervalSpinner.setOnItemSelectedListener(this);
		//Save button
        Button save = (Button)mRootView.findViewById(R.id.income_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                boolean validInput = ValidateInput();
                if(validInput){
                    //Call webservice
                }
                else {
                    //Show message
                }
            }
        });
        return mRootView;
    }

    private boolean ValidateInput() {
        return true;
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
		mDateEdit.setText("" + dayOfMonth + "-" + monthOfYear + "-" + year);
	}
}
