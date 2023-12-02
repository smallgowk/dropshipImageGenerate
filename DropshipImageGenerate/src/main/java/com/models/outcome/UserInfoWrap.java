/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.outcome;

import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PhanDuy
 */
public class UserInfoWrap {
    public List<String> listBannedKeys;
    public ArrayList<BaseStoreOrderInfo> listOrders;

    public List<String> getListBannedKeys() {
        return listBannedKeys;
    }

    public void setListBannedKeys(List<String> listBannedKeys) {
        this.listBannedKeys = listBannedKeys;
    }

    public ArrayList<BaseStoreOrderInfo> getListOrders() {
        return listOrders;
    }

    public void setListOrders(ArrayList<BaseStoreOrderInfo> listOrders) {
        this.listOrders = listOrders;
    }
    
    
}
