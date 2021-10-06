package com.agrobuy.app.admin.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryPartner implements Parcelable {
    String date;
    String userID;
    String cargoCategory;
    String cargoType;
    String containerType;
    String deliveryDate;
    String deliveryTerms;
    String deliveryType;
    String destinationAddress;
    String destinationCountry;
    String exporterID;
    String iecCode;
    String invoiceNumber;
    String noOfContainer;
    String restrictedGoods;

    protected DeliveryPartner(Parcel in) {
         date = in.readString();
         userID = in.readString();
         cargoCategory= in.readString();
         cargoType= in.readString();
         containerType= in.readString();
         deliveryDate= in.readString();
         deliveryTerms= in.readString();
         deliveryType= in.readString();
         destinationAddress= in.readString();
         destinationCountry= in.readString();
         exporterID= in.readString();
         iecCode= in.readString();
         invoiceNumber= in.readString();
         noOfContainer= in.readString();
         restrictedGoods= in.readString();
    }

    public DeliveryPartner(String date,String userID,String cargoCategory, String cargoType, String containerType, String deliveryDate,
                           String deliveryTerms, String deliveryType, String destinationAddress,
                           String destinationCountry, String exporterID, String iecCode, String invoiceNumber,
                           String noOfContainer, String restrictedGoods) {
        this.date = date;
        this.userID = userID;
        this.cargoCategory = cargoCategory;
        this.cargoType = cargoType;
        this.containerType = containerType;
        this.deliveryDate = deliveryDate;
        this.deliveryTerms = deliveryTerms;
        this.deliveryType = deliveryType;
        this.destinationAddress = destinationAddress;
        this.destinationCountry = destinationCountry;
        this.exporterID = exporterID;
        this.iecCode = iecCode;
        this.invoiceNumber = invoiceNumber;
        this.noOfContainer = noOfContainer;
        this.restrictedGoods = restrictedGoods;
    }

    public static final Creator<DeliveryPartner> CREATOR = new Creator<DeliveryPartner>() {
        @Override
        public DeliveryPartner createFromParcel(Parcel in) {
            return new DeliveryPartner(in);
        }

        @Override
        public DeliveryPartner[] newArray(int size) {
            return new DeliveryPartner[size];
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
        parcel.writeString(cargoCategory);
        parcel.writeString(cargoType);
        parcel.writeString(containerType);
        parcel.writeString(deliveryDate);
        parcel.writeString(deliveryTerms);
        parcel.writeString(deliveryType);
        parcel.writeString(destinationAddress);
        parcel.writeString(destinationCountry);
        parcel.writeString(exporterID);
        parcel.writeString(iecCode);
        parcel.writeString(invoiceNumber);
        parcel.writeString(noOfContainer);
        parcel.writeString(restrictedGoods);
    }

    public String getDate() {
        return date;
    }

    public String getUserID() {
        return userID;
    }

    public String getCargoCategory() {
        return cargoCategory;
    }

    public String getCargoType() {
        return cargoType;
    }

    public String getContainerType() {
        return containerType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public String getExporterID() {
        return exporterID;
    }

    public String getIecCode() {
        return iecCode;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getNoOfContainer() {
        return noOfContainer;
    }

    public String getRestrictedGoods() {
        return restrictedGoods;
    }
}
