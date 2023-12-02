/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.transform;

import com.models.aliex.AliexProductFull;
import com.models.authen.Account;
import com.models.aliex.store.AliexPageInfo;
import com.models.aliex.store.AliexStoreInfo;
import com.models.outcome.AliexProductInfoWrap;
import com.models.outcome.AliexStoreInfoWrap;
import com.models.outcome.AliexStorePageWrap;
import com.models.outcome.BaseObj;
import com.models.outcome.RegisterWrap;
import com.models.outcome.RequestObj;
import com.utils.AppUtil;
import com.utils.ComputerIdentifier;
import com.utils.Constants;

/**
 *
 * @author PhanDuy
 */
public class TransformToServer {
    private static TransformToServer transformToServer;
    
    public static TransformToServer getInstance() {
        if(transformToServer == null) {
            transformToServer = new TransformToServer();
        }
        
        return transformToServer;
    }
    
    
    public AliexStoreInfoWrap transformData(AliexStoreInfo aliexStoreInfo) {
        AliexStoreInfoWrap aliexStoreInfoWrap = new AliexStoreInfoWrap();
        initBaseInfo(aliexStoreInfoWrap);
        aliexStoreInfoWrap.setAction(Constants.TCPAction.STORE_INFO);
        aliexStoreInfoWrap.setData(aliexStoreInfo);
        
        return aliexStoreInfoWrap;
    }
    
    public AliexStorePageWrap transformData(AliexPageInfo aliexPageInfo) {
        AliexStorePageWrap aliexStorePageWrap = new AliexStorePageWrap();
        initBaseInfo(aliexStorePageWrap);
        aliexStorePageWrap.setAction(Constants.TCPAction.PAGE_INFO);
        aliexStorePageWrap.setData(aliexPageInfo);
        
        return aliexStorePageWrap;
    }
    
    public AliexProductInfoWrap transformData(AliexProductFull aliexProductFull) {
        AliexProductInfoWrap aliexStoreInfoWrap = new AliexProductInfoWrap();
        initBaseInfo(aliexStoreInfoWrap);
        aliexStoreInfoWrap.setAction(Constants.TCPAction.PRODUCT);
        aliexStoreInfoWrap.setData(aliexProductFull);
        
        return aliexStoreInfoWrap;
    }
    
    public RegisterWrap transformData(Account account) {
        RegisterWrap registerWrap = new RegisterWrap();
        initComputerInfo(registerWrap);
        registerWrap.setAccount(account);
        registerWrap.setAction(Constants.TCPAction.SIGNUP);
        return registerWrap;
    }
    
    public BaseObj getAccountInfoWrap() {
        BaseObj baseObj = new BaseObj();
        initComputerInfo(baseObj);
        baseObj.setAction(Constants.TCPAction.GET_ACCOUNT);
        baseObj.setModule("PLGenerateTool");
        return baseObj;
    }
    
    public BaseObj getCertWrap() {
        BaseObj baseObj = new BaseObj();
        initComputerInfo(baseObj);
        baseObj.setAction(Constants.TCPAction.GET_CERT);
        return baseObj;
    }
    
    public BaseObj updateInfoWrap(String message) {
        BaseObj baseObj = new BaseObj();
        initComputerInfo(baseObj);
        baseObj.setClientMessage(message);
        baseObj.setAction(Constants.TCPAction.UPDATE_INFO);
        return baseObj;
    }
    
    public void initBaseInfo(RequestObj requestObj) {
        requestObj.setDiskSerialNumber(ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-"));
        requestObj.setComputerName(ComputerIdentifier.getComputerName());
        requestObj.setVersion(AppUtil.getAppVersion());
    }
    
    public void initComputerInfo(BaseObj requestObj) {
        requestObj.setDiskSerialNumber(ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-"));
        System.out.println("Serial: " + requestObj.getDiskSerialNumber());
        requestObj.setComputerName(ComputerIdentifier.getComputerName());
        requestObj.setVersion(AppUtil.getAppVersion());
    }
}
