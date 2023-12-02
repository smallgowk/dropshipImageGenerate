/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.trademark;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.models.trademark.MarkerResultRes;
import com.models.trademark.MarkerTradeMarkRes;

/**
 *
 * @author duyuno
 */
public class TradeMarkParseUtil {
    public static MarkerTradeMarkRes parseMarkerTradeMarkResponse(String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, MarkerTradeMarkRes.class);
        } catch (JsonSyntaxException e) {
            if (e != null) {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    public static MarkerResultRes parseMarkerTradeMarkResultResponse(String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, MarkerResultRes.class);
        } catch (JsonSyntaxException e) {
            if (e != null) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
