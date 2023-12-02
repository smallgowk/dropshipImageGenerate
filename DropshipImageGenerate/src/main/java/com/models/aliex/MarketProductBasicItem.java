/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

import com.models.amazon.BTGNode;
import java.util.ArrayList;

/**
 *
 * @author duyuno
 */
public class MarketProductBasicItem {
    public String id;
    public String detailUrl;
    public String title;
    public String tips;
    public String reason;
    public String description;
    public String mainKeywords;
    public String brandName;
    public String productType;
    public ArrayList<String> listBullet;
    public BTGNode bTGNode;
    public String accNo;
    public float price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetailUrl() {
        
        if (detailUrl != null && !detailUrl.startsWith("https")) {
            detailUrl = "https:" + detailUrl;
        }

        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainKeywords() {
        return mainKeywords;
    }

    public void setMainKeywords(String mainKeywords) {
        this.mainKeywords = mainKeywords;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public ArrayList<String> getListBullet() {
        return listBullet;
    }

    public void setListBullet(ArrayList<String> listBullet) {
        this.listBullet = listBullet;
    }

    
    
    public BTGNode getbTGNode() {
        return bTGNode;
    }

    public void setbTGNode(BTGNode bTGNode) {
        this.bTGNode = bTGNode;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    
}
