/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.trademark;

import java.util.regex.Pattern;

/**
 *
 * @author duyuno
 */
public class MarkerTradeMarkObj {
    
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    
    private String serialnumber;
    private String wordmark;
    private String code;
    private String description;
    private String registrationdate;

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }
    
    public int getWordCount() {
        if(wordmark == null || wordmark.isEmpty()) return 0;
        
        return wordmark.split(Pattern.quote(" ")).length;
    }

    public String getWordmark() {
        return wordmark;
    }

    public void setWordmark(String wordmark) {
        this.wordmark = wordmark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(String registrationdate) {
        this.registrationdate = registrationdate;
    }
    
    public String getInfos() {
        return "WordMark: " + wordmark + "\nDescription: \n" + description;
    }
}
