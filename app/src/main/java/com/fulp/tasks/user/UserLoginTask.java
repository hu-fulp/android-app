package com.fulp.tasks.user;

import android.provider.Settings;

import com.fulp.domain.Account;
import com.fulp.domain.User;
import com.fulp.listeners.WebserviceListener;
import com.fulp.tasks.WebserviceRequestTask;
import com.fulp.domain.Subscription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royfokker on 02-04-14.
 */

public class UserLoginTask extends WebserviceRequestTask {

    private final WebserviceListener listener;
    private User user;

    public UserLoginTask(WebserviceListener listener, User user) {
        super("User/login", user.getEmail(), user.getPassword());

        this.user = user;

        this.listener = listener;

        parameters.put("android_device_id", user.getAndroidId());
    }

    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFailure(msg);
            return;
        }

        try {
            JSONObject json = new JSONObject(sJson);

            JSONObject auth = json.getJSONObject("auth");
            user.setToken(auth.getString("token"));
            user.setId(auth.getInt("user_id"));


            JSONArray accounts = json.getJSONArray("Account");

            for(int i = 0; i < accounts.length(); i++) {
                JSONObject o =  accounts.getJSONObject(i);

                Account account = new Account();
                account.setId(o.getInt("id"));
                account.setName(o.getString("name"));

                user.addAccount(account);
            }
         }
        catch(JSONException e) {
            msg = "Invalid response";
            if(listener != null) listener.onFailure(msg);
            return;
        }

        if(listener != null) listener.onComplete(null);
    }
}
