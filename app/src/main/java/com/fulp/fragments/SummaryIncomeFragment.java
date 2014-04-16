package com.fulp.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fulp.R;
import com.fulp.activities.MainActivity;
import com.fulp.domain.Income;
import com.fulp.listeners.DateSelectionListener;
import com.fulp.listeners.WebserviceListener;
import com.fulp.tasks.WebserviceRequestTask;
import com.fulp.tasks.income.CreateIncomeTask;
import com.fulp.tasks.income.ListIncomeTask;

import java.util.List;

public class SummaryIncomeFragment extends Fragment implements WebserviceListener {
    private View mRootView;

    public SummaryIncomeFragment() {
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


        ListIncomeTask listIncomeTask = new ListIncomeTask(this, ((MainActivity)this.getActivity()).getUser());
        listIncomeTask.execute();

        getActivity().setTitle("Inkomen overzicht");

        return mRootView;
    }

    public void addInsurance(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        AddIncomeFragment addIncomeFragment = new AddIncomeFragment();
        transaction.replace(R.id.content_frame, addIncomeFragment);
        transaction.commit();
    }

    @Override
    public void onComplete(List<?> data) {
        


        Toast.makeText(getActivity(), ((Income)data.get(0)).getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
