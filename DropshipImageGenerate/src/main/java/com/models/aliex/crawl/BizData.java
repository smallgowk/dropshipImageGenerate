/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

/**
 *
 * @author PhanDuy
 */
public class BizData {
    
    public static final String US_CODE = "US";
    public static final String CN_CODE = "CN";
    
    public String deliveryOptionCode;
    public String deliveryProviderName;
    public String shipToCode;
    public String shipFromCode;
    public String currency;
    public String shippingFee;
    public String company;
    public float displayAmount;
    public float discount;

    public String getDeliveryOptionCode() {
        return deliveryOptionCode;
    }

    public void setDeliveryOptionCode(String deliveryOptionCode) {
        this.deliveryOptionCode = deliveryOptionCode;
    }

    public String getDeliveryProviderName() {
        return deliveryProviderName;
    }

    public void setDeliveryProviderName(String deliveryProviderName) {
        this.deliveryProviderName = deliveryProviderName;
    }

    public String getShipToCode() {
        return shipToCode;
    }

    public void setShipToCode(String shipToCode) {
        this.shipToCode = shipToCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getDisplayAmount() {
        return displayAmount;
    }

    public void setDisplayAmount(float displayAmount) {
        this.displayAmount = displayAmount;
    }
    
    public float getShippingPrice() {
        if (discount == 100 || "free".equals(shippingFee)) {
            System.out.println("Free ship");
            return 0;
        }
        return displayAmount;
    }

    public boolean isAliexPress() {
        return company.toLowerCase().contains("aliexpress standard");
    }

    public boolean isEpacket() {
        return company.toLowerCase().contains("epacket");
    }
    
    public boolean isAliDirect() {
        return company.toLowerCase().contains("aliexpress direct");
    }
}
