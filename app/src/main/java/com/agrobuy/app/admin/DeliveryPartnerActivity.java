package com.agrobuy.app.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.agrobuy.app.admin.adapters.DeliveryPartnerDisplayAdapter;
import com.agrobuy.app.admin.dataclasses.DeliveryPartner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeliveryPartnerActivity extends AppCompatActivity {
    private static final String TAG = TradeFinanceActivity.class.getName();
    TextView emptyView;
    public List<DeliveryPartner> partnerList = new ArrayList<>();
    RecyclerView recyclerView;
    DeliveryPartnerDisplayAdapter adapter;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        emptyView = findViewById(R.id.empty_view);

        adapter = new DeliveryPartnerDisplayAdapter(this, partnerList, item -> {
            Intent i = new Intent(this, DeliveryPartnerDetailsActivity.class);
            i.putExtra("partner_details", item);
            startActivity(i);
        });
        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.empty_view);
        emptyView.setText(R.string.loading);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable divider = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.divider);
        if (divider != null) {
            itemDecorator.setDrawable(divider);
            recyclerView.addItemDecoration(itemDecorator);
        }
        recyclerView.setAdapter(adapter);

        //getting delivery partners applied
        database.getReference("delivery_partners").child("applied").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) postSnapshot.getValue();
                    String time = postSnapshot.getKey();
                    for (DataSnapshot userSnapshot: postSnapshot.getChildren()) {
                        Map<String, Object> details = (Map<String, Object>) userSnapshot.getValue();
                        DeliveryPartner item;
                        if (map == null || details==null) {
                            emptyView.setText(R.string.no_delivery_partner);
                            return;
                        }
                        item = new DeliveryPartner(time,
                                userSnapshot.getKey(),
                                String.valueOf(details.get("cargo_category")),
                                String.valueOf(details.get("cargo_type")),
                                String.valueOf(details.get("container_type")),
                                String.valueOf(details.get("delivery_date")),
                                String.valueOf(details.get("delivery_terms")),
                                String.valueOf(details.get("delivery_type")),
                                String.valueOf(details.get("destination_address")),
                                String.valueOf(details.get("destination_country")),
                                String.valueOf(details.get("exporter_id")),
                                String.valueOf(details.get("iec_code")),
                                String.valueOf(details.get("invoice_number")),
                                String.valueOf(details.get("no_of_containers")),
                                String.valueOf(details.get("restricted_goods"))
                                );
                        Log.d(TradeFinanceActivity.class.getName(), "item added: " + item);
                        partnerList.add(item);
                        adapter.notifyItemInserted(partnerList.size() - 1);
                    }
                }
                if (partnerList.size() > 0) findViewById(R.id.empty_view).setVisibility(View.GONE);
                else {
                    findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
                    emptyView.setText(R.string.no_delivery_partner);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}