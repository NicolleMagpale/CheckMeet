package com.example.checkmeet.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.thebluealliance.spectrum.SpectrumPalette;

public class EditMeetingActivity extends AppCompatActivity {

    private EditText etMeetingName;
    private EditText etMeetingDescription;
    private SpectrumPalette palette;
    private ImageButton btnOpenCalendar;
    private ImageButton btnOpenFromTime;
    private ImageButton btnOpenToTime;
    private ImageButton btnPickLocation;
    private ImageButton btnAddGuests;
    private RecyclerView rvAddedGuests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);
        initView();
    }

    private void initView() {
        etMeetingName = (EditText) findViewById(R.id.et_meeting_name);
        etMeetingDescription = (EditText) findViewById(R.id.et_meeting_description);
        palette = (SpectrumPalette) findViewById(R.id.palette);
        btnOpenCalendar = (ImageButton) findViewById(R.id.btn_open_calendar);
        btnOpenFromTime = (ImageButton) findViewById(R.id.btn_open_from_time);
        btnOpenToTime = (ImageButton) findViewById(R.id.btn_open_to_time);
        btnPickLocation = (ImageButton) findViewById(R.id.btn_pick_location);
        btnAddGuests = (ImageButton) findViewById(R.id.btn_add_guests);
        rvAddedGuests = (RecyclerView) findViewById(R.id.rv_added_guests);



    }
}
