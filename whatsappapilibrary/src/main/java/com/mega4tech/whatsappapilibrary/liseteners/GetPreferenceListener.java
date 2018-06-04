package com.mega4tech.whatsappapilibrary.liseteners;

import com.mega4tech.whatsappapilibrary.model.WPreference;

/**
 * Created by ajaybhatt on 03/01/18.
 */

public interface GetPreferenceListener {

    void onPreference(WPreference preference);

    void onError(Exception e);
}
