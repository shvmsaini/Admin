package com.agrobuy.app.admin.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Buyer implements Parcelable {
    String date;
    String userID;
    String buyerID;
    String buyerName;
    String categories;
    String contactTimePref;
    String contactPersonEmail;
    String contactPersonalNumber;
    String companyPhoneNumber;

    public Buyer(String date, String userID, String buyerID,String buyerName, String categories, String contactPersonEmail,
                 String contactPersonalNumber, String companyPhoneNumber, String contactTimePref) {
        this.date = date;
        this.userID = userID;
        this.buyerID = buyerID;
        this.buyerName = buyerName;
        this.categories = categories;
        this.contactPersonEmail = contactPersonEmail;
        this.contactPersonalNumber = contactPersonalNumber;
        this.companyPhoneNumber = companyPhoneNumber;
        this.contactTimePref = contactTimePref;
    }

    protected Buyer(Parcel in) {
        date = in.readString();
        userID = in.readString();
        buyerID = in.readString();
        buyerName = in.readString();
        categories = in.readString();
        contactPersonEmail = in.readString();
        contactPersonalNumber = in.readString();
        companyPhoneNumber = in.readString();
        contactTimePref = in.readString();
    }

    public static final Creator<Buyer> CREATOR = new Creator<Buyer>() {
        @Override
        public Buyer createFromParcel(Parcel in) {
            return new Buyer(in);
        }

        @Override
        public Buyer[] newArray(int size) {
            return new Buyer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(userID);
        parcel.writeString(buyerID);
        parcel.writeString(buyerName);
        parcel.writeString(categories);
        parcel.writeString(contactPersonEmail);
        parcel.writeString(contactPersonalNumber);
        parcel.writeString(companyPhoneNumber);
        parcel.writeString(contactTimePref);
    }

    public String getDate() {
        return date;
    }

    public String getUserID() {
        return userID;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getCategories() {
        return categories;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public String getContactPersonalNumber() {
        return contactPersonalNumber;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public String getContactTimePref() {
        return contactTimePref;
    }

    public String getBuyerID() {
        return buyerID;
    }
}
