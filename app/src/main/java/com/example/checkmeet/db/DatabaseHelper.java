package com.example.checkmeet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.checkmeet.model.Group;
import com.example.checkmeet.model.Meeting;
import com.example.checkmeet.model.Notes;
import com.example.checkmeet.model.Participant;

/**
 * Created by victo on 3/18/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static DatabaseHelper instance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "checkmeet.db";

    ///// SQL CREATE TABLES /////

    // meeting
    private static final String SQL_CREATE_MEETING_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Meeting.TABLE_NAME + " ( " +
                    Meeting.COL_MEETINGID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Meeting.COL_TITLE + " TEXT NOT NULL, " +
                    Meeting.COL_DESCRIPTION + " TEXT, " +
                    Meeting.COL_DATE + " REAL NOT NULL, " +
                    Meeting.COL_TIMESTART + " INTEGER NOT NULL, " +
                    Meeting.COL_TIMEEND + " INTEGER NOT NULL, " +
                    Meeting.COL_HOST_NAME + " TEXT NOT NULL, " +
                    Meeting.COL_HOST_NUMBER + " TEXT NOT NULL, " +
                    Meeting.COL_COLOR + " INTEGER NOT NULL, " +
                    Meeting.COL_ADDRESS + " TEXT NOT NULL, " +
                    Meeting.COL_LATITUDE + " REAL NOT NULL, " +
                    Meeting.COL_LONGITUDE + " REAL NOT NULL, " +
                    Meeting.COL_PARTICIPANTS_STRING + " TEXT);";

    // notes
    private static final String SQL_CREATE_NOTES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Notes.TABLE_NAME + " ( " +
                    Notes.COL_ID + " INTEGER PRIMARY KEY, " +
                    Group.COL_NAME + " TEXT NOT NULL);";

    // participant
    private static final String SQL_CREATE_PARTICIPANT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Participant.TABLE_NAME + " ( " +
                    Participant.COL_PARTICIPANTID + " INTEGER PRIMARY);";

    // group
    private static final String SQL_CREATE_GROUP_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Group.TABLE_NAME + " ( " +
                    Group.COL_GROUPID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Group.COL_NAME + " TEXT NOT NULL);";

    // meeting_participant
    private static final String SQL_CREATE_MEETING_PARTICIPANT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Meeting.TABLE_NAME_MEETING_PARTICIPANTS + " ( " +
                    Meeting.COL_MEETINGID + " INTEGER NOT NULL, " +
                    Participant.COL_PARTICIPANTID + " INTEGER NOT NULL, " +
                    "PRIMARY KEY (" + Meeting.COL_MEETINGID + ", " +
                    Participant.COL_PARTICIPANTID + "));";

    // group_participant
    private static final String SQL_CREATE_GROUP_PARTICIPANT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Group.TABLE_NAME_GROUP_PARTICIPANT + " ( " +
                    Group.COL_GROUPID + " INTEGER NOT NULL, " +
                    Participant.COL_PARTICIPANTID + " INTEGER NOT NULL, " +
                    "PRIMARY KEY (" + Group.COL_GROUPID + ", " +
                    Participant.COL_PARTICIPANTID + "));";

    ///// SQL DELETE TABLES /////
    private static final String SQL_DELETE_MEETING_TABLE =
            "DROP TABLE IF EXISTS " + Meeting.TABLE_NAME;

    private static final String SQL_DELETE_NOTES_TABLE =
            "DROP TABLE IF EXISTS " + Notes.TABLE_NAME;

    private static final String SQL_DELETE_PARTICIPANT_TABLE =
            "DROP TABLE IF EXISTS " + Participant.TABLE_NAME;

    private static final String SQL_DELETE_GROUP_TABLE =
            "DROP TABLE IF EXISTS " + Group.TABLE_NAME;

    private static final String SQL_DELETE_MEETING_PARTICIPANT_TABLE =
            "DROP TABLE IF EXISTS " + Meeting.TABLE_NAME_MEETING_PARTICIPANTS;

    private static final String SQL_DELETE_GROUP_PARTICIPANT_TABLE =
            "DROP TABLE IF EXISTS " + Group.TABLE_NAME_GROUP_PARTICIPANT;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseHelper(context);
        }

        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MEETING_TABLE);
        db.execSQL(SQL_CREATE_NOTES_TABLE);
        db.execSQL(SQL_CREATE_PARTICIPANT_TABLE);
        db.execSQL(SQL_CREATE_GROUP_TABLE);
        db.execSQL(SQL_CREATE_MEETING_PARTICIPANT_TABLE);
        db.execSQL(SQL_CREATE_GROUP_PARTICIPANT_TABLE);

        initData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MEETING_TABLE);
        db.execSQL(SQL_DELETE_NOTES_TABLE);
        db.execSQL(SQL_DELETE_PARTICIPANT_TABLE);
        db.execSQL(SQL_DELETE_GROUP_TABLE);
        db.execSQL(SQL_DELETE_MEETING_PARTICIPANT_TABLE);
        db.execSQL(SQL_DELETE_GROUP_PARTICIPANT_TABLE);
        onCreate(db);
    }

    private void initData(SQLiteDatabase db) {

    }
}
