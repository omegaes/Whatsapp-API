package com.mega4tech.whatsappapilibrary.model;

import android.database.Cursor;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajaybhatt on 04/01/18.
 */

public class WGroupParticipant {

    private int _id;

    private String gjid;

    private String jid;

    private int admin;

    private int pending;

    private int sentSenderKey;

    public WGroupParticipant(Cursor cursor) {
        _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        gjid = cursor.getString(cursor.getColumnIndexOrThrow("gjid"));
        jid = cursor.getString(cursor.getColumnIndexOrThrow("jid"));
        admin = cursor.getInt(cursor.getColumnIndexOrThrow("admin"));
        pending = cursor.getInt(cursor.getColumnIndexOrThrow("pending"));
        sentSenderKey = cursor.getInt(cursor.getColumnIndexOrThrow("sent_sender_key"));
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getGjid() {
        return gjid;
    }

    public void setGjid(String gjid) {
        this.gjid = gjid;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getSentSenderKey() {
        return sentSenderKey;
    }

    public void setSentSenderKey(int sentSenderKey) {
        this.sentSenderKey = sentSenderKey;
    }

    public Map<String, Object> getMapObj() {
        Map<String, Object> map = new HashMap<>();
        return (Map<String, Object>) new Gson().fromJson(new Gson().toJson(this), map.getClass());
    }

    public String getKey() {
        StringBuilder stringBuilder = new StringBuilder();
        if (gjid!=null) {
            stringBuilder.append(gjid);
        }
        if (jid!=null) {
            stringBuilder.append(jid);
        }
        return stringBuilder.toString();
    }
}
