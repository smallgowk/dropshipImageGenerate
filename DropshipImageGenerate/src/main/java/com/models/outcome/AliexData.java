/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.outcome;

import com.models.aliex.store.AliexPageInfo;
import com.models.aliex.store.AliexStoreInfo;
import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class AliexData {
    
    public boolean isOnlyUS;
    public float priceRate;
    public float priceLimit;
    public int sendEmailMode;
    
    public AliexStoreInfo storeInfo;
    public ArrayList<AliexPageInfo> listPageData;

    public boolean isIsOnlyUS() {
        return isOnlyUS;
    }

    public void setIsOnlyUS(boolean isOnlyUS) {
        this.isOnlyUS = isOnlyUS;
    }

    public float getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(float priceRate) {
        this.priceRate = priceRate;
    }

    public float getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(float priceLimit) {
        this.priceLimit = priceLimit;
    }

    public int getSendEmailMode() {
        return sendEmailMode;
    }

    public void setSendEmailMode(int sendEmailMode) {
        this.sendEmailMode = sendEmailMode;
    }

    public AliexStoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(AliexStoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    

    public ArrayList<AliexPageInfo> getListPageData() {
        return listPageData;
    }

    public void setListPageData(ArrayList<AliexPageInfo> listPageData) {
        this.listPageData = listPageData;
    }
    
    
}
