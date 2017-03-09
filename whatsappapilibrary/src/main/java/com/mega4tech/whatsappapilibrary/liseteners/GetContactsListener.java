package com.mega4tech.whatsappapilibrary.liseteners;

import com.mega4tech.whatsappapilibrary.model.WContact;

import java.util.List;

/**
 * Created by aboodba on 02/03/2017.
 */

public interface GetContactsListener {

    void receiveWhatsappContacts(List<WContact> contacts);
}
