package com.mega4tech.whatsappapilibrary.liseteners;

import com.mega4tech.whatsappapilibrary.exception.WhatsappNotInstalledException;
import com.mega4tech.whatsappapilibrary.model.WStoredMessage;

import java.io.IOException;
import java.util.List;

/**
 * Created by ajaybhatt on 24/11/17.
 */

public interface GetMessageListener {

    void receiveWhatsappMessages(List<WStoredMessage> messages);

    void onError(Exception exception);
}
