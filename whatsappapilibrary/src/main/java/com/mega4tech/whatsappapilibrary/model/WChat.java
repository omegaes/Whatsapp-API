package com.mega4tech.whatsappapilibrary.model;

import android.database.Cursor;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajaybhatt on 03/01/18.
 */

public class WChat {

    private int _id;

    private String keyRemoteJID;

    private int messageTableId;

    private String subject;

    private int creation;

    private int lastReadMessageTableId;

    private int lastReadReceiptSentMessageTableId;

    private int archived;

    private long sortTimestamp;

    private int modTag;

    private int myMessages;

    private int plaintextDisabled;

    private int lastMessageTableId;

    private int unseenMissedCallCount;

    private int unseenRowCount;

    private int vcardUiDismissed;

    private int changeNumberNotifiedMessageId;

    public WChat(Cursor cursor) {
        _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        keyRemoteJID = cursor.getString(cursor.getColumnIndexOrThrow("key_remote_jid"));
        messageTableId = cursor.getInt(cursor.getColumnIndexOrThrow("message_table_id"));
        subject = cursor.getString(cursor.getColumnIndexOrThrow("subject"));
        lastReadMessageTableId = cursor.getInt(cursor.getColumnIndexOrThrow("last_read_message_table_id"));
        lastReadReceiptSentMessageTableId = cursor.getInt(cursor.getColumnIndexOrThrow("last_read_receipt_sent_message_table_id"));
        archived = cursor.getInt(cursor.getColumnIndexOrThrow("archived"));
        sortTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow("sort_timestamp"));
        modTag = cursor.getInt(cursor.getColumnIndexOrThrow("mod_tag"));
        myMessages = cursor.getInt(cursor.getColumnIndexOrThrow("my_messages"));
        plaintextDisabled = cursor.getInt(cursor.getColumnIndexOrThrow("plaintext_disabled"));
        lastMessageTableId = cursor.getInt(cursor.getColumnIndexOrThrow("last_message_table_id"));
        unseenMissedCallCount = cursor.getInt(cursor.getColumnIndexOrThrow("unseen_missed_calls_count"));
        unseenRowCount = cursor.getInt(cursor.getColumnIndexOrThrow("unseen_row_count"));
        vcardUiDismissed = cursor.getInt(cursor.getColumnIndexOrThrow("vcard_ui_dismissed"));
        changeNumberNotifiedMessageId = cursor.getInt(cursor.getColumnIndexOrThrow("change_number_notified_message_id"));
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getKeyRemoteJID() {
        return keyRemoteJID;
    }

    public void setKeyRemoteJID(String keyRemoteJID) {
        this.keyRemoteJID = keyRemoteJID;
    }

    public int getMessageTableId() {
        return messageTableId;
    }

    public void setMessageTableId(int messageTableId) {
        this.messageTableId = messageTableId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCreation() {
        return creation;
    }

    public void setCreation(int creation) {
        this.creation = creation;
    }

    public int getLastReadMessageTableId() {
        return lastReadMessageTableId;
    }

    public void setLastReadMessageTableId(int lastReadMessageTableId) {
        this.lastReadMessageTableId = lastReadMessageTableId;
    }

    public int getLastReadReceiptSentMessageTableId() {
        return lastReadReceiptSentMessageTableId;
    }

    public void setLastReadReceiptSentMessageTableId(int lastReadReceiptSentMessageTableId) {
        this.lastReadReceiptSentMessageTableId = lastReadReceiptSentMessageTableId;
    }

    public int getArchived() {
        return archived;
    }

    public void setArchived(int archived) {
        this.archived = archived;
    }

    public long getSortTimestamp() {
        return sortTimestamp;
    }

    public void setSortTimestamp(long sortTimestamp) {
        this.sortTimestamp = sortTimestamp;
    }

    public int getModTag() {
        return modTag;
    }

    public void setModTag(int modTag) {
        this.modTag = modTag;
    }

    public int getMyMessages() {
        return myMessages;
    }

    public void setMyMessages(int myMessages) {
        this.myMessages = myMessages;
    }

    public int getPlaintextDisabled() {
        return plaintextDisabled;
    }

    public void setPlaintextDisabled(int plaintextDisabled) {
        this.plaintextDisabled = plaintextDisabled;
    }

    public int getLastMessageTableId() {
        return lastMessageTableId;
    }

    public void setLastMessageTableId(int lastMessageTableId) {
        this.lastMessageTableId = lastMessageTableId;
    }

    public int getUnseenMissedCallCount() {
        return unseenMissedCallCount;
    }

    public void setUnseenMissedCallCount(int unseenMissedCallCount) {
        this.unseenMissedCallCount = unseenMissedCallCount;
    }

    public int getUnseenRowCount() {
        return unseenRowCount;
    }

    public void setUnseenRowCount(int unseenRowCount) {
        this.unseenRowCount = unseenRowCount;
    }

    public int getVcardUiDismissed() {
        return vcardUiDismissed;
    }

    public void setVcardUiDismissed(int vcardUiDismissed) {
        this.vcardUiDismissed = vcardUiDismissed;
    }

    public int getChangeNumberNotifiedMessageId() {
        return changeNumberNotifiedMessageId;
    }

    public void setChangeNumberNotifiedMessageId(int changeNumberNotifiedMessageId) {
        this.changeNumberNotifiedMessageId = changeNumberNotifiedMessageId;
    }

    public Map<String, Object> getMapObj() {
        Map<String, Object> map = new HashMap<>();
        return (Map<String, Object>) new Gson().fromJson(new Gson().toJson(this), map.getClass());
    }
}
