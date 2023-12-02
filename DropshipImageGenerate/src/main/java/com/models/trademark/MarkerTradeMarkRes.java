/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.trademark;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author duyuno
 */
public class MarkerTradeMarkRes {

    private int count;
//    private ArrayList<MarkerTradeMarkObj> trademarks;
    private ArrayList<MarkerTradeMarkObj> trademarks;
    private HashMap<String, ArrayList<MarkerTradeMarkObj>> hashMapTradeMark;
    private HashMap<Integer, String> hashMapTradeMarkByCount;

    private String alertMessage;
    private String keyword;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<MarkerTradeMarkObj> getTrademarks() {
        return trademarks;
    }
    
    public void addTradeMarks(ArrayList<MarkerTradeMarkObj> datas) {
        if(trademarks == null) {
            trademarks = new ArrayList<>();
        }
        
        trademarks.addAll(datas);
        
        count = trademarks.size();
    }
    

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String type, String keyword) {
        this.keyword = keyword;
        StringBuilder sb = new StringBuilder();
//        sb.append("<html>");
//        sb.append("<p>").append("The ").append(type).append(" has some word mark registration!").append("</p>").append("</br>");
        
        String wordmarkStr = "";
        ArrayList<String> listWordMark = new ArrayList<>();
        int maxWordCount = 1;

        HashMap<Integer, ArrayList<String>> hashMapTradeMarkByCount = new HashMap<>();
        hashMapTradeMark = new HashMap<>();

        for (MarkerTradeMarkObj markObj : trademarks) {
            int wordCount = markObj.getWordCount();

            if (maxWordCount < wordCount) {
                maxWordCount = wordCount;
            }

            ArrayList<String> listString = hashMapTradeMarkByCount.get(markObj.getWordCount());

            if (listString == null) {
                listString = new ArrayList<>();
                listString.add(markObj.getWordmark().toLowerCase());
                hashMapTradeMarkByCount.put(wordCount, listString);
            } else {
                listString.add(markObj.getWordmark().toLowerCase());
            }
            
            ArrayList<MarkerTradeMarkObj> listMarks = hashMapTradeMark.get(markObj.getWordmark().toLowerCase());
            
            if(listMarks == null) {
                listMarks = new ArrayList<>();
                listMarks.add(markObj);
                hashMapTradeMark.put(markObj.getWordmark().toLowerCase(), listMarks);
            } else {
                listMarks.add(markObj);
            }

            
        }

        for (int i = maxWordCount; i > 0; i--) {
            ArrayList<String> listString = hashMapTradeMarkByCount.get(i);
            for (String s : listString) {
                if (!wordmarkStr.contains(s)) {
                    wordmarkStr += s + ",";
                    listWordMark.add(s);
                }
            }
        }
        
        String keyWordHtml = keyword.toLowerCase();
        
        for(String s : listWordMark) {
            keyWordHtml = keyWordHtml.replaceAll(s, "<b> <font size=\"5\">" + s + "</font></b>");
        }
        sb.append("<font style=\"font-family:monospaced;\" size=\"3\">");
        sb.append(keyWordHtml);
        sb.append("</font>");
//        sb.append("</html>");

        this.alertMessage = sb.toString();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setTrademarks(ArrayList<MarkerTradeMarkObj> trademarks) {
        this.trademarks = trademarks;
    }

    public String getAllInfo() {

        if (trademarks == null || trademarks.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (MarkerTradeMarkObj markerTradeMarkObj : trademarks) {
//            if (sb.length() == 0) {
//                sb.append("=================").append("</br>");
//            }
//
//            sb.append("WordMark: ").append(markerTradeMarkObj.getWordmark()).append("\"</br>\"");
//            sb.append("RegistrationDate: ").append(markerTradeMarkObj.getRegistrationdate()).append("\"</br>\"");
//            sb.append("Description: ").append("</br>").append(markerTradeMarkObj.getDescription()).append("</br>");
//            sb.append("=================").append("</br>");

            if (sb.length() == 0) {
                sb.append("=================").append("\n");
            }

            sb.append("WordMark:  ").append(markerTradeMarkObj.getWordmark()).append("\n");
            sb.append("RegistrationDate:  ").append(markerTradeMarkObj.getRegistrationdate()).append("\n");
            sb.append("Description: ").append("\n").append(markerTradeMarkObj.getDescription()).append("\n");
            sb.append("=================").append("\n");
        }

        return sb.toString();
    }

}
