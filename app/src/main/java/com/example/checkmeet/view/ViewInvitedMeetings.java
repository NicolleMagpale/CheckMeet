package com.example.checkmeet.view;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkmeet.R;
import com.example.checkmeet.adapter.MeetingListsAdapter;
import com.example.checkmeet.model.Meeting;
import com.example.checkmeet.model.Month;

import java.util.ArrayList;
import java.util.List;


public class ViewInvitedMeetings extends ViewMeetingsBaseFragment {


    public ViewInvitedMeetings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_invited_meetings, container, false);

        initData();

        // set up views
        recView = (RecyclerView) rootView.findViewById(R.id.rv_invited_meetings);
        swipeRefreshLayout =
                (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout_invited);
        tv_no_meetings_found = (TextView) rootView.findViewById(R.id.tv_no_meetings_found);


        adapter = new MeetingListsAdapter(meetingList, getContext());
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        recView.setAdapter(adapter);
        adapter.setMeetingItemClickCallback(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }

            void refreshItems() {
                // load the whole set of data
                refreshData();

                // Load complete
                onItemsLoadComplete();
            }

            void onItemsLoadComplete() {
                // Update the adapter and notify data set changed
                adapter.setItems(meetingList);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    private void initData() {
        this.meetingList = new ArrayList<>();

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FFBBFF"));
        colors.add(Color.parseColor("#EEBBEE"));
        colors.add(Color.parseColor("#DFB0FF"));
        colors.add(Color.parseColor("#DBBFF7"));
        colors.add(Color.parseColor("#CBC5F5"));
        colors.add(Color.parseColor("#BAD0EF"));
        colors.add(Color.parseColor("#A5DBEB"));
        colors.add(Color.parseColor("#B5FFC8"));
        colors.add(Color.parseColor("#B3FF99"));
        colors.add(Color.parseColor("#DFFFCA"));
        colors.add(Color.parseColor("#FFFFC8"));
        colors.add(Color.parseColor("#F7F9D0"));

        Meeting m;

        for(int i = 0; i < 12; i ++) {
            m = new Meeting();
            m.setTitle("Meeting " + (i+1));
            m.setMonth(Month.values()[i] + "");
            m.setColor(colors.get(i));

            meetingList.add(m);
        }
    }

    public void refreshData() {
        // get data from server again

        // temporarily initialize data again
        initData();
    }

    @Override
    public void onItemClick(int p) {
        Intent intent = new Intent(getActivity(), ViewMeetingActivity.class);
        intent.putExtra(ViewMeetingsActivity.EXTRA_MEETING_TITLE, meetingList.get(p).getTitle());
        intent.putExtra(ViewMeetingsActivity.EXTRA_MEETING_COLOR, meetingList.get(p).getColor());
        startActivity(intent);
    }

    @Override
    public void onOptionsClick(int p, int id_item) {

        switch(id_item) {
            case R.id.popup_edit:
                Toast.makeText(getActivity(), "EDIT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.popup_delete:
                Toast.makeText(getActivity(), "DELETE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.popup_open_notes:
                Toast.makeText(getActivity(), "OPEN NOTES", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
