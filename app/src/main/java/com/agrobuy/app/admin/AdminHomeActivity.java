package com.agrobuy.app.admin;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        Button user = findViewById(R.id.users);
        Button tradeFinance = findViewById(R.id.trade_finance);
        Button logistics = findViewById(R.id.logistics);
        Button buyer = findViewById(R.id.buyer);

        setSupportActionBar(findViewById(R.id.topAppBar));

        Toolbar topBar = findViewById(R.id.topAppBar);

        if(topBar.getOverflowIcon()!=null){
            topBar.getOverflowIcon().setColorFilter(ContextCompat.getColor(this,R.color.white), PorterDuff.Mode.SRC_ATOP);
        }

        user.setOnClickListener(v->{
            Intent intent = new Intent(this, UsersActivity.class);
            startActivity(intent);
        });
        tradeFinance.setOnClickListener(v->{
            Intent intent = new Intent(this, TradeFinanceActivity.class);
            startActivity(intent);
        });
        logistics.setOnClickListener(v->{
            Intent intent = new Intent(this, DeliveryPartnerActivity.class);
            startActivity(intent);
        });
        buyer.setOnClickListener(v->{
            Intent intent = new Intent(this, BuyerActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.sign_out).setVisible(true);
        menu.findItem(R.id.sign_out).setOnMenuItemClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return true;
        });
        return true;
    }

}