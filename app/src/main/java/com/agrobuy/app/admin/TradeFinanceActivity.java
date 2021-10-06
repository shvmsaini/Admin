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

import com.agrobuy.app.admin.adapters.TradeFinanceDisplayAdapter;
import com.agrobuy.app.admin.dataclasses.TradeFinance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeFinanceActivity extends AppCompatActivity {
    private static final String TAG = TradeFinanceActivity.class.getName();
    TextView emptyView;
    public List<TradeFinance> tradeList = new ArrayList<>();
    RecyclerView recyclerView;
    TradeFinanceDisplayAdapter adapter;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        emptyView = findViewById(R.id.empty_view);

        adapter = new TradeFinanceDisplayAdapter(this, tradeList, item -> {
            Intent i = new Intent(this, TradeFinanceDetailsActivity.class);
            i.putExtra("user_details", item);
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

        //getting finances
        database.getReference("trade_finance").child("applied").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) postSnapshot.getValue();
                    String time = postSnapshot.getKey();
                    for (DataSnapshot userSnapshot: postSnapshot.getChildren()) {
                        Map<String, Object> details = (Map<String, Object>) userSnapshot.getValue();
                        TradeFinance item;
                        if (map == null || details==null) {
                            emptyView.setText(R.string.no_finance);
                            return;
                        }
                        item = new TradeFinance(time,
                                userSnapshot.getKey(),
                                String.valueOf(details.get("email")),
                                String.valueOf(details.get("customer_name")),
                                String.valueOf(details.get("finance_type")),
                                String.valueOf(details.get("invoice_amount")),
                                String.valueOf(details.get("invoice_id")),
                                String.valueOf(details.get("phone_number")),
                                String.valueOf(details.get("select_invoice")));
                        Log.d(TradeFinanceActivity.class.getName(), "item added: " + item);
                        tradeList.add(item);
                        adapter.notifyItemInserted(tradeList.size() - 1);
                    }
                }
                if (tradeList.size() > 0) findViewById(R.id.empty_view).setVisibility(View.GONE);
                else {
                    findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
                    emptyView.setText(R.string.no_user_found);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}