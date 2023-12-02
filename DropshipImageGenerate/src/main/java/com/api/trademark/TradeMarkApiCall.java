/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.trademark;

import com.api.ApiBase;
import com.interfaces.CallApiInterface;

/**
 *
 * @author duyuno
 */
public class TradeMarkApiCall {
    
    public static String checkTradeMarkWord(String keyword, CallApiInterface callApiInterface) {
       return ApiBase.getInstance().sendGet(TradeMarkUrlFactory.createCheckTradeMarkWordUrl(keyword), callApiInterface);
    }
    
    public static String checkTradeMarkAllString(String keyword, int count, int limit, CallApiInterface callApiInterface) {
        return ApiBase.getInstance().sendGet(TradeMarkUrlFactory.createCheckTradeMarkAllStringUrl(keyword, count, limit), callApiInterface);
    }
    
//    public static String checkTradeMarkAllString(String keyword, int count, int limit, CallApiInterface callApiInterface) {
//        return ApiBase.sendGet(TradeMarkUrlFactory.createCheckTradeMarkAllStringUrl(keyword, count, limit), callApiInterface);
//    }
    
    
}
