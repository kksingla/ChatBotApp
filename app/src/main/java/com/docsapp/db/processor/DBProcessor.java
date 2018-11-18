package com.docsapp.db.processor;

import com.docsapp.bean.model.MyChatHead;
import com.docsapp.bean.model.MyMessage;
import com.docsapp.config.MyApplication;
import com.docsapp.enums.RequestStatus;

import java.util.List;

public abstract class DBProcessor {
    private MyDB db;

    DBProcessor() {
        this.db = MyDB.getAppDatabase(MyApplication.getContext());
    }

    protected abstract void sendResponse(Object obj);

    protected abstract void sendError(DBError dbError);

    void getAllChatHeads() {
        List<MyChatHead> all = db.myChatDao().getAll();
        sendResponse(all);
    }

    void getAllMsg(Object requestBody) {
        if (requestBody instanceof String) {
            String extId = (String) requestBody;
            List<MyMessage> all = db.myMessageDao().getAll(extId);
            sendResponse(all);
        } else {
            sendError(new DBError("Invalid Request"));
        }

    }

    void insertChat(Object requestBody) {
        if (requestBody instanceof MyChatHead) {
            MyChatHead chatHead = (MyChatHead) requestBody;
            db.myChatDao().insert(chatHead);
        } else {
            sendError(new DBError("Invalid Request"));
        }
    }

    void insertAllMsg(Object requestBody) {
        if (requestBody instanceof List) {
            if (!((List) requestBody).isEmpty() && (((List) requestBody).get(0) instanceof MyMessage)) {
                List<MyMessage> myMessages = (List<MyMessage>) requestBody;
                for (MyMessage message : myMessages) {
                    if (message.getReqStatus() == RequestStatus.INIT.getValue()) {
                        message.setReqStatus(RequestStatus.PENDING.getValue());
                    }
                }

                db.myMessageDao().insertAll(myMessages);
            } else {
                sendError(new DBError("Invalid Request"));
            }
        } else {
            sendError(new DBError("Invalid Request"));
        }
    }

}