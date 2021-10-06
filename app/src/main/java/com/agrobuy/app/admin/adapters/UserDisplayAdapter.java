package com.agrobuy.app.admin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agrobuy.app.admin.R;
import com.agrobuy.app.admin.dataclasses.User;
import com.agrobuy.app.admin.listeners.UserOnItemClickListener;

import java.util.List;

public class UserDisplayAdapter extends RecyclerView.Adapter<UserDisplayAdapter.ItemViewHolder> {
    private static final String LOG_TAG = UserDisplayAdapter.class.getName();
    public List<User> UserList;
    public Context mContext;
    private final UserOnItemClickListener listener;


    public UserDisplayAdapter(android.content.Context context, List<User> UserList,
                              UserOnItemClickListener listener) {
        this.UserList = UserList;
        this.mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Log.d(LOG_TAG, "bindViewHolder");
        User user = UserList.get(position);
        holder.bind(user,listener);
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView userID, userEmail;

        public ItemViewHolder(View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.sub_title);
            userID = itemView.findViewById(R.id.title);
        }
        public void bind(final User item, final UserOnItemClickListener listener) {
            userID.setText(item.getUser_ID());
            userEmail.setText(item.getUserEmail());
            itemView.setOnClickListener(view -> listener.onItemClick(item));
        }


    }
}
