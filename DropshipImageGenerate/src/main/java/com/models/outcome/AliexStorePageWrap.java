/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.outcome;

import com.models.aliex.store.AliexPageInfo;

/**
 *
 * @author PhanDuy
 */
public class AliexStorePageWrap extends RequestObj{
    public AliexPageInfo pageInfo;

    public AliexPageInfo getData() {
        return pageInfo;
    }

    public void setData(AliexPageInfo data) {
        this.pageInfo = data;
    }
    
}
