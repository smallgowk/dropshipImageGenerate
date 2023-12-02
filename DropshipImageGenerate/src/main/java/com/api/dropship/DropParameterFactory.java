/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.dropship;

import com.api.dropship.req.SendInfoReq;
import com.api.dropship.req.TransformAliexToAmzReq;
import com.api.dropship.req.UpdateClientConfigReq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.models.aliex.crawl.AliexScriptDetailData;
import com.models.outcome.AliexProductInfoWrap;
import com.models.outcome.AliexStoreInfoWrap;
import com.models.outcome.AliexStorePageWrap;
import com.models.outcome.BaseObj;
import com.models.outcome.RegisterWrap;
import com.models.outcome.RequestObj;
import com.models.request.TransformAliexCrawlDataReq;
import com.mysql.cj.jdbc.result.UpdatableResultSet;
import com.utils.AppUtil;
import com.utils.ComputerIdentifier;
import com.utils.Constants;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author PhanDuy
 */
public class DropParameterFactory {
    
    
    public static StringEntity createSendStoreInfoEntity(AliexStoreInfoWrap aliexStoreInfoWrap) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String description = aliexStoreInfoWrap.getData().getDescription();
        description = description.replace("★★★", "{bullet}");
        aliexStoreInfoWrap.getData().setDescription(description);
        String json = gson.toJson(aliexStoreInfoWrap);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createSendPageInfoEntity(AliexStorePageWrap aliexStorePageWrap) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(aliexStorePageWrap);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createSendProductInfoEntity(AliexProductInfoWrap aliexProductInfoWrap) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(aliexProductInfoWrap);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createSendUserInfoEntity(String type, String data, int hash) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(createSendUserInfoReq(type, data, hash));
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
//    public static StringEntity createSendUserInfoEntity(Object data) {
//        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        String json = gson.toJson(createSendUserInfoReq(data, gson));
//        return new StringEntity(json, ContentType.APPLICATION_JSON);
//    }
//    
//    public static StringEntity createSendUserInfoEntity(BaseStoreOrderInfo order) {
//        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        String json = gson.toJson(createSendUserInfoReq(order, gson));
//        return new StringEntity(json, ContentType.APPLICATION_JSON);
//    }
    
    public static StringEntity createRegisterReq(RegisterWrap registerWrap) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(registerWrap);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createGetCertReq(BaseObj baseObj) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(baseObj);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createGetAccountInfoReq(BaseObj baseObj) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(baseObj);
        System.out.println("Data: " + json);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createCheckLicenseReq(String license) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        RequestObj requestObj = createBaseRequestObj();
        requestObj.setLicense(license);
        requestObj.setAction(Constants.TCPAction.CHECK_LICENSE);
        String json = gson.toJson(requestObj);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createCheckSerialReq() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        RequestObj requestObj = createBaseRequestObj();
        requestObj.setAction(Constants.TCPAction.CHECK_SERIAL);
        String json = gson.toJson(requestObj);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createUpdateLicenseReq(String license) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        RequestObj requestObj = createBaseRequestObj();
        requestObj.setLicense(license);
        requestObj.setAction(Constants.TCPAction.UPDATE_LICENSE);
        String json = gson.toJson(requestObj);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createSendInfoReq() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        RequestObj requestObj = createBaseRequestObj();
        requestObj.setAction(Constants.TCPAction.SEND_INFO);
        String json = gson.toJson(requestObj);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    
    
    public static RequestObj createBaseRequestObj() {
        RequestObj requestObj = new RequestObj();
        requestObj.setDiskSerialNumber(ComputerIdentifier.getDiskSerialNumber());
        requestObj.setComputerName(ComputerIdentifier.getComputerName());
        requestObj.setVersion(AppUtil.getAppVersion());
        
        return requestObj;
    }
    
    public static RequestObj createBaseObj() {
        RequestObj requestObj = new RequestObj();
        requestObj.setDiskSerialNumber(ComputerIdentifier.getDiskSerialNumber());
        requestObj.setComputerName(ComputerIdentifier.getComputerName());
        requestObj.setVersion(AppUtil.getAppVersion());
        
        return requestObj;
    }
    
//    public static SendInfoReq createSendUserInfoReq(Object data, Gson gson) {
//        SendInfoReq requestObj = new SendInfoReq();
//        requestObj.setDiskSerialNumber(ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-"));
//        requestObj.setComputerName(ComputerIdentifier.getComputerName());
//        requestObj.setVersion(AppUtil.getAppVersion());
//        requestObj.setAction(Constants.TCPAction.SEND_USER_INFO);
//        
////        UserInfoWrap userInfoWrap = new UserInfoWrap();
////        userInfoWrap.setListBannedKeys(AWSUtil.listBannedKeyword);
////        userInfoWrap.setListOrders(listOrders);
//        requestObj.setData(gson.toJson(data));
//        
//        return requestObj;
//    }
//    
//    public static SendInfoReq createSendUserInfoReq(BaseStoreOrderInfo order, Gson gson) {
//        SendInfoReq requestObj = new SendInfoReq();
//        requestObj.setDiskSerialNumber(ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-"));
//        requestObj.setComputerName(ComputerIdentifier.getComputerName());
//        requestObj.setVersion(AppUtil.getAppVersion());
//        requestObj.setAction(Constants.TCPAction.SEND_USER_INFO);
//        
////        UserInfoWrap userInfoWrap = new UserInfoWrap();
////        userInfoWrap.setListBannedKeys(AWSUtil.listBannedKeyword);
////        userInfoWrap.setListOrders(listOrders);
//        requestObj.setData(gson.toJson(order));
//        
//        return requestObj;
//    }
//    
//    public static SendInfoReq createSendUserInfoReq(Gson gson) {
//        SendInfoReq requestObj = new SendInfoReq();
//        requestObj.setDiskSerialNumber(ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-"));
//        requestObj.setComputerName(ComputerIdentifier.getComputerName());
//        requestObj.setVersion(AppUtil.getAppVersion());
//        requestObj.setAction(Constants.TCPAction.SEND_USER_INFO);
//        
////        UserInfoWrap userInfoWrap = new UserInfoWrap();
////        userInfoWrap.setListBannedKeys(AWSUtil.listBannedKeyword);
////        userInfoWrap.setListOrders(listOrders);
//        requestObj.setData(gson.toJson(AWSUtil.listBannedKeyword));
//        
//        return requestObj;
//    }
    
    public static SendInfoReq createSendUserInfoReq(String typeName, String data, int hash) {
        SendInfoReq requestObj = new SendInfoReq();
        requestObj.setDiskSerialNumber(ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-"));
        requestObj.setComputerName(ComputerIdentifier.getComputerName());
        requestObj.setVersion(AppUtil.getAppVersion());
        requestObj.setAction(Constants.TCPAction.SEND_USER_INFO);
        requestObj.setTypeName(typeName);
        requestObj.setHash(hash);
        
//        UserInfoWrap userInfoWrap = new UserInfoWrap();
//        userInfoWrap.setListBannedKeys(AWSUtil.listBannedKeyword);
//        userInfoWrap.setListOrders(listOrders);
        requestObj.setData(data);
        
        return requestObj;
    }
    
    public static StringEntity createTransformCrawlDataReq(AliexScriptDetailData data) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(data);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createTransformCrawlDataReqV2(TransformAliexCrawlDataReq data) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(data);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createUpdateConfigReq(UpdateClientConfigReq data) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(data);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createTransformAliexToAmzReq(TransformAliexToAmzReq data) {
        data.diskSerialNumber = ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-");
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(data);
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }
}
