package com.mock.rentapanda.rentapanda;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by martinyeh on 16/4/10.
 */


class MyAsyncTask extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {


    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

    //private Context context;

    private AsyncDelegate delegate;

    public MyAsyncTask(AsyncDelegate delegate) {
        //Dependency Injection : this delegate will be called when the task finishes
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String... params) {

        JSONArray json = JSONfunctions.getJSONfromURL(params[0]);

        SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf1 = new SimpleDateFormat("dd.MM.yyyy");

        for (int i = 0; i < json.length(); i++)
            try {
                JSONObject e = json.getJSONObject(i);

                HashMap<String, String> map = (HashMap<String, String>) JsonHelper.toMap(e);

                map.put("job_name", "job" + new Integer(i + 1).toString());

                //date format: 2016-04-10
                String job_date = map.get("job_date").substring(0, 10);
                Date nDate = tf.parse(job_date);
                map.put("job_date", tf1.format(nDate));

                map.put("distance", map.get("distance") + " km");

                RecurEnum re = RecurEnum.Monthly;


                if (map.get("recurrency") != null) {
                    map.put("recurrency", RecurEnum.fromId(Integer.parseInt(map.get("recurrency"))).name());
                }

                mylist.add(map);

            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            } catch (ParseException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
        return mylist;
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {

        delegate.asyncComplete(result);
    }
}