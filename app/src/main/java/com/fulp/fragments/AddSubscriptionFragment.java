package com.fulp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fulp.R;

/**
 * Created by roystraub on 02-04-14.
 */
public class AddSubscriptionFragment extends Fragment {
    private View mRootView;

    public AddSubscriptionFragment(){
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.add_subscription, container, false);

        getActivity().setTitle(R.string.add_subscription_title);
        
        return mRootView;
    }
}
