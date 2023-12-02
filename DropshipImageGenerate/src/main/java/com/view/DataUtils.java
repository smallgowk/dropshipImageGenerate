/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author PhanDuy
 */
public class DataUtils {
    
    public static ArrayList<BaseStoreOrderInfo> listAllStore = new ArrayList<>();
    public static HashMap<String, String> mapStatus= new HashMap<>();
    public static int total = 0;
    
    public static void updateAllStores(ArrayList<BaseStoreOrderInfo> datas) {
        listAllStore.clear();
        mapStatus.clear();
        if (datas != null) {
            listAllStore.addAll(datas);
        }
    }
    
    public static void processShipState() {
        if(listAllStore == null || listAllStore.isEmpty()) {
            total = 0;
            return;
        }
        total = listAllStore.size();
    }
    
    public static boolean isHasData() {
        return !listAllStore.isEmpty();
    }
    
    public static int getDataRow() {
        return listAllStore.size();
    }
    
    public static BaseStoreOrderInfo getState(int row) {
        return listAllStore != null && row < listAllStore.size() ? listAllStore.get(row) : null;
    }
    
    public static void clearData() {
        listAllStore.clear();
        mapStatus.clear();
        total = 0;
    }
    
    
    public static void updateStatus(String storeSign, String status) {
        mapStatus.put(storeSign, status);
    }
    
    public static String getStatus(String storeSign) {
        return mapStatus.get(storeSign);
    }
}
