/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.aliex;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.api.aliex.response.AliexErrorResponse;
import com.api.aliex.response.AliexProductDescriptionRes;
import com.api.aliex.response.AliexProductFullResponse;
import com.models.aliex.AliexProductDetail;
import com.models.aliex.AliexProductFull;
import com.api.aliex.response.AliexProductShipResponse;
import com.api.aliex.response.AliexProductVariationResponse;

/**
 *
 * @author Admin
 */
public class AliexParseUtil {

    public static AliexProductDetail parseProductDetailResponse(String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, AliexProductDetail.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Error parseProductDetailResponse: " + response);
//            if (e != null) {
//                e.printStackTrace();
//            }
            return null;
        }
    }
    
    public static AliexProductFullResponse parseProductFullResponse(String productId, String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, AliexProductFullResponse.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Error parseProductFullResponse " + productId + ": " + response);
            return null;
        }
    }
    
    public static AliexProductDescriptionRes parseProductDesResponse(String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, AliexProductDescriptionRes.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }
    
    public static AliexProductShipResponse parseProductShipResponse(String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, AliexProductShipResponse.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }
    
    public static AliexProductVariationResponse parseProductVariationResponse(String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, AliexProductVariationResponse.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }
   
    
    public static AliexErrorResponse parseErrorResponse(String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, AliexErrorResponse.class);
        } catch (JsonSyntaxException e) {
//            if (e != null) {
//                e.printStackTrace();
//            }
            return null;
        }
    }
}
