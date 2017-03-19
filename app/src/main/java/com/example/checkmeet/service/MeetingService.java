package com.example.checkmeet.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.checkmeet.db.DatabaseHelper;
import com.example.checkmeet.model.Date;
import com.example.checkmeet.model.Meeting;
import com.example.checkmeet.model.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victo on 3/18/2017.
 */

public class MeetingService {

    public static long createMeeting(Context context, Meeting meeting) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        ///// meeting table /////
        ContentValues contentValues = new ContentValues();
        contentValues.put(Meeting.COL_TITLE, meeting.getTitle());
        contentValues.put(Meeting.COL_DATE, meeting.getDate().toMilliseconds());
        contentValues.put(Meeting.COL_TIMESTART, meeting.getStartTime());
        contentValues.put(Meeting.COL_TIMEEND, meeting.getEndTime());
        contentValues.put(Meeting.COL_ADDRESS, meeting.getAddress());
        contentValues.put(Meeting.COL_LATITUDE, meeting.getLatitude());
        contentValues.put(Meeting.COL_LONGITUDE, meeting.getLongitude());
        contentValues.put(Meeting.COL_HOST_NAME, meeting.getHostName());
        contentValues.put(Meeting.COL_HOST_NUMBER, meeting.getHostNumber());
        contentValues.put(Meeting.COL_COLOR, meeting.getColor());

        if(meeting.getDescription() != null) {
            contentValues.put(Meeting.COL_DESCRIPTION, meeting.getDescription());
        }

        ///// for NOT HOST
        if(meeting.getStringParticipants() != null) {
            contentValues.put(Meeting.COL_PARTICIPANTS_STRING, meeting.getStringParticipants());
        }

        long result = db.insert(Meeting.TABLE_NAME, null, contentValues);

        ///// for HOST
        if(meeting.getStringParticipants() == null) {
            ///// meeting_participants table /////
            List<Participant> participantList = meeting.getParticipantList();
            for (int i = 0; i < participantList.size(); i ++) {
                contentValues = new ContentValues();
                contentValues.put(Meeting.COL_MEETINGID, result);
                contentValues.put(Participant.COL_PARTICIPANTID,
                        participantList.get(i).getParticipant_id());

                db.insert(Meeting.TABLE_NAME_MEETING_PARTICIPANTS, null, contentValues);
            }
        }

        db.close();
        return result;
    }

    /**
     * This will only be called if user changes something about the details of the meeting
     * EXCLUDING the guest list
     */
    public static int updateMeeting(Context context, Meeting meeting) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        String selection = Meeting.COL_MEETINGID + " = ?";
        String[] selectionArgs = {meeting.getMeeting_id() + ""};

        ///// meeting table /////
        // update all attributes
        ContentValues contentValues = new ContentValues();
        contentValues.put(Meeting.COL_TITLE, meeting.getTitle());
        contentValues.put(Meeting.COL_DATE, meeting.getDate().toMilliseconds());
        contentValues.put(Meeting.COL_TIMESTART, meeting.getStartTime());
        contentValues.put(Meeting.COL_TIMEEND, meeting.getEndTime());
        contentValues.put(Meeting.COL_ADDRESS, meeting.getAddress());
        contentValues.put(Meeting.COL_LATITUDE, meeting.getLatitude());
        contentValues.put(Meeting.COL_LONGITUDE, meeting.getLongitude());
        contentValues.put(Meeting.COL_HOST_NAME, meeting.getHostName());
        contentValues.put(Meeting.COL_HOST_NUMBER, meeting.getHostNumber());
        contentValues.put(Meeting.COL_COLOR, meeting.getColor());

        if(meeting.getDescription() != null) {
            contentValues.put(Meeting.COL_DESCRIPTION, meeting.getDescription());
        }

        int result = db.update(Meeting.TABLE_NAME, contentValues, selection, selectionArgs);

        db.close();

        return result;
    }

    /**
     * This will be called only if user edits guests to the meeting
     */
    public static void updateMeetingParticipants(Context context,
                                                List<Participant> participantList,
                                                long meeting_id) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        // remove all participants in meeting
        String selection = Meeting.COL_MEETINGID + " = ?";
        String[] selectionArgs = {meeting_id + ""};

        db.delete(Meeting.TABLE_NAME_MEETING_PARTICIPANTS, selection, selectionArgs);

        // add new participants
        ContentValues contentValues;
        for(int i = 0; i < participantList.size(); i ++) {
            contentValues = new ContentValues();
            contentValues.put(Meeting.COL_MEETINGID, meeting_id);
            contentValues.put(
                    Participant.COL_PARTICIPANTID, participantList.get(0).getParticipant_id());

            db.insert(Meeting.TABLE_NAME_MEETING_PARTICIPANTS, null, contentValues);
        }

        db.close();

    }

    public static int deleteMeeting(Context context, int id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        String selection = Meeting.COL_MEETINGID + " = ?";
        String[] selectionArgs = {id + ""};

        ///// meeting table /////
        int result = db.delete(Meeting.TABLE_NAME, selection, selectionArgs);

        ///// meeting_participants table /////
        db.delete(Meeting.TABLE_NAME_MEETING_PARTICIPANTS, selection, selectionArgs);

        db.close();

        return result;
    }

    public static Cursor getAllMeetings(Context context) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        // sort by date and time
        String orderBy = Meeting.COL_DATE + ", " + Meeting.COL_TIMESTART;

        return db.query(Meeting.TABLE_NAME, null, null, null, null, null, orderBy);
    }

    public static Meeting getMeeting(Context context, int id) {
        Meeting meeting = null;
        boolean isHost = true;

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        String selection = Meeting.TABLE_NAME + "." + Meeting.COL_MEETINGID + " = ?";
        String[] selectionArgs = {id + ""};


        ///// meeting table /////
        Cursor cursor = db.query(
                Meeting.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()) {

            // meeting
            meeting = new Meeting();

            meeting.setMeeting_id(cursor.getInt(cursor.getColumnIndex(Meeting.COL_MEETINGID)));
            meeting.setTitle(cursor.getString(cursor.getColumnIndex(Meeting.COL_TITLE)));
            meeting.setDate(new Date(cursor.getLong(cursor.getColumnIndex(Meeting.COL_DATE))));
            meeting.setStartTime(cursor.getInt(cursor.getColumnIndex(Meeting.COL_TIMESTART)));
            meeting.setEndTime(cursor.getInt(cursor.getColumnIndex(Meeting.COL_TIMEEND)));
            meeting.setHostName(cursor.getString(cursor.getColumnIndex(Meeting.COL_HOST_NAME)));
            meeting.setHostNumber(cursor.getString(cursor.getColumnIndex(Meeting.COL_HOST_NUMBER)));
            meeting.setAddress(cursor.getString(cursor.getColumnIndex(Meeting.COL_ADDRESS)));
            meeting.setLatitude(cursor.getDouble(cursor.getColumnIndex(Meeting.COL_LATITUDE)));
            meeting.setLongitude(cursor.getDouble(cursor.getColumnIndex(Meeting.COL_LONGITUDE)));
            meeting.setColor(cursor.getInt(cursor.getColumnIndex(Meeting.COL_COLOR)));

            if(cursor.getString(cursor.getColumnIndex(Meeting.COL_DESCRIPTION)) != null) {
                meeting.setDescription(cursor.getString(
                        cursor.getColumnIndex(Meeting.COL_DESCRIPTION)));
            }

            if(cursor.getString(cursor.getColumnIndex(Meeting.COL_PARTICIPANTS_STRING)) != null) {
                meeting.setStringParticipants(
                        cursor.getString(cursor.getColumnIndex(Meeting.COL_PARTICIPANTS_STRING)));
                isHost = false;
            }
        }

        cursor.close();

        // get participants
        if(meeting != null && isHost) {

            if(meeting.getStringParticipants() == null) {
                String[] projection = {
                        Participant.COL_PARTICIPANTID
                };

                selection =
                        Meeting.TABLE_NAME + "." + Meeting.COL_MEETINGID + " = ? AND " +
                                Meeting.TABLE_NAME + "." + Meeting.COL_MEETINGID + " = " +
                                Meeting.TABLE_NAME_MEETING_PARTICIPANTS + "." + Meeting.COL_MEETINGID;

                selectionArgs = new String[]{id + ""};

                cursor = db.query(Meeting.TABLE_NAME_MEETING_PARTICIPANTS,
                        projection, selection, selectionArgs, null, null, null);

                List<Participant> participantList = new ArrayList<>();
                Participant p;
                while(cursor.moveToNext()) {
                    p = new Participant();
                    p.setParticipant_id(
                            cursor.getInt(cursor.getColumnIndex(Participant.COL_PARTICIPANTID)));

                    participantList.add(p);
                }

                meeting.setParticipantList(participantList);

                cursor.close();
            }

        }

        db.close();

        return meeting;
    }

}
