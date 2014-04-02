package com.fulp.fragments;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fulp.R;
import com.fulp.domain.Income;
import com.fulp.domain.Subscription;
import com.fulp.listeners.DateSelectionListener;
import com.fulp.listeners.WebserviceListener;
import com.fulp.tasks.PostDataTask;
import com.fulp.tasks.income.CreateIncomeTask;
import com.fulp.tasks.subscriptions.CreateSubscriptionTask;

import java.util.List;

/**
 * Created by roystraub on 02-04-14.
 */
public class AddSubscriptionFragment extends Fragment implements OnDateSetListener, DateSelectionListener, AdapterView.OnItemSelectedListener, WebserviceListener {
    private View mRootView;
    private EditText mDateEdit;
    private String mSelectedCategory;
    private String mSelectedInterval;
    private WebserviceListener webserviceListener;

    public AddSubscriptionFragment(){
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.add_subscription, container, false);

        getActivity().setTitle(R.string.add_subscription_title);
        setupView();
        webserviceListener = this;

        //Save button
        Button save = (Button)mRootView.findViewById(R.id.add_subscription_save);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                boolean validInput = ValidateInput();
                if(validInput){
                    Subscription subscription = new Subscription();
                    subscription.setName("test");
                    subscription.setAmount(1);
                    subscription.setStart("2014-01-01");
                    subscription.setEnd("2015-01-01");
                    subscription.setInterval("month");
                    subscription.setCategory("internet");

                    PostDataTask createSubscriptionTask = new CreateSubscriptionTask(webserviceListener, subscription);
                    createSubscriptionTask.execute();
                }
            }
        });
        return mRootView;
    }

    private void setupView() {
        //Setup spinners
        //Category spinner
        Spinner categorySpinner = (Spinner)mRootView.findViewById(R.id.add_subscription_category_spinner);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.subscription_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);
        //Interval spinner
        Spinner intervalSpinner = (Spinner)mRootView.findViewById(R.id.add_subscription_interval_spinner);
        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.intervals, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);
        intervalSpinner.setOnItemSelectedListener(this);
    }

    private boolean ValidateInput() {
        return true;
    }

    @Override
    public void selectDatum(View view) {
        mDateEdit = (EditText)view;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        mDateEdit.setText("" + dayOfMonth + "-" + monthOfYear + "-" + year);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.add_subscription_category_spinner){
            this.mSelectedCategory = parent.getItemAtPosition(position).toString();
        }
        else if(spinner.getId() == R.id.add_subscription_interval_spinner){
            this.mSelectedInterval = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
