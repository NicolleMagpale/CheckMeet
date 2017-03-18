package com.example.checkmeet.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkmeet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewMeetingActivity";

    private ActionBar actionBar;

    private ImageView iv_open_view_map;

    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";

    ////////// TEMPORARY ONLY ///////////////
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);

        String title = getIntent().getStringExtra(ViewMeetingsActivity.EXTRA_MEETING_TITLE);
        int color = getIntent().getIntExtra(ViewMeetingsActivity.EXTRA_MEETING_COLOR, -1);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
//        actionBar.setBackgroundDrawable(new ColorDrawable(color));

        ((TextView) findViewById(R.id.tv_member_list)).setText(
                "Hazel Anne Brosas\nMavic Reccion\nNicolle Magpale\nCourtney Ngo"
        );

        iv_open_view_map = (ImageView) findViewById(R.id.iv_open_view_map);
        iv_open_view_map.setOnClickListener(this);

//        ((ImageView)findViewById(R.id.ic_date)).setColorFilter(color);
//        ((ImageView)findViewById(R.id.ic_description)).setColorFilter(color);
//        ((ImageView)findViewById(R.id.ic_host_name)).setColorFilter(color);
//        ((ImageView)findViewById(R.id.ic_location)).setColorFilter(color);
//        ((ImageView)findViewById(R.id.ic_member_list)).setColorFilter(color);
//        ((ImageView)findViewById(R.id.ic_time)).setColorFilter(color);

        address = getResources().getString(R.string.meeting_location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.popupmenu_temp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id) {
            case R.id.popup_edit:
                Toast.makeText(this, "EDIT", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, EditMeetingActivity.class);
                startActivity(i);
                break;
            case R.id.popup_delete:
                Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.popup_open_notes:
                Toast.makeText(this, "OPEN NOTES", Toast.LENGTH_SHORT).show();
                break;
            default:
                super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == iv_open_view_map.getId()) {
            Toast.makeText(ViewMeetingActivity.this,
                    "Opening map...", Toast.LENGTH_LONG).show();

            // get data from google
            getDataFromGoogle();
        }
    }

    private void getDataFromGoogle() {

        String tempAddress = address.replace(" ", "+");

        String JSON_URL =
                "https://maps.googleapis.com/maps/api/geocode/json?" +
                        "address=" + tempAddress + "&" +
                        "sensor=false";

        Log.e(TAG, "JSON_URL:\n" + JSON_URL);

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "JSON:\n" + response);

                        // create JSONObject
                        JSONObject jsonObject;
                        double lat, lng;

                        try {
                            jsonObject = new JSONObject(response);

                            // get latitude and longitude
                            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lng");

                            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lat");

                            Log.e(TAG, lat + "");
                            Log.e(TAG, lng + "");

                            // go to ViewLocationActivity passing lat lng

                            Intent intent = new Intent(ViewMeetingActivity.this,
                                    ViewLocationActivity.class);

                            intent.putExtra(EXTRA_LATITUDE, lat);
                            intent.putExtra(EXTRA_LONGITUDE, lng);
                            intent.putExtra(EXTRA_ADDRESS, address);

                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // handle error
                            Toast.makeText(ViewMeetingActivity.this,
                                    "Oops! Something went wrong!", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "JSON:\n" + error.getMessage());
                // handle error
                Toast.makeText(ViewMeetingActivity.this,
                        "Oops! Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }
}
