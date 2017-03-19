package com.example.checkmeet.model;

import java.util.List;

/**
 * Created by victo on 3/18/2017.
 */

public class Group {

    // db attributes
    public static final String TABLE_NAME = "group";
    public static final String TABLE_NAME_GROUP_PARTICIPANT = "group_participant";

    public static final String COL_GROUPID = "group_id";
    public static final String COL_NAME = "name";

    private int group_id;
    private String name;
    private List<Participant> memberList;

    public Group() {}

    public Group(String name, List<Participant> memberList) {
        this.name = name;
        this.memberList = memberList;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Participant> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Participant> memberList) {
        this.memberList = memberList;
    }
}
