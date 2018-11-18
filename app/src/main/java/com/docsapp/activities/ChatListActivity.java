package com.docsapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.docsapp.R;
import com.docsapp.adapter.ChatListAdapter;
import com.docsapp.bean.model.MyChatHead;
import com.docsapp.db.processor.DBAsync;
import com.docsapp.db.processor.DBError;
import com.docsapp.db.processor.DBResponse;
import com.docsapp.db.processor.DBURLConstants;
import com.docsapp.fragment.NewUserBottomSheetFragment;
import com.docsapp.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kushaal singla on 18-Nov-18.
 */
public class ChatListActivity extends BaseActivity implements ChatListAdapter.CallBacks, NewUserBottomSheetFragment.CallBack {
    private RecyclerView recyclerView;
    private ChatListAdapter mAdapter;
    private List<MyChatHead> list;
    private TextView tvFallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        initViews();
        getData();
    }

    private void getData() {
        DBAsync dbAsync = new DBAsync.Builder<>(DBURLConstants.GET_ALL_CHAT_HEADS,
                new DBResponse.Listener<List<MyChatHead>>() {
                    @Override
                    public void onResponse(List<MyChatHead> response) {
                        list = response;
                        setAdapter();
                    }
                },
                new DBResponse.ErrorListener() {
                    @Override
                    public void onErrorResponse(DBError error) {
                        makeToast(error.getErrorMsg());
                    }
                }).build();
        dbAsync.get();
    }

    private void setAdapter() {
        if (list != null && !list.isEmpty()) {
            if (mAdapter == null) {
                mAdapter = new ChatListAdapter(this, list, this);
                recyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setMyMessages(list);
            }

            tvFallback.setVisibility(View.GONE);
        } else {
            tvFallback.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
        tvFallback = findViewById(R.id.tv_fallback);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public void onClickNewChat(View view) {
        NewUserBottomSheetFragment.getInstance(this).show(getSupportFragmentManager(), "New");
    }

    @Override
    public void onClick(MyChatHead myChatHead) {
        openMainActivity(myChatHead);
    }

    @Override
    public void onNewUser(String name) {
        MyChatHead chatHead = new MyChatHead();
        chatHead.setName(name);
        chatHead.setExternalId(AndroidUtils.replaceAllSpaces(name).toLowerCase());
        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.indexOf(chatHead) < 0) {
            list.add(chatHead);
            setAdapter();

            DBAsync dbAsync = new DBAsync.Builder<>(DBURLConstants.INSERT_CHAT,
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
                    })
                    .setRequestBody(chatHead).build();
            dbAsync.get();
        }

        openMainActivity(chatHead);
    }
}
