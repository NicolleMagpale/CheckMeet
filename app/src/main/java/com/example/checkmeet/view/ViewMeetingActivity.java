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
import com.example.checkmeet.model.Meeting;
import com.example.checkmeet.service.MeetingService;

public class ViewMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewMeetingActivity";

    private ImageView iv_open_view_map;

    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";

    private Meeting meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);

        int meeting_id = getIntent().getIntExtra(Meeting.COL_MEETINGID, -1);
        meeting = MeetingService.getMeeting(getBaseContext(), meeting_id);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(meeting.getTitle());
        }

        ((TextView) findViewById(R.id.tv_member_list)).setText(
                "Hazel Anne Brosas\nMavic Reccion\nNicolle Magpale\nCourtney Ngo"
        );

        iv_open_view_map = (ImageView) findViewById(R.id.iv_open_view_map);
        iv_open_view_map.setOnClickListener(this);
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
                Intent intent = new Intent(this, OpenNotesActivity.class);
                intent.putExtra(Meeting.COL_MEETINGID, meeting.getMeeting_id());
                intent.putExtra(Meeting.COL_TITLE, meeting.getTitle());
                startActivity(intent);
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

            Intent intent = new Intent(ViewMeetingActivity.this,
                    ViewLocationActivity.class);

            intent.putExtra(EXTRA_LATITUDE, meeting.getLatitude());
            intent.putExtra(EXTRA_LONGITUDE, meeting.getLongitude());
            intent.putExtra(EXTRA_ADDRESS, meeting.getAddress());

            startActivity(intent);
        }
    }
}
