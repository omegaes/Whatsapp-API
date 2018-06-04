package com.mega4tech.whatsappapilibrary.model;

import java.util.HashMap;

/**
 * Created by ajaybhatt on 03/01/18.
 */

public class WPreference {

    private String phoneNumber;

    private String pushName;

    private int participantLimit;

    private String version;

    public WPreference(HashMap<String, Object> data) {
        updateData(data);
    }

    public void updateData(HashMap<String, Object> data) {
        if (data.containsKey("ph") && !data.get("ph").toString().isEmpty()) {
            phoneNumber = String.valueOf(data.get("ph"));
        }
        if (data.containsKey("version") && !data.get("version").toString().isEmpty()) {
            version = String.valueOf(data.get("version"));
        }
        if (data.containsKey("push_name") && !data.get("push_name").toString().isEmpty()) {
            pushName = String.valueOf(data.get("push_name"));
        }
        if (data.containsKey("participants_size_limit") && !data.get("participants_size_limit").toString().isEmpty()) {
            participantLimit = (int) data.get("participants_size_limit");
        }
        if (data.containsKey("com.whatsapp.registration.RegisterPhone.phone_number") &&
                !data.get("com.whatsapp.registration.RegisterPhone.phone_number").toString().isEmpty()) {
            phoneNumber = String.valueOf(data.get("com.whatsapp.registration.RegisterPhone.phone_number"));
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getParticipantLimit() {
        return participantLimit;
    }

    public void setParticipantLimit(int participantLimit) {
        this.participantLimit = participantLimit;
    }

    public String getPushName() {
        return pushName;
    }

    public void setPushName(String pushName) {
        this.pushName = pushName;
    }
}
