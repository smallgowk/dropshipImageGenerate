/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.dropship;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.models.response.ResponseObj;
import com.models.response.TransformAliexToAmzResponse;
import com.models.response.TransformCrawlAliexResponse;
import com.utils.StringUtils;

/**
 *
 * @author PhanDuy
 */
public class DropParseUtil {

    public static ResponseObj parseRespone(String response) {

        if (StringUtils.isEmpty(response)) {
            return null;
        }

        try {
            Gson gson = new Gson();
            return gson.fromJson(response, ResponseObj.class);
        } catch (JsonSyntaxException ex) {
            return null;
        }
    }
    
    public static TransformCrawlAliexResponse parseAliexProductFullRespone(String response) {
        if (StringUtils.isEmpty(response)) {
            return null;
        }

        try {
            Gson gson = new Gson();
            return gson.fromJson(response, TransformCrawlAliexResponse.class);
        } catch (JsonSyntaxException ex) {
            return null;
        }
    }
    
    public static TransformAliexToAmzResponse parseAliexToAmzRespone(String response) {
        if (StringUtils.isEmpty(response)) {
            return null;
        }

        try {
            Gson gson = new Gson();
            return gson.fromJson(response, TransformAliexToAmzResponse.class);
        } catch (JsonSyntaxException ex) {
            return null;
        }
    }
}
