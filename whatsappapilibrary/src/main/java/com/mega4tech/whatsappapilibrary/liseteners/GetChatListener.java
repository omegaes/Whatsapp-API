package com.mega4tech.whatsappapilibrary.liseteners;

import com.mega4tech.whatsappapilibrary.model.WChat;

import java.util.List;

/**
 * Created by ajaybhatt on 24/11/17.
 */

public interface GetChatListener {

    void onChats(List<WChat> chats) ;

    void onError(Exception e);
}
