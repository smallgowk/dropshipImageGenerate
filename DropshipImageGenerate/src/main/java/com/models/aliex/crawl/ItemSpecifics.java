/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

/**
 *
 * @author duyuno
 */
public class ItemSpecifics {
    public String name;
    public String value;

    public ItemSpecifics() {
        
    }
    
    public ItemSpecifics(String name, String value) {
        this.name = name != null ? name.trim() : null;
        this.value = value;
    }

    public String getName() {
        return name != null ? (name.endsWith(":") ? name : name + ":") : null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    public boolean isAvailable() {
        
        if(name == null) return false;
        
        String textCheck = name.trim().toLowerCase();
        return !textCheck.contains("brandname") && !textCheck.contains("brand name");
//        return textCheck.contains("material") 
//                || textCheck.contains("item type")
//                || textCheck.contains("itemtype")
//                || textCheck.contains("weight")
//                || textCheck.contains("size")
//                || textCheck.contains("color")
//                || textCheck.contains("audience")
//                || textCheck.contains("pattern")
//                ;
    }
    
    public boolean isBrandName() {
        if(name == null) return false;
        
        String textCheck = name.trim().toLowerCase();
        return textCheck.contains("brandname") || textCheck.contains("brand name");
    }
    
}
