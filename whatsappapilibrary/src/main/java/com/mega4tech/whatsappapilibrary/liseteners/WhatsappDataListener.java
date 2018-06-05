package com.mega4tech.whatsappapilibrary.liseteners;

import com.mega4tech.whatsappapilibrary.model.WIdentity;

import java.util.List;

/**
 * Created by ajaybhatt on 25/11/17.
 */

public interface WhatsappDataListener {

    void onIdentityData(List<WIdentity> data);

    void onIdentityData(WIdentity data);

}
