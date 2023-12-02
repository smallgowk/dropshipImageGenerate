package com.api.dropship.req;

import java.util.ArrayList;

public class SendLogInfoReq {
    public String computerSerial;
    public int year;
    public int month;
    public String storeUrl;
    public int productCount;

    public void setComputerSerial(String computerSerial) {
        this.computerSerial = computerSerial;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
    
    public void addProductCount() {
        this.productCount ++;
    }
}
