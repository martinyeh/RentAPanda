package com.mock.rentapanda.rentapanda;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class MainActivity extends ListActivity implements AsyncDelegate{

    private ListView listView;
    public final static String EXTRA_MESSAGE = "com.mock.rentapanda.rentapanda.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView list_view = (ListView) this.findViewById( android.R.id.list );

        list_view.setClickable(true);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = list_view.getItemAtPosition(position);
                HashMap<String, String> map=(HashMap<String, String>)o;//As you are using Default String Adapter

                sendMessage(map);
            }
        });

        new MyAsyncTask(this).execute("http://private-anon-b803aa249-rentapanda.apiary-mock.com/jobs");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void asyncComplete(ArrayList<HashMap<String, String>>  result){
        SimpleAdapter myAdapter = new SimpleAdapter(this, result, R.layout.simple,
                new String[]{"job_name"},
                new int[]{R.id.text});

        ListView list_view = (ListView) this.findViewById(android.R.id.list);

        list_view.setAdapter(myAdapter); // If Activity extends ListActivity
        list_view.setTextFilterEnabled(true);

    }


    public void sendMessage(HashMap<String, String> map) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, map);
        startActivity(intent);
    }



}


