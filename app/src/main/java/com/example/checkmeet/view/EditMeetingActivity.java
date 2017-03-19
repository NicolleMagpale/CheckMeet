package com.example.checkmeet.view;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumPalette;
import com.example.checkmeet.R;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title = getIntent().getStringExtra(ViewMeetingsActivity.EXTRA_MEETING_TITLE);
        int color = getIntent().getIntExtra(ViewMeetingsActivity.EXTRA_MEETING_COLOR, -1);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));

        etMeetingName.setText(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        String name = "DONE"; // your string here
        SpannableString s = new SpannableString(name);
        s.setSpan(new TypefaceSpan("fonts/roboto_regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        final MenuItem menuItem = menu.add(Menu.NONE, 1000, Menu.NONE, s);
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Toast.makeText(getBaseContext(), "BACK PRESSED ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
