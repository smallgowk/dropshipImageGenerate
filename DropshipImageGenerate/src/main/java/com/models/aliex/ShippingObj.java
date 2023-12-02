/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

/**
 *
 * @author duyuno
 */
public class ShippingObj {
    private String company;
    private String serviceName;
    private String discount;
    private String shipFrom;
    private int commitDays;
    private boolean tracking;
    private PriceUnit amount;
    private DeliveryTime deliveryTime;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getShipFrom() {
        return shipFrom;
    }

    public void setShipFrom(String shipFrom) {
        this.shipFrom = shipFrom;
    }

    public int getCommitDays() {
        return commitDays;
    }

    public void setCommitDays(int commitDays) {
        this.commitDays = commitDays;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    public PriceUnit getAmount() {
        return amount;
    }

    public void setAmount(PriceUnit amount) {
        this.amount = amount;
    }

    public DeliveryTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(DeliveryTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    
    
}
