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
import com.agrobuy.app.admin.dataclasses.TradeFinance;
import com.agrobuy.app.admin.listeners.TradeFinanceOnItemClickListener;

import java.util.List;

public class TradeFinanceDisplayAdapter extends RecyclerView.Adapter<TradeFinanceDisplayAdapter.ItemViewHolder> {
    private static final String LOG_TAG = TradeFinanceDisplayAdapter.class.getName();
    public List<TradeFinance> list;
    public Context mContext;
    private final TradeFinanceOnItemClickListener listener;


    public TradeFinanceDisplayAdapter(android.content.Context context, List<TradeFinance> list,
                              TradeFinanceOnItemClickListener listener) {
        this.list = list;
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
        TradeFinance item = list.get(position);
        holder.bind(item,listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView date,userID;

        public ItemViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.title);
            userID = itemView.findViewById(R.id.sub_title);
        }
        public void bind(final TradeFinance item, final TradeFinanceOnItemClickListener listener) {
            date.setText(item.getDate());
            userID.setText(item.getUserID());
            itemView.setOnClickListener(view -> listener.onItemClick(item));
        }


    }
}
