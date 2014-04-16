package com.fulp.fragments;

import com.fulp.R;
import com.fulp.listeners.DateSelectionListener;

import android.app.DatePickerDialog.OnDateSetListener;
import com.fulp.domain.Insurance;
import com.fulp.listeners.DateSelectionListener;
import com.fulp.listeners.WebserviceListener;
import com.fulp.tasks.WebserviceRequestTask;
import com.fulp.tasks.insurance.CreateInsuranceTask;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddInsuranceFragment extends Fragment implements AdapterView.OnItemSelectedListener, WebserviceListener, DatePickerDialog.OnDateSetListener, DateSelectionListener {

    private View mRootView;
    private String mSelectedInsuranceType;
    private WebserviceListener webserviceListener;
    private EditText mDateEdit;
    private String mSelectedInterval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRootView = inflater.inflate(R.layout.add_insurance, container, false);
        
        getActivity().setTitle(R.string.add_insurance_title);
        
        setupView();
        //Save button
        Button save = (Button)mRootView.findViewById(R.id.insurance_save_button);
        webserviceListener = this;


        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Insurance insurance = createInsurance();
                if(insurance != null){
                    WebserviceRequestTask createInsuranceTask = new CreateInsuranceTask(webserviceListener, insurance);
                    createInsuranceTask.execute();
                }
            }
        });
        return mRootView;
	}

    private Insurance createInsurance() {
        //get fields
        String name = ((EditText)mRootView.findViewById(R.id.add_insurance_insurer)).getText().toString();
        String amountText = ((EditText) mRootView.findViewById(R.id.add_insurance_premium)).getText().toString();
        Double amount;
        if(!amountText.equals("")){
            amount = Double.parseDouble(amountText);
        }
        else{
            amount = 0.0;
        }
        String type = ((Spinner)mRootView.findViewById(R.id.add_insurance_category_spinner)).getSelectedItem().toString();
        String interval = ((Spinner)mRootView.findViewById(R.id.add_insurance_interval_spinner)).getSelectedItem().toString();
        String start = ((EditText)mRootView.findViewById(R.id.add_insurance_startdate_edit)).getText().toString();
        String end = ((EditText)mRootView.findViewById(R.id.add_insurance_enddate_edit)).getText().toString();

        try{
            Insurance insurance = new Insurance();
            insurance.setName(name);
            insurance.setAmount(amount);
            insurance.setCategory(type);
            insurance.setInterval(interval);
            insurance.setStart(start);
            insurance.setEnd(end);
            return insurance;
        }
        catch(IllegalArgumentException iae){
            Toast toast = Toast.makeText(getActivity(), iae.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
        return null;
    }

    private void setupView() {
        //Category
        Spinner typeSpinner = (Spinner)mRootView.findViewById(R.id.add_insurance_category_spinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.insurance_categories, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);
        //Interval
        Spinner intervalSpinner = (Spinner)mRootView.findViewById(R.id.add_insurance_interval_spinner);
        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.intervals, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);
        intervalSpinner.setOnItemSelectedListener(this);
    }

    public void selectDatum(View view){
        mDateEdit = (EditText)view;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        mDateEdit.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)parent;
        if(spinner.getId() == R.id.add_insurance_category_spinner){
            this.mSelectedInsuranceType = parent.getItemAtPosition(position).toString();
        }
        else if(spinner.getId() == R.id.add_insurance_interval_spinner){
            this.mSelectedInterval = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onComplete(List<?> data) {
        Toast.makeText(getActivity(), data.get(0).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
