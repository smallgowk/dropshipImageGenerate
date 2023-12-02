/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.aliex;

import com.config.AuthenConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.api.aliex.req.GetProductDetailReq;
import com.api.aliex.req.GetProductFullInfoReq;
import com.api.aliex.req.GetProductShippingReq;
import com.api.aliex.req.GetProductVariationReq;
import com.config.Configs;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author Admin
 */
public class AliexParameterFactory {

    public static final String X_API_CLIENT_ID_FIELD_NAME = "X-API-CLIENT-ID";
//    public static final String DAFAULT_KEY = "SEHRTRFKOWVKHUST"; // 10000
//    public static String DAFAULT_KEY = "JBIWPFKHUQHMSBRN"; // 10000
//    public static final String DAFAULT_KEY = "ATLTKDPCCEBNINYT"; // 200

//    public static int currentKeyIndex = 0;

//    public static ArrayList<String> listKeys;

    public static String[][] headers;

    public static StringEntity createGetProductDetailStringEntity(String productId) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        GetProductDetailReq getProductDetailReq = createGetProductDetailReq(productId);
        String urlParameters = gson.toJson(getProductDetailReq, GetProductDetailReq.class);
        return new StringEntity(urlParameters, ContentType.APPLICATION_JSON);
    }

    public static StringEntity createGetProductShipStringEntity(String productId) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        GetProductShippingReq getProductShippingReq = createGetProductShippingReq(productId);
        String urlParameters = gson.toJson(getProductShippingReq, GetProductShippingReq.class);
        return new StringEntity(urlParameters, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createGetProductVariationStringEntity(String productId) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        GetProductVariationReq getProductShippingReq = createGetProductVariationReq(productId);
        String urlParameters = gson.toJson(getProductShippingReq, GetProductVariationReq.class);
        return new StringEntity(urlParameters, ContentType.APPLICATION_JSON);
    }
    
    public static StringEntity createGetProductFullInfoStringEntity(String productId) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        GetProductFullInfoReq getProductFullInfoReq = createGetProductFullInfoReq(productId);
        String urlParameters = gson.toJson(getProductFullInfoReq, GetProductFullInfoReq.class);
        return new StringEntity(urlParameters, ContentType.APPLICATION_JSON);
    }

    public static GetProductDetailReq createGetProductDetailReq(String productId) {
        return new GetProductDetailReq(productId, "USD");
    }

    public static GetProductShippingReq createGetProductShippingReq(String productId) {
        return new GetProductShippingReq(productId, "USD", 1);
    }
    
    public static GetProductVariationReq createGetProductVariationReq(String productId) {
        return new GetProductVariationReq(productId, "USD", "en_US");
    }
    
    public static GetProductFullInfoReq createGetProductFullInfoReq(String productId) {
        return new GetProductFullInfoReq(productId);
    }

    public static String[][] getHeaders() {
        if (headers != null) {
            return headers;
        }

        headers = createHeaders();
        return headers;
    }

    public static String[][] createHeaders() {

        String[][] headers = new String[2][2];
        headers[0][0] = X_API_CLIENT_ID_FIELD_NAME;
        headers[0][1] = AuthenConfig.apiKey;
        headers[1][0] = "Content-Type";
        headers[1][1] = "application/json";
        return headers;
    }

}
