package com.mega4tech.whatsappapilibrary.model;

import android.database.Cursor;

/**
 * Created by ajaybhatt on 25/11/17.
 */

public class WIdentity {

    private int _id;

    private int recipientId;

    private int registrationId;

    private byte[] publicKey;

    private byte[] privateKey;

    private int nextPrekeyId;

    private long timestamp;


    public WIdentity(Cursor cursor) {
        _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        recipientId = cursor.getInt(cursor.getColumnIndexOrThrow("recipient_id"));
        registrationId = cursor.getInt(cursor.getColumnIndexOrThrow("registration_id"));
        publicKey = cursor.getBlob(cursor.getColumnIndexOrThrow("public_key"));
        privateKey = cursor.getBlob(cursor.getColumnIndexOrThrow("private_key"));
        nextPrekeyId = cursor.getInt(cursor.getColumnIndexOrThrow("next_prekey_id"));
        timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"));
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }

    public int getNextPrekeyId() {
        return nextPrekeyId;
    }

    public void setNextPrekeyId(int nextPrekeyId) {
        this.nextPrekeyId = nextPrekeyId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
