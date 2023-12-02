/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.outcome;

import com.models.aliex.store.AliexStoreInfo;

/**
 *
 * @author PhanDuy
 */
public class AliexStoreInfoWrap extends RequestObj{
    public AliexStoreInfo storeInfo;

    public AliexStoreInfo getData() {
        return storeInfo;
    }

    public void setData(AliexStoreInfo data) {
        this.storeInfo = data;
    }
    
}
