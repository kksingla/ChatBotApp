package com.docsapp.activities;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.docsapp.R;
import com.docsapp.adapter.ChatAdapter;
import com.docsapp.bean.model.MyChatHead;
import com.docsapp.bean.model.MyMessage;
import com.docsapp.bean.response.ChatResponse;
import com.docsapp.constants.IntentConstants;
import com.docsapp.constants.URLConstants;
import com.docsapp.db.processor.DBAsync;
import com.docsapp.db.processor.DBError;
import com.docsapp.db.processor.DBResponse;
import com.docsapp.db.processor.DBURLConstants;
import com.docsapp.server.CustomGSONGetRequest;
import com.docsapp.server.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView rvChat;
    private List<MyMessage> myMessagesList;
    private ChatAdapter mAdapter;
    private TextView tvSend;
    private TextView tvFallback;
    private EditText etChatMsg;

    private static final int CHAT_BOT_ID = 63906;
    private String externalID;
    private MyChatHead selectedUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBackButton(true);
        initViews();
        getData();
    }

    private void getData() {
        selectedUser = (MyChatHead) getIntent().getSerializableExtra(IntentConstants.SELECTED_USER);
        if (selectedUser != null) {
            externalID = selectedUser.getExternalId();
            tvFallback.setText(getString(R.string.no_msg, selectedUser.getName()));
        }

        DBAsync dbAsync = new DBAsync.Builder<>(DBURLConstants.GET_ALL_MSG,
                new DBResponse.Listener<List<MyMessage>>() {
                    @Override
                    public void onResponse(List<MyMessage> response) {
                        if (myMessagesList == null)
                            myMessagesList = new ArrayList<>();
                        myMessagesList.addAll(response);
                        setData();
                    }
                },
                new DBResponse.ErrorListener() {
                    @Override
                    public void onErrorResponse(DBError error) {
                        makeToast(error.getErrorMsg());
                    }
                }).setRequestBody(externalID).build();
        dbAsync.get();
    }

    private void setData() {
        if (myMessagesList != null && !myMessagesList.isEmpty()) {
            tvFallback.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new ChatAdapter(this, myMessagesList);
                rvChat.setAdapter(mAdapter);

            } else {
                mAdapter.setMyMessages(myMessagesList);
            }

            try {
                rvChat.scrollToPosition(myMessagesList.size() - 1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            tvFallback.setVisibility(View.VISIBLE);
        }
    }


    private void initViews() {
        tvFallback = findViewById(R.id.tv_msg);
        tvSend = findViewById(R.id.tv_send);
        etChatMsg = findViewById(R.id.et_chat_msg);
        rvChat = findViewById(R.id.recycler_view);
        rvChat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        etChatMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (TextUtils.isEmpty(charSequence.toString())) {
                        disableSend();
                    } else {
                        enableSend();
                    }
                } catch (Exception ex) {
                    enableSend();
                    ex.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMessage message = new MyMessage();
                message.setMessage(etChatMsg.getText().toString());
                addMsg(message);
                callNewMessageAPI(message.getMessage(), selectedUser.getName());
                etChatMsg.setText("");
            }
        });
    }

    private void enableSend() {
        tvSend.setEnabled(true);
        tvSend.setAlpha(1f);
    }

    private void disableSend() {
        tvSend.setEnabled(false);
        tvSend.setAlpha(.3f);
    }

    private void callNewMessageAPI(String message, String name) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("apiKey", "6nt5d1nJHkqbkphe");
            map.put("firstName", name);
            map.put("message", message);
            map.put("chatBotID", CHAT_BOT_ID);
            map.put("externalID", externalID);

            CustomGSONGetRequest<ChatResponse> request = new CustomGSONGetRequest<>(this, URLConstants.CHAT,
                    ChatResponse.class, getMapToString(map),
                    new Response.Listener<ChatResponse>() {
                        @Override
                        public void onResponse(ChatResponse response) {
                            try {
                                if (response.isSuccess()) {
                                    addMsg(response.getMessage());
                                } else {
                                    makeToast(response.getErrormessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleVolleyError(error);
                        }
                    }, false, null);
            VolleySingleton.getInstance().getRequestQueue().add(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addMsg(MyMessage message) {
        if (myMessagesList == null) {
            myMessagesList = new ArrayList<>();
        }
        message.setExternalId(externalID);
        myMessagesList.add(message);
        setData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myMessagesList != null && !myMessagesList.isEmpty()) {
            DBAsync dbAsync = new DBAsync.Builder<>(DBURLConstants.INSERT_MSGs,
                    new DBResponse.Listener<Boolean>() {
                        @Override
                        public void onResponse(Boolean response) {

                        }
                    },
                    new DBResponse.ErrorListener() {
                        @Override
                        public void onErrorResponse(DBError error) {
                            makeToast(error.getErrorMsg());
                        }
                    }).setRequestBody(myMessagesList).build();
            dbAsync.get();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
