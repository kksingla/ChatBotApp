package com.docsapp.adapter;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.docsapp.R;
import com.docsapp.bean.model.MyMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VH> {

    private static final int CLIENT = 1;
    private static final int CHAT_BOT = 0;
    private final LayoutInflater mInflater;
    private final CallBack callback;
    private Context mContext;
    private List<MyMessage> mMyMessages;

    public ChatAdapter(Context context, ChatAdapter.CallBack callBack, List<MyMessage> myMessages) {
        this.mContext = context;
        this.callback = callBack;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mMyMessages = myMessages;
    }

    @Override
    public int getItemViewType(int position) {

        MyMessage message = mMyMessages.get(position);

        if (message.getChatbotid() == 0) {
            return CLIENT;
        } else {
            return CHAT_BOT;
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CLIENT) {
            return new VH(mInflater.inflate(R.layout.item_inline_chat_right, parent, false));
        } else {
            return new VH(mInflater.inflate(R.layout.item_inline_chat_left, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MyMessage message = mMyMessages.get(position);
        if (message != null) {
            holder.tvMsg.setText(message.getMessage());
            if (callback != null && message.isPending()) {
                callback.nonResponseRxMsg(message, position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMyMessages != null ? mMyMessages.size() : 0;
    }

    public interface CallBack {
        void nonResponseRxMsg(MyMessage myMessage, int pos);
    }

    class VH extends RecyclerView.ViewHolder {
        private final TextView tvMsg;

        VH(View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tv_msg);
        }
    }

    public void setMyMessages(List<MyMessage> mMyMessages) {
        this.mMyMessages = mMyMessages;
        notifyDataSetChanged();
    }
}
