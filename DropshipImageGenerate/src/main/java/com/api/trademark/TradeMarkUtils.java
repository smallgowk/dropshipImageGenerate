/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.trademark;

import com.interfaces.ProgressInterface;
import com.models.trademark.MarkerResultRes;
import com.models.trademark.MarkerTradeMarkRes;

/**
 *
 * @author duyuno
 */
public class TradeMarkUtils {
    public static boolean checkIsTradeMark(String key) {
        String checkRes = TradeMarkApiCall.checkTradeMarkWord(key, null);
        
        MarkerResultRes markerResultRes = TradeMarkParseUtil.parseMarkerTradeMarkResultResponse(checkRes);
        
        return markerResultRes != null && markerResultRes.getCount() > 1;
    }
    
    public static MarkerTradeMarkRes getTradeMarkInfos(String keywords, ProgressInterface progressInterface) {
        
        MarkerTradeMarkRes markerTradeMarkRes = null;
        
        String[] parts = keywords.split(" ");
//        int wordIndex = 0;
//        int wordCount = 0;

//        progressInterface.startProcess(parts.length);
        
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0, length = parts.length; i < length; i++) {
            sb.append(parts[i].trim()).append(" ");
            if(i % 20 == 19 || i == length - 1) {
                String checkWords = sb.toString().trim();
                
                System.out.println("Checking: " + checkWords);
                
                
                String checkRes = TradeMarkApiCall.checkTradeMarkAllString(checkWords, 1, 100, null);
                
                if(checkRes == null || checkRes.isEmpty() || !checkRes.startsWith("{")) {
                    progressInterface.onUpdateProcess("Error with: " + checkWords,i);
                    continue;
                }
                
                MarkerResultRes markerResultRes = TradeMarkParseUtil.parseMarkerTradeMarkResultResponse(checkRes);
                if (markerResultRes != null && markerResultRes.getCount() > 0) {
                    MarkerTradeMarkRes mtr = TradeMarkParseUtil.parseMarkerTradeMarkResponse(checkRes);
                    
                    if(markerTradeMarkRes == null) {
                        markerTradeMarkRes = mtr;
                    } else {
                        markerTradeMarkRes.addTradeMarks(mtr.getTrademarks());
                    }
                }
                
//                progressInterface.onUpdateProcess("Checked: " + checkWords, i);
                sb = new StringBuilder();
            }
        }
        
//        progressInterface.onFinishProcess();
        
        return markerTradeMarkRes;
    }
}
