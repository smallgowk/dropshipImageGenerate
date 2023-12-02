/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.aliex;

import com.api.ApiBase;
import com.api.aliex.response.AliexProductFullResponse;
import com.api.aliex.response.AliexProductShipResponse;
import com.interfaces.CallApiInterface;
import com.models.aliex.AliexProductFull;
import com.utils.StringUtils;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author Admin
 */
public class AliexApiCall {
      
    public static String getProductDetail(String productId, CallApiInterface callApiInterface) {
        String url = AliexUrlFactory.createProductDetailUrl();
        StringEntity stringEntity = AliexParameterFactory.createGetProductDetailStringEntity(productId);
        
        return ApiBase.getInstance().sendPostStringEntity(url, stringEntity, AliexParameterFactory.getHeaders(), callApiInterface);
    }
    
    public static String getProductDescription(String productId, CallApiInterface callApiInterface) {
        String url = AliexUrlFactory.createGetProductDescriptionUrl();
        StringEntity stringEntity = AliexParameterFactory.createGetProductDetailStringEntity(productId);
        return ApiBase.getInstance().sendPostStringEntity(url, stringEntity, AliexParameterFactory.getHeaders(), callApiInterface);
    }
    
    public static String getProductShipping(String productId, CallApiInterface callApiInterface) {
        String url = AliexUrlFactory.createGetProductShippingUrl();
        StringEntity stringEntity = AliexParameterFactory.createGetProductShipStringEntity(productId);
        return ApiBase.getInstance().sendPostStringEntity(url, stringEntity, AliexParameterFactory.getHeaders(), callApiInterface);
    }
    
    public static String getProductVariation(String productId, CallApiInterface callApiInterface) {
        String url = AliexUrlFactory.createGetProductVariationUrl();
        StringEntity stringEntity = AliexParameterFactory.createGetProductVariationStringEntity(productId);
        return ApiBase.getInstance().sendPostStringEntity(url, stringEntity, AliexParameterFactory.getHeaders(), callApiInterface);
    }
    
    public static AliexProductFullResponse getProductFullInfo(String productId) {
        String url = AliexUrlFactory.createGetProductFullInfoUrl();
        StringEntity stringEntity = AliexParameterFactory.createGetProductFullInfoStringEntity(productId);
        String response = ApiBase.getInstance().sendPostStringEntity(url, stringEntity, AliexParameterFactory.getHeaders(), null);
        
        if(StringUtils.isEmpty(response)) {
            return null;
        } 
//        else {
//            System.out.println("Info " + productId + ": " + response);
//        }
        
        return AliexParseUtil.parseProductFullResponse(productId, response);
    }
    
    public static AliexProductShipResponse getProductShippingInfo(String productId) {
        String url = AliexUrlFactory.createGetProductShippingUrl();
        StringEntity stringEntity = AliexParameterFactory.createGetProductShipStringEntity(productId);
        String response = ApiBase.getInstance().sendPostStringEntity(url, stringEntity, AliexParameterFactory.getHeaders(), null);
        
        if(StringUtils.isEmpty(response)) {
            return null;
        } 
//        else {
//            System.out.println("Info " + productId + ": " + response);
//        }
        
        return AliexParseUtil.parseProductShipResponse(response);
    }
}
