package com.example.checkmeet.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkmeet.R;

public class ViewMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    private ImageView iv_open_view_map;

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
            Toast.makeText(this, "Opening map...", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, ChooseLocationActivity.class);
            startActivity(intent);
        }
    }
}
