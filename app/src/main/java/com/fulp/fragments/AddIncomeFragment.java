package com.fulp.fragments;

import com.fulp.R;
import com.fulp.domain.Income;
import com.fulp.listeners.DateSelectionListener;
import com.fulp.listeners.WebserviceListener;

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
import android.widget.Toast;

import java.util.List;

public class AddIncomeFragment extends Fragment implements OnItemSelectedListener, OnDateSetListener, WebserviceListener, DateSelectionListener {

	private String mSelectedInkomstenType;
	private String mSelectedInterval;
	private View mRootView;
	private EditText mDateEdit;
    private WebserviceListener webserviceListener;
	
	public AddIncomeFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRootView = inflater.inflate(R.layout.add_income, container, false);
        
        getActivity().setTitle(R.string.add_income_title);
        setupView();

        //Save button
        Button save = (Button)mRootView.findViewById(R.id.income_save_button);
        webserviceListener = this;


        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Income userIncome = createIncome();

                /*
                    Map<String, String> parameters = new HashMap<String, String>();

                    Income income = new Income();
                    income.setName("test");

                    CreateIncomeTask createIncomeTask = new CreateIncomeTask(webserviceListener, income);
                    createIncomeTask.execute();
                }
                else {
                    //Show message

                    //Initialize object and call webservice
                */

            }
        });
        return mRootView;
    }

    private void setupView() {
        //Setup spinners
        //Incometype spinner
        Spinner incomeTypeSpinner = (Spinner)mRootView.findViewById(R.id.add_income_type_spinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.income_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeTypeSpinner.setAdapter(typeAdapter);
        incomeTypeSpinner.setOnItemSelectedListener(this);
        //Interval spinner
        Spinner intervalSpinner = (Spinner)mRootView.findViewById(R.id.add_income_interval_spinner);
        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.intervals, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);
        intervalSpinner.setOnItemSelectedListener(this);
    }

    private Income createIncome() {
        String name = ((EditText)mRootView.findViewById(R.id.add_income_description)).getText().toString();
        String amountText = ((EditText) mRootView.findViewById(R.id.add_income_amount)).getText().toString();
        Double amount;
        if(!amountText.equals("")){
            amount = Double.parseDouble(amountText);
        }
        else{
            amount = 0.0;
        }
        String type = ((Spinner)mRootView.findViewById(R.id.add_income_type_spinner)).getSelectedItem().toString();
        String interval = ((Spinner)mRootView.findViewById(R.id.add_income_interval_spinner)).getSelectedItem().toString();
        String start = ((EditText)mRootView.findViewById(R.id.add_income_startdate_edit)).getText().toString();
        String end = ((EditText)mRootView.findViewById(R.id.add_income_enddate_edit)).getText().toString();
        try{
            Income income = new Income();
            income.setName(name);
            income.setAmount(amount);
            income.setType(type);
            income.setInterval(interval);
            income.setStart(start);
            income.setEnd(end);
            return income;
        }
        catch(IllegalArgumentException iae){
            Toast toast = Toast.makeText(getActivity(), iae.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
        return null;
    }

    @Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    	Spinner spinner = (Spinner) parent;
    	if(spinner.getId() == R.id.add_income_type_spinner){
    		this.mSelectedInkomstenType = parent.getItemAtPosition(position).toString();
    	}
    	else if(spinner.getId() == R.id.add_income_interval_spinner){
    		this.mSelectedInterval = parent.getItemAtPosition(position).toString();
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

    @Override
    public void onComplete(List<?> data) {
        Toast.makeText(getActivity(), data.get(0).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
