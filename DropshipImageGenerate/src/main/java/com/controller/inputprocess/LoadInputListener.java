/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.inputprocess;

import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public interface LoadInputListener {
    public void onLoaded(ArrayList<BaseStoreOrderInfo> listOrders);
}
