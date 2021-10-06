package com.agrobuy.app.admin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agrobuy.app.admin.dataclasses.Buyer;
import com.agrobuy.app.admin.dataclasses.DeliveryPartner;
import com.agrobuy.app.admin.R;
import com.agrobuy.app.admin.listeners.BuyerOnItemClickListener;
import com.agrobuy.app.admin.listeners.DeliveryPartnerOnItemClickListener;

import java.util.List;

public class BuyerDisplayAdapter
        extends RecyclerView.Adapter<BuyerDisplayAdapter.ItemViewHolder>  {
    private static final String LOG_TAG = BuyerDisplayAdapter.class.getName();
    public List<Buyer> list;
    public Context mContext;
    private final BuyerOnItemClickListener listener;


    public BuyerDisplayAdapter(android.content.Context context, List<Buyer> list,
                               BuyerOnItemClickListener listener) {
        this.list = list;
        this.mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BuyerDisplayAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new BuyerDisplayAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerDisplayAdapter.ItemViewHolder holder, int position) {
        Log.d(LOG_TAG, "bindViewHolder");
        Buyer item = list.get(position);
        holder.bind(item,listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView date, userID;

        public ItemViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.title);
            userID = itemView.findViewById(R.id.sub_title);
        }

        public void bind(final Buyer item, final BuyerOnItemClickListener listener) {
            date.setText(item.getDate());
            userID.setText(item.getUserID());
            itemView.setOnClickListener(view -> listener.onItemClick(item));
        }
    }
}
