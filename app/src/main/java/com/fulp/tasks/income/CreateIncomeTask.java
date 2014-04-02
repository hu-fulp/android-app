package com.fulp.tasks.income;

import com.fulp.domain.Income;
import com.fulp.listeners.WebserviceListener;
import com.fulp.tasks.PostDataTask;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by royfokker on 02-04-14.
 */

public class CreateIncomeTask extends PostDataTask {

    private final WebserviceListener listener;

    public CreateIncomeTask(WebserviceListener listener, Income income) {
        this.listener = listener;

        parameters.put("name", income.getName());
        parameters.put("start", income.getStart());
        parameters.put("end", income.getEnd());
        parameters.put("interval", income.getInterval());
        parameters.put("amount", String.valueOf(income.getAmount()));
        parameters.put("type", income.getType());

        this.resource = "Income/create";
    }

    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFailure(msg);
            return;
        }

        List<String> output = new ArrayList<String>();

        try {
            JSONObject json = new JSONObject(sJson);

            output.add(json.getString("type"));
            output.add(json.getString("message"));

        }
        catch(JSONException e) {
            msg = "Invalid response";
            if(listener != null) listener.onFailure(msg);
            return;
        }

        if(listener != null) listener.onComplete(output);
    }
}
