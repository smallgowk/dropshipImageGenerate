/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.thread;

import com.api.dropship.DropApiCall;
import com.google.gson.Gson;
import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import com.utils.EncryptUtil;
import com.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PhanDuy
 */
public class SendConfigThread extends Thread {

    private static Set<Integer> hashSet = new HashSet<>();

    String singleData;
    ArrayList<BaseStoreOrderInfo> listOrders;
    String type;

    public SendConfigThread(String type, String singleData, ArrayList<BaseStoreOrderInfo> listOrders) {
        this.type = type;
        this.singleData = singleData;
        this.listOrders = listOrders;

    }

    @Override
    public void run() {
//        long start = System.currentTimeMillis();

        if (!StringUtils.isEmpty(singleData)) {
            Integer hash = singleData.hashCode();
//            System.out.println("Hash: " + hash);
            if (!hashSet.contains(singleData.hashCode())) {
                
                String encrypt = EncryptUtil.encrypt(singleData);
                
                DropApiCall.doSendUserInfo(type, encrypt, hash, null);
                hashSet.add(singleData.hashCode());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SendConfigThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        if (listOrders != null && !listOrders.isEmpty()) {
            Gson gson = new Gson();
            for (BaseStoreOrderInfo data : listOrders) {
                Integer hash = data.hashCode();
                if (!hashSet.contains(hash)) {
//                    System.out.println("StoreHash: " + hash);
                    String encrypt = EncryptUtil.encrypt(gson.toJson(data));
                    DropApiCall.doSendUserInfo(type, encrypt,hash, null);
                    hashSet.add(hash);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SendConfigThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }

}
