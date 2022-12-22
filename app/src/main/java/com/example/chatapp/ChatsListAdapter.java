package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatsListAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<BaseChat> mChatsList;

    public ChatsListAdapter(Context context, List<BaseChat> chatsList) {
        mContext = context;
        mChatsList = chatsList;
    }


    private class ReceivedChatsHolder extends RecyclerView.ViewHolder {
        private ImageView chatIcon;
        private TextView nameText;


        ReceivedChatsHolder(View itemView) {
            super(itemView);
            chatIcon = (ImageView) itemView.findViewById(R.id.imageViewChats);
            nameText = (TextView) itemView.findViewById(R.id.textViewNameChats);

        }

        void bind(BaseChat chat) {
            //
            if (chat.icon_chat == null){
                chatIcon.setImageResource(R.drawable.addchat);
            }
            else {
                chatIcon.setImageBitmap(chat.icon_chat);

            }

            nameText.setText(chat.text_chat);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chats_file, parent, false);
        return new ChatsListAdapter.ReceivedChatsHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        BaseChat chat = (BaseChat) mChatsList.get(position);


        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseChat chat = (BaseChat) mChatsList.get(position);
        ((ChatsListAdapter.ReceivedChatsHolder) holder).bind(chat);
    }

    @Override
    public int getItemCount() {
        return mChatsList.size();
    }
}
