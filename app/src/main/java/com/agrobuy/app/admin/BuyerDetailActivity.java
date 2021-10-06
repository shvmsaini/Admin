package com.agrobuy.app.admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.agrobuy.app.admin.databinding.BuyerDetailBinding;
import com.agrobuy.app.admin.dataclasses.Buyer;

public class BuyerDetailActivity extends AppCompatActivity {
    public BuyerDetailBinding binding;
    public Buyer item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BuyerDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        item = getIntent().getParcelableExtra("buyer_details");
        binding.date.setText(item.getDate());
        binding.userId.setText(item.getUserID());
        binding.buyerId.setText(item.getBuyerID());
        binding.name.setText(item.getBuyerName());
        binding.categories.setText(item.getCategories());
        binding.timepref.setText(item.getContactTimePref());
        binding.phoneNumber.setText(item.getCompanyPhoneNumber());
        binding.personalNumber.setText(item.getContactPersonalNumber());
        binding.email.setText(item.getContactPersonEmail());



        binding.topAppBar.setNavigationOnClickListener(v->{onBackPressed();});
    }
}
