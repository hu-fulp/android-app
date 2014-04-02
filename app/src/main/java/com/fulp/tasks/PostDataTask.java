package com.fulp.tasks;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by royfokker on 02-04-14.
 */

public abstract class PostDataTask extends AsyncTask<String, Void, String> {

    protected String msg;
    protected Map<String, String> parameters = new HashMap<String, String>();
    protected String endpoint = "http://149.210.161.130/fulp_webservice/public/1/";
    protected String resource;
    protected abstract void onPostExecute(String sJson);

    @Override
    protected String doInBackground(String... args) {
        String url = this.endpoint + this.resource;

        if(this.resource == null) return null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(url);

            List<NameValuePair> params = new LinkedList<NameValuePair>();

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            postMethod.setEntity(new UrlEncodedFormEntity(params));
            String encoded = Base64.encodeToString(("7d2b63335b8a1cfa4d55927af70e801a4eb0c88a07cb40cfb79165b9e2919e7d5a36e112d1ed1b5969399664ae56fb51093bd2f6b210c766ddb4640f2e473e9b" + ":" + "7").getBytes(), Base64.NO_WRAP);
            postMethod.setHeader("Authorization", "Basic "+encoded);
            String output = httpClient.execute(postMethod,responseHandler);

            Log.d("API response: ", output);

            return output;
        }
        catch(IOException e){
            e.printStackTrace();
            msg = e.getMessage();
        }

        return null;
    }


    /**
     * This function will convert response stream into json string
     * @param is respons string
     * @return json string
     * @throws java.io.IOException
     */
    public String streamToString(final InputStream is) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                throw e;
            }
        }

        return sb.toString();
    }
}
