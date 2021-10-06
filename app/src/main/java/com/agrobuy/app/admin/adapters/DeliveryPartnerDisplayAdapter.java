package com.agrobuy.app.admin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agrobuy.app.admin.dataclasses.DeliveryPartner;
import com.agrobuy.app.admin.R;
import com.agrobuy.app.admin.listeners.DeliveryPartnerOnItemClickListener;

import java.util.List;

public class DeliveryPartnerDisplayAdapter
        extends RecyclerView.Adapter<DeliveryPartnerDisplayAdapter.ItemViewHolder>  {
     private static final String LOG_TAG = DeliveryPartnerDisplayAdapter.class.getName();
        public List<DeliveryPartner> list;
        public Context mContext;
        private final DeliveryPartnerOnItemClickListener listener;


        public DeliveryPartnerDisplayAdapter(android.content.Context context, List<DeliveryPartner> list,
                                          DeliveryPartnerOnItemClickListener listener) {
            this.list = list;
            this.mContext = context;
            this.listener = listener;
        }

        @NonNull
        @Override
        public DeliveryPartnerDisplayAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item, parent, false);
            return new DeliveryPartnerDisplayAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DeliveryPartnerDisplayAdapter.ItemViewHolder holder, int position) {
            Log.d(LOG_TAG, "bindViewHolder");
            DeliveryPartner item = list.get(position);
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

            public void bind(final DeliveryPartner item, final DeliveryPartnerOnItemClickListener listener) {
                date.setText(item.getDate());
                userID.setText(item.getUserID());
                itemView.setOnClickListener(view -> listener.onItemClick(item));
            }
        }
}
