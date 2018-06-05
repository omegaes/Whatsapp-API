package com.mega4tech.whatsappapilibrary.model;

import android.database.Cursor;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mega4tech.whatsappapilibrary.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ajaybhatt on 24/11/17.
 */

public class WStoredMessage {

    private long _id;

    @SerializedName("key_remote_jid")
    private String keyRemoteJID;

    @SerializedName("key_from_me")
    private int keyFromMe;

    @SerializedName("key_id")
    private String keyID;

    @SerializedName("status")
    private int status;

    @SerializedName("need_push")
    private int needPush;

    private String data;

    private long timestamp;

    @SerializedName("media_url")
    private String mediaUrl;

    @SerializedName("media_mime_type")
    private String mediaMimeType;

    @SerializedName("media_wa_type")
    private String mediaWAType;

    @SerializedName("media_size")
    private int mediaSize;

    @SerializedName("media_name")
    private String mediaName;

    @SerializedName("media_caption")
    private String mediaCaption;

    @SerializedName("media_hash")
    private String mediaHash;

    @SerializedName("media_duration")
    private int mediaDuration;

    private int origin;

    private double lat;

    private double lng;

    private transient byte[] thumbImage;

    @SerializedName("remote_resource")
    private String remoteResource;

    @SerializedName("received_timestamp")
    private long receivedTimestamp;

    @SerializedName("send_timestamp")
    private long sendTimestamp;

    @SerializedName("receipt_server_timestamp")
    private long receiptServerTimestamp;

    @SerializedName("receipt_device_timestamp")
    private long receiptDeviceTimestamp;

    @SerializedName("read_device_timestamp")
    private long readDeviceTimestamp;

    @SerializedName("played_device_timestamp")
    private long playedDeviceTimestamp;

    private transient byte[] rawData;

    @SerializedName("recipient_count")
    private int recipientCount;

    @SerializedName("participant_hash")
    private String participantHash;

    private int starred;

    @SerializedName("quote_row_id")
    private int quotedRowId;

    @SerializedName("mentioned_jids")
    private String mentionedJIDs;

    @SerializedName("multicast_id")
    private String multicastID;

    @SerializedName("edit_version")
    private int editVersion;

    @SerializedName("media_enc_hash")
    private String mediaEncHash;

    @SerializedName("payment_transaction_id")
    private String paymentTransactionID;

    @SerializedName("media_data")
    private String mediaData;

    public WStoredMessage(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        keyRemoteJID = cursor.getString(cursor.getColumnIndexOrThrow("key_remote_jid"));
        keyFromMe = cursor.getInt(cursor.getColumnIndexOrThrow("key_from_me"));
        keyID = cursor.getString(cursor.getColumnIndexOrThrow("key_id"));
        status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));
        needPush = cursor.getInt(cursor.getColumnIndexOrThrow("needs_push"));
        data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
        timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"));
        mediaUrl = cursor.getString(cursor.getColumnIndexOrThrow("media_url"));
        mediaMimeType = cursor.getString(cursor.getColumnIndexOrThrow("media_mime_type"));
        mediaWAType = cursor.getString(cursor.getColumnIndexOrThrow("media_wa_type"));
        mediaSize = cursor.getInt(cursor.getColumnIndexOrThrow("media_size"));
        mediaName = cursor.getString(cursor.getColumnIndexOrThrow("media_name"));
        mediaCaption = cursor.getString(cursor.getColumnIndexOrThrow("media_caption"));
        mediaHash = cursor.getString(cursor.getColumnIndexOrThrow("media_hash"));
        mediaDuration = cursor.getInt(cursor.getColumnIndexOrThrow("media_duration"));
        origin = cursor.getInt(cursor.getColumnIndexOrThrow("origin"));
        lat = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
        lng = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
        thumbImage = cursor.getBlob(cursor.getColumnIndexOrThrow("thumb_image"));
        remoteResource = cursor.getString(cursor.getColumnIndexOrThrow("remote_resource"));
        receivedTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow("received_timestamp"));
        sendTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow("send_timestamp"));
        receiptServerTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow("receipt_server_timestamp"));
        receiptDeviceTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow("receipt_device_timestamp"));
        readDeviceTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow("read_device_timestamp"));
        playedDeviceTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow("played_device_timestamp"));
        rawData = cursor.getBlob(cursor.getColumnIndexOrThrow("raw_data"));
        recipientCount = cursor.getInt(cursor.getColumnIndexOrThrow("recipient_count"));
        participantHash = cursor.getString(cursor.getColumnIndexOrThrow("participant_hash"));
        starred = cursor.getInt(cursor.getColumnIndexOrThrow("starred"));
        quotedRowId = cursor.getInt(cursor.getColumnIndexOrThrow("quoted_row_id"));
        mentionedJIDs = cursor.getString(cursor.getColumnIndexOrThrow("mentioned_jids"));
        multicastID = cursor.getString(cursor.getColumnIndexOrThrow("multicast_id"));
        editVersion = cursor.getInt(cursor.getColumnIndexOrThrow("edit_version"));
        mediaEncHash = cursor.getString(cursor.getColumnIndexOrThrow("media_enc_hash"));
        paymentTransactionID = cursor.getString(cursor.getColumnIndexOrThrow("payment_transaction_id"));
    }

    public WStoredMessage(Cursor cursor, List<WMediaFile> files) {
        this(cursor);
        if (isMediaFile()) {
            int indexOf = files.indexOf(new WMediaFile(getMediaHash()));
            if (indexOf > -1) {
                WMediaFile wMediaFile = files.get(indexOf);
                setMediaData(Base64.encodeToString(Utils.readFileContent(wMediaFile.getFile()), Base64.DEFAULT));
            }
        }
    }


    public long getID() {
        return _id;
    }

    public void setID(long _id) {
        this._id = _id;
    }

    public String getKeyRemoteJID() {
        return keyRemoteJID;
    }

    public void setKeyRemoteJID(String keyRemoteJID) {
        this.keyRemoteJID = keyRemoteJID;
    }

    public int getKeyFromMe() {
        return keyFromMe;
    }

    public void setKeyFromMe(int keyFromMe) {
        this.keyFromMe = keyFromMe;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNeedPush() {
        return needPush;
    }

    public void setNeedPush(int needPush) {
        this.needPush = needPush;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaMimeType() {
        return mediaMimeType;
    }

    public void setMediaMimeType(String mediaMimeType) {
        this.mediaMimeType = mediaMimeType;
    }

    public String getMediaWAType() {
        return mediaWAType;
    }

    public void setMediaWAType(String mediaWAType) {
        this.mediaWAType = mediaWAType;
    }

    public int getMediaSize() {
        return mediaSize;
    }

    public void setMediaSize(int mediaSize) {
        this.mediaSize = mediaSize;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getMediaCaption() {
        return mediaCaption;
    }

    public void setMediaCaption(String mediaCaption) {
        this.mediaCaption = mediaCaption;
    }

    public String getMediaHash() {
        return mediaHash;
    }

    public void setMediaHash(String mediaHash) {
        this.mediaHash = mediaHash;
    }

    public int getMediaDuration() {
        return mediaDuration;
    }

    public void setMediaDuration(int mediaDuration) {
        this.mediaDuration = mediaDuration;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getRemoteResource() {
        return remoteResource;
    }

    public void setRemoteResource(String remoteResource) {
        this.remoteResource = remoteResource;
    }

    public long getReceivedTimestamp() {
        return receivedTimestamp;
    }

    public void setReceivedTimestamp(long receivedTimestamp) {
        this.receivedTimestamp = receivedTimestamp;
    }

    public long getSendTimestamp() {
        return sendTimestamp;
    }

    public void setSendTimestamp(long sendTimestamp) {
        this.sendTimestamp = sendTimestamp;
    }

    public long getReceiptServerTimestamp() {
        return receiptServerTimestamp;
    }

    public void setReceiptServerTimestamp(long receiptServerTimestamp) {
        this.receiptServerTimestamp = receiptServerTimestamp;
    }

    public long getReceiptDeviceTimestamp() {
        return receiptDeviceTimestamp;
    }

    public void setReceiptDeviceTimestamp(long receiptDeviceTimestamp) {
        this.receiptDeviceTimestamp = receiptDeviceTimestamp;
    }

    public long getReadDeviceTimestamp() {
        return readDeviceTimestamp;
    }

    public void setReadDeviceTimestamp(long readDeviceTimestamp) {
        this.readDeviceTimestamp = readDeviceTimestamp;
    }

    public byte[] getRawData() {
        return rawData;
    }

    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    public int getRecipientCount() {
        return recipientCount;
    }

    public void setRecipientCount(int recipientCount) {
        this.recipientCount = recipientCount;
    }

    public String getParticipantHash() {
        return participantHash;
    }

    public void setParticipantHash(String participantHash) {
        this.participantHash = participantHash;
    }

    public int getStarred() {
        return starred;
    }

    public void setStarred(int starred) {
        this.starred = starred;
    }

    public int getQuotedRowId() {
        return quotedRowId;
    }

    public void setQuotedRowId(int quotedRowId) {
        this.quotedRowId = quotedRowId;
    }

    public String getMentionedJIDs() {
        return mentionedJIDs;
    }

    public void setMentionedJIDs(String mentionedJIDs) {
        this.mentionedJIDs = mentionedJIDs;
    }

    public String getMulticastID() {
        return multicastID;
    }

    public void setMulticastID(String multicastID) {
        this.multicastID = multicastID;
    }

    public int getEditVersion() {
        return editVersion;
    }

    public void setEditVersion(int editVersion) {
        this.editVersion = editVersion;
    }

    public String getMediaEncHash() {
        return mediaEncHash;
    }

    public void setMediaEncHash(String mediaEncHash) {
        this.mediaEncHash = mediaEncHash;
    }

    public String getPaymentTransactionID() {
        return paymentTransactionID;
    }

    public void setPaymentTransactionID(String paymentTransactionID) {
        this.paymentTransactionID = paymentTransactionID;
    }

    public long getPlayedDeviceTimestamp() {
        return playedDeviceTimestamp;
    }

    public void setPlayedDeviceTimestamp(long playedDeviceTimestamp) {
        this.playedDeviceTimestamp = playedDeviceTimestamp;
    }

    public byte[] getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(byte[] thumbImage) {
        this.thumbImage = thumbImage;
    }

    public long getRecipientId() {
        if (keyRemoteJID == null) return -1;
        String key = keyRemoteJID.split("@")[0];
        if (key == null) return -1;
        key = key.split("-")[0];
        if (key != null) {
            return Long.parseLong(key);
        }
        return -1;
    }

    public boolean isMediaFile() {
        return mediaMimeType != null;
    }

    public String getMediaData() {
        return mediaData;
    }

    public void setMediaData(String mediaData) {
        this.mediaData = mediaData;
    }

    public Map<String, Object> getMapObj() {
        Map<String, Object> map = new HashMap<>();
        return (Map<String, Object>) new Gson().fromJson(new Gson().toJson(this), map.getClass());
    }
}
