/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

/**
 *
 * @author PhanDuy
 */
public class ComputerLicense {
    private String serial;
    private String license;
    private String licenseType;
    private long period;
    private int totalProduct;
    private int currentProduct;
    private int price;
    private long startTime;
    private long endTime;
    
    public void initEndTime() {
        endTime = startTime + (long)period * 24 * 60 * 60 * 1000;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(int currentProduct) {
        this.currentProduct = currentProduct;
    }
    
    
}
