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
import com.docsapp.bean.model.MyChatHead;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.VH> {

    private final LayoutInflater mInflater;
    private final CallBacks callBacks;
    private List<MyChatHead> list;

    public ChatListAdapter(Context context, List<MyChatHead> myChatHeads, ChatListAdapter.CallBacks callBacks) {
        this.callBacks = callBacks;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = myChatHeads;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(mInflater.inflate(R.layout.item_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MyChatHead chatHead = list.get(position);
        holder.tvName.setText(chatHead.getName());
        holder.tvTime.setText(chatHead.getExternalId());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public interface CallBacks {
        void onClick(MyChatHead myJob);
    }

    class VH extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvTime;

        VH(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (callBacks != null && position != RecyclerView.NO_POSITION) {
                        callBacks.onClick(list.get(position));
                    }
                }
            });

            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

    public void setMyMessages(List<MyChatHead> myChatHeads) {
        this.list = myChatHeads;
        notifyDataSetChanged();
    }
}
