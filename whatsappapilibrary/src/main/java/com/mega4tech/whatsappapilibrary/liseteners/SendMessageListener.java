package com.mega4tech.whatsappapilibrary.liseteners;

import com.mega4tech.whatsappapilibrary.model.WContact;
import com.mega4tech.whatsappapilibrary.model.WMessage;

import java.util.List;

/**
 * Created by aboodba on 02/03/2017.
 */

public interface SendMessageListener {
    void finishSendWMessage(List<WContact> contact, WMessage message);
}
