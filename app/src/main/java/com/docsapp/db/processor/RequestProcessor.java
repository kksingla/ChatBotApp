package com.docsapp.db.processor;

/**
 * Created by kushaal singla on 18-Nov-18.
 */
abstract class RequestProcessor extends DBProcessor {
    void processRequest(String operation, Object requestBody) {
        switch (operation) {
            case DBURLConstants.GET_ALL_CHAT_HEADS:
                getAllChatHeads();
                break;
            case DBURLConstants.INSERT_CHAT:
                insertChat(requestBody);
                break;
            case DBURLConstants.GET_ALL_MSG:
                getAllMsg(requestBody);
                break;
            case DBURLConstants.INSERT_MSGs:
                insertAllMsg(requestBody);
                break;

        }
    }



}
