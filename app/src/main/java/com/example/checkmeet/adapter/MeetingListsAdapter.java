package com.example.checkmeet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkmeet.R;
import com.example.checkmeet.model.Meeting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victo on 2/22/2017.
 */

public class MeetingListsAdapter extends RecyclerView.Adapter<MeetingListsAdapter.MeetingHolder> {

    private List<Meeting> meetingList;
    private LayoutInflater inflater;

    private MeetingItemClickCallback meetingItemClickCallback;

    public MeetingListsAdapter(List<Meeting> meetingList, Context context) {
        this.meetingList = meetingList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MeetingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.meeting_item, parent, false);
        return new MeetingHolder(v);
    }

    @Override
    public void onBindViewHolder(MeetingHolder holder, int position) {
        holder.tv_title_meeting.setText(meetingList.get(position).getTitle());
        holder.tv_month.setText(meetingList.get(position).getMonth());
        holder.background_date.setBackgroundColor(meetingList.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public void setMeetingItemClickCallback(
            final MeetingItemClickCallback meetingItemClickCallback) {
        this.meetingItemClickCallback = meetingItemClickCallback;
    }

    public void setItems(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    class MeetingHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, PopupMenu.OnMenuItemClickListener{

        TextView tv_title_meeting;
        TextView tv_month;
        ImageView iv_options;
        View background_date;
        View container;

        MeetingHolder(View itemView) {
            super(itemView);

            tv_title_meeting = (TextView) itemView.findViewById(R.id.tv_title_meeting);
            tv_month = (TextView) itemView.findViewById(R.id.tv_month);
            background_date = itemView.findViewById(R.id.background_date_meeting);
            iv_options = (ImageView) itemView.findViewById(R.id.iv_options_menu_meeting);
            container = itemView.findViewById(R.id.container_meeting_item);

            container.setOnClickListener(this);
            iv_options.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == container.getId()) {
                meetingItemClickCallback.onItemClick(getAdapterPosition());
            } else if(v.getId() == iv_options.getId()) {

                // inflate popup menu
                PopupMenu popupMenu = new PopupMenu(container.getContext(), iv_options);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu_temp, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(this);

                MenuPopupHelper menuPopupHelper = new MenuPopupHelper(
                        container.getContext(),
                        (MenuBuilder) popupMenu.getMenu(),
                        iv_options
                );

                menuPopupHelper.setForceShowIcon(true);
                menuPopupHelper.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            meetingItemClickCallback.onOptionsClick(getAdapterPosition(), item.getItemId());
            return true;
        }
    }
}
