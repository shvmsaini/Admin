package com.agrobuy.app.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.agrobuy.app.admin.databinding.ExportLogisticsBinding;
import com.agrobuy.app.admin.dataclasses.DeliveryPartner;

public class DeliveryPartnerDetailsActivity extends AppCompatActivity {
    public ExportLogisticsBinding binding;
    DeliveryPartner item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ExportLogisticsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        item = getIntent().getParcelableExtra("partner_details");

        binding.date.setText(item.getDate());
        binding.userId.setText(item.getUserID());
        binding.invoiceNumber.setText(item.getInvoiceNumber());
        binding.iecCode.setText(item.getIecCode());
        binding.deliveryDate.setText(item.getDeliveryDate());
        binding.deliveryTerms.setText(item.getDeliveryTerms());
        binding.destinationCountry.setText(item.getDestinationCountry());
        binding.destinationAddress.setText(item.getDestinationAddress());
        binding.deliveryType.setText(item.getDeliveryType());
        binding.cargoType.setText(item.getCargoType());
        binding.containerType.setText(item.getContainerType());
        binding.cargoCategory.setText(item.getCargoCategory());
        binding.restrictedGoods.setText(item.getRestrictedGoods());
        binding.noContainers.setText(item.getNoOfContainer());

        binding.topAppBar.setNavigationOnClickListener(v->{
            onBackPressed();
        });
    }
}