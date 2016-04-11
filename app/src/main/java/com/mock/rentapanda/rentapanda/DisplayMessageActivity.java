package com.mock.rentapanda.rentapanda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DisplayMessageActivity extends Activity {

    private GoogleMap googleMap = null;
    private MapFragment mapFragment = null;
    public double latitude = 0, longitude = 0;

    LinearLayout content = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        HashMap<String, String> hashmap = (HashMap<String, String>) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);


        content = (LinearLayout) findViewById(R.id.content);

        Iterator it = hashmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.setLayoutParams(layoutParams);

            TextView keyTextView = new TextView(this);
            keyTextView.setTextSize(20);
            keyTextView.setText(pair.getKey().toString());
            keyTextView.setTextColor(Color.BLUE);

            layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 20;
            keyTextView.setLayoutParams(layoutParams);
            ll.addView(keyTextView);

            TextView valueTextView = new TextView(this);
            valueTextView.setTextSize(20);
            valueTextView.setText(pair.getValue().toString());
            valueTextView.setTextColor(Color.RED);
            valueTextView.setLayoutParams(layoutParams);
            ll.addView(valueTextView);

            content.addView(ll);
            //it.remove(); // avoids a ConcurrentModificationException
        }
        if (hashmap.get("job_latitude") != null) {
            latitude = Double.parseDouble(hashmap.get("job_latitude"));
        }

        if (hashmap.get("job_longitude") != null) {
            longitude = Double.parseDouble(hashmap.get("job_longitude"));
        }

        initializeMap();

    }


    private void initializeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            if (googleMap != null) {
                displayMap();
            }
        }
    }

    /**
     * Display The Current Location on Map
     */
    private void displayMap() {
        // Enable MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Create a criteria Object to retrieve provider
        Criteria criteria = new Criteria();

        // set Map Type
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in Google map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("You R Here..."));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_message, menu);
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
}
