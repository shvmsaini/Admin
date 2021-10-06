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

import com.agrobuy.app.admin.adapters.BuyerDisplayAdapter;
import com.agrobuy.app.admin.adapters.DeliveryPartnerDisplayAdapter;
import com.agrobuy.app.admin.dataclasses.Buyer;
import com.agrobuy.app.admin.dataclasses.DeliveryPartner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuyerActivity extends AppCompatActivity {
    private static final String TAG = BuyerActivity.class.getName();
    TextView emptyView;
    public List<Buyer> buyerList = new ArrayList<>();
    RecyclerView recyclerView;
    BuyerDisplayAdapter adapter;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        emptyView = findViewById(R.id.empty_view);

        adapter = new BuyerDisplayAdapter(this, buyerList, item -> {
            Intent i = new Intent(this, BuyerDetailActivity.class);
            i.putExtra("buyer_details", item);
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
        database.getReference("buyer_network").child("applied").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) postSnapshot.getValue();
                    String time = postSnapshot.getKey();
                    for (DataSnapshot userSnapshot: postSnapshot.getChildren()) {
                        Map<String, Object> details = (Map<String, Object>) userSnapshot.getValue();
                        if (map == null || details==null) {
                            emptyView.setText("No selling history");
                            return;
                        }
                        String userID = String.valueOf(userSnapshot.getKey());
                        String buyerID = String.valueOf(details.get("buyer_ID"));
                        Log.d(TAG, "buyerID = " + buyerID);
                        String name = String.valueOf(details.get("buyer_Name"));
                        FirebaseDatabase.getInstance().getReference("buyer_network" + "/" +
                                userID + "/" +
                                buyerID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Map<String, Object> buyerDetails = (Map<String, Object>) snapshot.getValue();
                                Log.d(TAG, "snapshot" + snapshot);
                                Buyer item;
                                if (buyerDetails!=null){
                                    item = new Buyer(time,
                                            userID,
                                            buyerID,
                                            String.valueOf(buyerDetails.get("buyer_name")),
                                            String.valueOf(buyerDetails.get("categories")),
                                            String.valueOf(buyerDetails.get("contact_person_email")),
                                            String.valueOf(buyerDetails.get("contact_personal_number")),
                                            String.valueOf(buyerDetails.get("company_phone_number")),
                                            String.valueOf(buyerDetails.get("contact_time_preference"))
                                    );
                                    Log.d(TradeFinanceActivity.class.getName(), "item added: " + item);
                                    buyerList.add(item);
                                    adapter.notifyItemInserted(buyerList.size() - 1);
                                }
                                updateEmptyView();

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updateEmptyView(){
        if (buyerList.size() > 0) findViewById(R.id.empty_view).setVisibility(View.GONE);
        else {
            findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            emptyView.setText("No selling history");
        }
    }
}