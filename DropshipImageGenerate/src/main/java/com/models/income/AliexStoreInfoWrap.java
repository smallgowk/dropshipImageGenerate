/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.income;

import com.models.aliex.store.AliexStoreInfo;

/**
 *
 * @author PhanDuy
 */
public class AliexStoreInfoWrap extends IncomeObj{
    public AliexStoreInfo data;

    public AliexStoreInfo getData() {
        return data;
    }

    public void setData(AliexStoreInfo data) {
        this.data = data;
    }
    
}
