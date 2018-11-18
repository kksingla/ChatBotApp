package com.docsapp.activities;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.VolleyError;
import com.docsapp.bean.model.MyChatHead;
import com.docsapp.constants.IntentConstants;
import com.docsapp.utils.AndroidUtils;
import com.ks.myutils.activity.KKActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public abstract class BaseActivity extends KKActivity {

    protected View mRootLayout;

    @Override
    protected void onResume() {
        super.onResume();
        hideSoftKeyBoard();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void handleVolleyError(VolleyError error) {
        if (mRootLayout != null) {
            showSnackBarIndefinite(mRootLayout, AndroidUtils.getVolleyErrorMsg(error));
        } else {
            makeToast(AndroidUtils.getVolleyErrorMsg(error));
        }
    }


    protected String getMapToString(HashMap<String, Object> hashMap) {
        if (hashMap != null) {
            Iterator<Map.Entry<String, Object>> iterator = hashMap.entrySet().iterator();
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            try {
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    String value = URLEncoder.encode(String.valueOf(entry.getValue()), "utf-8");
                    if (i == 1) {
                        stringBuilder.append("?").append(entry.getKey()).append("=").append(value);
                    } else {
                        stringBuilder.append("&").append(entry.getKey()).append("=").append(value);
                    }
                    iterator.remove();
                    i++;
                }
                return stringBuilder.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public Context getContext() {
        return this;
    }

    public AppCompatActivity getMyActivity() {
        return this;
    }

    public void openMainActivity(MyChatHead chat) {
        startActivity(new Intent(this, MainActivity.class)
                .putExtra(IntentConstants.SELECTED_USER, chat));
    }

    public void openChatListActivity() {
        startActivity(new Intent(this, ChatListActivity.class));
    }
}
