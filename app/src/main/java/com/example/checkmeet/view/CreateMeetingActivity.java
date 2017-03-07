package com.example.checkmeet.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkmeet.R;
import com.thebluealliance.spectrum.SpectrumPalette;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateMeetingActivity extends AppCompatActivity implements SpectrumPalette.OnColorSelectedListener,
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {


    public static String TAG = "CreateMeeting";
    public static String MEETING_COLOR = "MEETING_COLOR";
    public static int ADD_GUESTS = 0;

    private RelativeLayout activityCreateMeeting;
    private TextView tvPickColor;
    private TextView tvMeetingName;
    private EditText etMeetingName;
    private TextView tvMeetingDescription;
    private EditText etMeetingDescription;
    private SpectrumPalette palette;
    private TextView tvPickDateTime;
    private ImageButton btnOpenCalendar;
    private TextView tvDate;
    private TextView tvTimeFrom;
    private ImageButton btnOpenFromTime;
    private TextView tvTimeTo;
    private ImageButton btnOpenToTime;
    private LinearLayout llDate;
    private LinearLayout llFromDate;
    private TextView tvTimefrom;
    private LinearLayout llToDate;
    private TextView tvTimeto;
    private TextView tvPickLocation;
    private TextView tvAddGuests;
    private ImageButton btnAddGuests;
    private ImageButton btnPickLocation;

    private int timeFlag;
    private int timeFirstSet;
    private int fromHour;
    private int fromMinute;
    private int toHour;
    private int toMinute;
    private int meetingColor;


    public static Typeface tf_roboto;
    private android.support.v7.app.ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        initView();
    }

    private void initView() {

        timeFlag = 0;
        timeFirstSet = 0;
        meetingColor = Color.rgb(63, 81, 181);
        activityCreateMeeting = (RelativeLayout) findViewById(R.id.activity_create_meeting);
        tvPickColor = (TextView) findViewById(R.id.tv_pick_color);
        tvMeetingName = (TextView) findViewById(R.id.tv_meeting_name);
        etMeetingName = (EditText) findViewById(R.id.et_meeting_name);
        tvMeetingDescription = (TextView) findViewById(R.id.tv_meeting_description);
        etMeetingDescription = (EditText) findViewById(R.id.et_meeting_description);
        palette = (SpectrumPalette) findViewById(R.id.palette);
        tvPickDateTime = (TextView) findViewById(R.id.tv_pick_date_time);
        btnOpenCalendar = (ImageButton) findViewById(R.id.btn_open_calendar);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTimeFrom = (TextView) findViewById(R.id.tv_time_from);
        btnOpenFromTime = (ImageButton) findViewById(R.id.btn_open_from_time);
        tvTimeTo = (TextView) findViewById(R.id.tv_time_to);
        btnOpenToTime = (ImageButton) findViewById(R.id.btn_open_to_time);
        llDate = (LinearLayout) findViewById(R.id.ll_date);
        llFromDate = (LinearLayout) findViewById(R.id.ll_from_date);
        tvTimefrom = (TextView) findViewById(R.id.tv_timefrom);
        llToDate = (LinearLayout) findViewById(R.id.ll_to_date);
        tvTimeto = (TextView) findViewById(R.id.tv_timeto);
        tvPickLocation = (TextView) findViewById(R.id.tv_pick_location);
        tvAddGuests = (TextView) findViewById(R.id.tv_add_guests);
        btnAddGuests = (ImageButton) findViewById(R.id.btn_add_guests);
        btnPickLocation = (ImageButton) findViewById(R.id.btn_pick_location);
        actionBar = getSupportActionBar();

        Calendar dateToday = Calendar.getInstance();
        tvDate.setText(dateToday.get(Calendar.MONTH) + 1 + "/" +
                dateToday.get(Calendar.DAY_OF_MONTH) + "/" +
                dateToday.get(Calendar.YEAR));
        tvTimefrom.setText(convertTimeToString(dateToday.get(Calendar.HOUR_OF_DAY), dateToday.get(Calendar.MINUTE)));
        tvTimeto.setText(convertTimeToString(dateToday.get(Calendar.HOUR_OF_DAY) + 1, dateToday.get(Calendar.MINUTE)));
        fromHour = dateToday.get(Calendar.HOUR_OF_DAY);
        toHour = dateToday.get(Calendar.HOUR_OF_DAY) + 1;
        fromMinute = dateToday.get(Calendar.MINUTE);
        toMinute = dateToday.get(Calendar.MINUTE) + 1;

        tf_roboto = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        tvPickColor.setTypeface(tf_roboto);
        tvMeetingDescription.setTypeface(tf_roboto);
        tvMeetingName.setTypeface(tf_roboto);
        tvPickDateTime.setTypeface(tf_roboto);
        tvTimeFrom.setTypeface(tf_roboto);
        tvTimeTo.setTypeface(tf_roboto);
        tvDate.setTypeface(tf_roboto);
        tvTimefrom.setTypeface(tf_roboto);
        tvTimeto.setTypeface(tf_roboto);
        tvPickLocation.setTypeface(tf_roboto);
        tvAddGuests.setTypeface(tf_roboto);

        palette.setOnColorSelectedListener(this);
        btnOpenCalendar.setOnClickListener(this);
        btnOpenFromTime.setOnClickListener(this);
        btnOpenToTime.setOnClickListener(this);
        btnAddGuests.setOnClickListener(this);

        actionBar.setDisplayHomeAsUpEnabled(true);

        String name = "Create Meeting"; // your string here
        SpannableString s = new SpannableString(name);
        s.setSpan(new TypefaceSpan("fonts/rancho_regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.getSupportActionBar().setTitle(s);
    }

    public String convertTimeToString(int hourOfDay, int minute) {
        String partOfDay;
        String strTime;
        if (hourOfDay - 12 <= 0)
            partOfDay = "AM";
        else
            partOfDay = "PM";

        if (hourOfDay % 12 == 0)
            hourOfDay = 12;
        else
            hourOfDay = hourOfDay % 12;

        String hr = String.valueOf(hourOfDay);
        String min = String.valueOf(minute);

        if (hourOfDay < 10)
            hr = "0" + hr;
        if (minute < 10)
            min = "0" + min;

        strTime = hr + ":" + min + "  " + partOfDay;
        return strTime;
    }

    @Override
    public void onColorSelected(@ColorInt int color) {

        Log.d(TAG, "Meeting Color changed to " + String.format("#%06X", (0xFFFFFF & color)));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));

        meetingColor = color;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "CLICKED");
        if (view.getId() == btnOpenCalendar.getId()) {
            Log.d(TAG, "DATE PICKER BUTTON CLICKED");
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAccentColor(meetingColor);
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.setCancelColor(Color.DKGRAY);
            dpd.setOkColor(Color.DKGRAY);
            dpd.show(getFragmentManager(), "Datepickerdialog");
        } else if (view.getId() == btnOpenToTime.getId()) {
            timeFlag = 1;
            showTimePicker(1);
        } else if (view.getId() == btnOpenFromTime.getId()) {
            timeFlag = 0;
            showTimePicker(0);
        } else if (view.getId() == btnAddGuests.getId()) {
            Intent i = new Intent(this, AddGuestsActivity.class);
            i.putExtra(MEETING_COLOR, meetingColor);
            startActivityForResult(i, ADD_GUESTS);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        tvDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        if (timeFlag == 0) {
            tvTimefrom.setText(convertTimeToString(hourOfDay, minute));
            if (timeFirstSet == 0)
                tvTimeto.setText(convertTimeToString(hourOfDay + 1, minute));
            Log.d(TAG, "TIME: " + hourOfDay + " : " + minute);
            fromHour = hourOfDay;
            fromMinute = minute;
        } else {
            if (isTimeValid(fromHour, hourOfDay, fromMinute, minute)) {
                tvTimeto.setText(convertTimeToString(hourOfDay, minute));
                toHour = hourOfDay;
                toMinute = minute;
            } else {
                Toast.makeText(getBaseContext(), "Invalid Time", Toast.LENGTH_SHORT).show();
                showTimePicker(1);
            }
        }
    }

    public boolean isTimeValid(int fromHourOfDay, int toHourOfDay, int fromMinute, int toMinute) {
        if (fromHourOfDay > toHourOfDay)
            return false;
        else if (fromHourOfDay < toHourOfDay) {
            return true;
        } else if (fromHourOfDay == toHourOfDay) {
            if (fromMinute > toMinute)
                return false;
            else
                return true;
        }
        return false;
    }

    public void showTimePicker(int tofromtime) {
        Calendar now = Calendar.getInstance();
        int hour;
        int min;

        if (tofromtime == 0) {
            hour = fromHour;
            min = fromMinute;
        } else {
            hour = toHour;
            min = toMinute;
        }

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                hour,
                min,
                false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.setAccentColor(meetingColor);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "BACK PRESS");
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_GUESTS)
        {
            Log.d(TAG, "From Add Guests");
            ArrayList<String> guests = data.getParcelableExtra("List_of_Names");

            Log.d(TAG, String.valueOf(guests.size()));

        }
    }
}
