package com.fulp.listeners;

import android.app.DialogFragment;
import android.app.Fragment;
import android.view.View;

import com.fulp.fragments.DatePickerFragment;

/**
 * Created by Daantie on 3-4-14.
 */
public class DateClickListener implements View.OnClickListener {
    private Fragment fragment;

    public DateClickListener(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(fragment, 1);
        newFragment.show(fragment.getFragmentManager(), "datePicker");
    }
}
