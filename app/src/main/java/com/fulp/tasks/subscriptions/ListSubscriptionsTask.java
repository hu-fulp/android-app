package com.fulp.tasks.subscriptions;

import com.fulp.domain.Insurance;
import com.fulp.domain.Subscription;
import com.fulp.domain.User;
import com.fulp.listeners.WebserviceListener;
import com.fulp.tasks.WebserviceRequestTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royfokker on 02-04-14.
 */

public class ListSubscriptionsTask extends WebserviceRequestTask {

    private final WebserviceListener listener;

    public ListSubscriptionsTask(WebserviceListener listener, User user) {
        super("Subscriptions", user.getToken(), String.valueOf(user.getId()));
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFailure(msg);
            return;
        }

        List<Subscription> output = new ArrayList<Subscription>();

        try {
            JSONObject json = new JSONObject(sJson);
            JSONArray items = json.getJSONArray("items");


            for(int i = 0; i < items.length(); i++) {
                JSONObject o =  items.getJSONObject(i);

                Subscription subscription = new Subscription();
                subscription.setStart(o.getString("start"));
                subscription.setEnd(o.getString("end"));
                subscription.setInterval(o.getString("interval"));
                subscription.setAmount(o.getDouble("amount"));
                subscription.setName(o.getString("name"));

                output.add(subscription);
            }

        }
        catch(JSONException e) {
            msg = "Invalid response";
            if(listener != null) listener.onFailure(msg);
            return;
        }

        if(listener != null) listener.onComplete(output);
    }
}
