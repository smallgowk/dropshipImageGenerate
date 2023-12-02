
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.dropship;

import com.api.ApiBase;
import com.api.dropship.req.SendLogInfoReq;
import com.api.dropship.req.TransformAliexToAmzReq;
import com.api.dropship.req.UpdateClientConfigReq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.interfaces.CallApiInterface;
import com.models.aliex.crawl.AliexScriptDetailData;
import com.controller.transform.TransformToServer;
import com.models.response.ResponseObj;
import com.models.response.TransformAliexToAmzResponse;
import com.models.response.TransformCrawlAliexResponse;
import com.models.request.TransformAliexCrawlDataReq;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author PhanDuy
 */
public class DropApiCall {
    
    
//    public static ResponseObj doSendStoreInfo(AliexStoreInfoWrap aliexStoreInfoWrap, CallApiInterface callApiInterface) {
//        String url = DropUrlFactory.createDropUrl();
//        StringEntity stringEntity = DropParameterFactory.createSendStoreInfoEntity(aliexStoreInfoWrap);
//        String response = ApiBase.getInstance().sendPostStringEntity(url, stringEntity, null, callApiInterface);
//        return DropParseUtil.parseRespone(response);
//    }
    
//    public static ResponseObj doSendPageInfo(AliexStorePageWrap aliexStorePageWrap, CallApiInterface callApiInterface) {
//        String url = DropUrlFactory.createDropUrl();
//        StringEntity stringEntity = DropParameterFactory.createSendPageInfoEntity(aliexStorePageWrap);
//        String response =  ApiBase.getInstance().sendPostStringEntity(url, stringEntity, null, callApiInterface);
//        return DropParseUtil.parseRespone(response);
//    }
    
//    public static ResponseObj doSendProductInfo(AliexProductInfoWrap aliexProductInfoWrap, CallApiInterface callApiInterface) {
//        String url = DropUrlFactory.createDropUrl();
//        StringEntity stringEntity = DropParameterFactory.createSendProductInfoEntity(aliexProductInfoWrap);
//        String response =  ApiBase.getInstance().sendPostStringEntity(url, stringEntity, null, callApiInterface);
//        return DropParseUtil.parseRespone(response);
//    }
    
    public static ResponseObj doSendGetInfo(CallApiInterface callApiInterface) {
        String url = DropUrlFactory.createAuthenUrl();
        StringEntity stringEntity = DropParameterFactory.createGetAccountInfoReq(TransformToServer.getInstance().getAccountInfoWrap());
        return ApiBase.getInstance().sendPostStringEntityIgnoreError(url, stringEntity, null, callApiInterface);
    }
    
    public static TransformCrawlAliexResponse doTransformCrawlData(AliexScriptDetailData data, CallApiInterface callApiInterface) {
        String url = DropUrlFactory.createTransformCrawlDataUrl();
        StringEntity stringEntity = DropParameterFactory.createTransformCrawlDataReq(data);
        return (TransformCrawlAliexResponse)ApiBase.getInstance().sendPostStringEntityIgnoreErrorWithBaseResponse(url, stringEntity, null, DropParseUtil::parseAliexProductFullRespone);
    }
    
    public static TransformCrawlAliexResponse doTransformCrawlDataV2(TransformAliexCrawlDataReq data, CallApiInterface callApiInterface) {
        String url = DropUrlFactory.createTransformCrawlDataUrlV2();
        StringEntity stringEntity = DropParameterFactory.createTransformCrawlDataReqV2(data);
        return (TransformCrawlAliexResponse)ApiBase.getInstance().sendPostStringEntityIgnoreErrorWithBaseResponse(url, stringEntity, null, DropParseUtil::parseAliexProductFullRespone);
    }
    
    public static ResponseObj doUpdateClientConfig(UpdateClientConfigReq data, CallApiInterface callApiInterface) {
        String url = DropUrlFactory.createUpdateConfigUrl();
        StringEntity stringEntity = DropParameterFactory.createUpdateConfigReq(data);
        return ApiBase.getInstance().sendPostStringEntityIgnoreError(url, stringEntity, null, callApiInterface);
    }
    
    public static TransformAliexToAmzResponse doTransformAliexToAmz(TransformAliexToAmzReq data, CallApiInterface callApiInterface) {
        String url = DropUrlFactory.createTransformAliexToAmzUrl();
        StringEntity stringEntity = DropParameterFactory.createTransformAliexToAmzReq(data);
        return (TransformAliexToAmzResponse)ApiBase.getInstance().sendPostStringEntityIgnoreErrorWithBaseResponse(url, stringEntity, null, DropParseUtil::parseAliexToAmzRespone);
    }

    public static ResponseObj doSendLogInfo(SendLogInfoReq req, CallApiInterface callApiInterface) {
        String url = DropUrlFactory.createLogUrl();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(req);
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        return ApiBase.getInstance().sendPostStringEntityIgnoreError(url, stringEntity, null, callApiInterface);
    }
    
    public static ResponseObj doSendGetCert(CallApiInterface callApiInterface) {
        String url = DropUrlFactory.createAuthenUrl();
        StringEntity stringEntity = DropParameterFactory.createGetAccountInfoReq(TransformToServer.getInstance().getCertWrap());
        String response =  ApiBase.getInstance().sendPostStringEntity(url, stringEntity, null, callApiInterface);
        return DropParseUtil.parseRespone(response);
    }
    
    public static void doSendUserInfo(String type, String data, int hash, CallApiInterface callApiInterface) {
        String url = DropUrlFactory.createAuthenUrl();
        StringEntity stringEntity = DropParameterFactory.createSendUserInfoEntity(type, data, hash);
        ApiBase.getInstance().sendPostStringEntity(url, stringEntity, null, callApiInterface);
    }
    
//    public static ResponseObj doSendUserInfo(BaseStoreOrderInfo listOrders, CallApiInterface callApiInterface) {
//        String url = DropUrlFactory.createAuthenUrl();
//        StringEntity stringEntity = DropParameterFactory.createSendUserInfoEntity(listOrders);
//        String response =  ApiBase.getInstance().sendPostStringEntity(url, stringEntity, null, callApiInterface);
//        return DropParseUtil.parseRespone(response);
//    }
    
    public static void doSendUpdateInfo(String message, CallApiInterface callApiInterface) {
        String url = DropUrlFactory.createAuthenUrl();
        StringEntity stringEntity = DropParameterFactory.createGetAccountInfoReq(TransformToServer.getInstance().updateInfoWrap(message));
        ApiBase.getInstance().sendPostStringEntity(url, stringEntity, null, callApiInterface);
    }
}
