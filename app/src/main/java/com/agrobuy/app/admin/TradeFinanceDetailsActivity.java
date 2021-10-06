package com.agrobuy.app.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.agrobuy.app.admin.databinding.FinanceDetailsBinding;
import com.agrobuy.app.admin.dataclasses.TradeFinance;

public class TradeFinanceDetailsActivity extends AppCompatActivity {
    public FinanceDetailsBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FinanceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TradeFinance item = getIntent().getParcelableExtra("user_details");
        binding.userId.setText(item.getUserID());
        binding.userEmail.setText(item.getUserEmail());
        binding.phoneNumber.setText(item.getPhoneNumber());
        binding.customerName.setText(item.getCustomerName());
        binding.financeType.setText(item.getFinanceType());
        binding.invoiceAmount.setText(item.getInvoiceAmount());
        binding.invoiceId.setText(item.getInvoiceID());
        binding.selectedInvoice.setText(item.getSelectedInvoice());
        binding.appliedDate.setText(item.getDate());

        int len = binding.phoneNumber.getText().toString().length();
        String phoneNumber  =binding.phoneNumber.getText().toString();
        // calling link
        SpannableString callSpan = new SpannableString(phoneNumber);
        callSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            }
        }, 0, len, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        binding.phoneNumber.setText(callSpan);
        binding.phoneNumber.setMovementMethod(LinkMovementMethod.getInstance());


        //email link
        int emailLen = binding.userEmail.getText().toString().length();
        SpannableString emailSpan = new SpannableString(binding.userEmail.getText().toString());
        emailSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent (Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, item.getUserEmail());
                startActivity(intent);
            }
        }, 0, emailLen, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        binding.userEmail.setText(emailSpan);
        binding.userEmail.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
