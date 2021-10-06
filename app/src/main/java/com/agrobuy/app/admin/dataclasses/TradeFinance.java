package com.agrobuy.app.admin.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

public class TradeFinance implements Parcelable {
    String date,userID,userEmail,customerName,financeType,invoiceAmount,invoiceID,
            phoneNumber,selectedInvoice;

    protected TradeFinance(Parcel in) {
        date = in.readString();
        userID = in.readString();
        userEmail = in.readString();
        customerName = in.readString();
        financeType = in.readString();
        invoiceAmount = in.readString();
        invoiceID = in.readString();
        phoneNumber = in.readString();
        selectedInvoice = in.readString();
    }

    public TradeFinance(String date, String userID, String userEmail, String customerName, String financeType,
                        String invoiceAmount, String invoiceID, String phoneNumber,
                        String selectedInvoice) {
        this.date = date;
        this.userID = userID;
        this.userEmail = userEmail;
        this.customerName = customerName;
        this.financeType = financeType;
        this.invoiceAmount = invoiceAmount;
        this.invoiceID = invoiceID;
        this.phoneNumber = phoneNumber;
        this.selectedInvoice = selectedInvoice;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(userID);
        dest.writeString(userEmail);
        dest.writeString(customerName);
        dest.writeString(financeType);
        dest.writeString(invoiceAmount);
        dest.writeString(invoiceID);
        dest.writeString(phoneNumber);
        dest.writeString(selectedInvoice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TradeFinance> CREATOR = new Creator<TradeFinance>() {
        @Override
        public TradeFinance createFromParcel(Parcel in) {
            return new TradeFinance(in);
        }

        @Override
        public TradeFinance[] newArray(int size) {
            return new TradeFinance[size];
        }
    };

    public String getDate() {
        return date;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getFinanceType() {
        return financeType;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSelectedInvoice() {
        return selectedInvoice;
    }
}
