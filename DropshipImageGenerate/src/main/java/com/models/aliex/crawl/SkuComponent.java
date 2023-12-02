/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class SkuComponent {
    public ArrayList<AliexScriptSkuProperty> productSKUPropertyList;
    
    public boolean hasData() {
        return productSKUPropertyList != null && !productSKUPropertyList.isEmpty();
    }
    
    public boolean isHasShipFrom() {
        if(productSKUPropertyList == null || productSKUPropertyList.isEmpty()) return false;
        
        for(AliexScriptSkuProperty aliexScriptSku : productSKUPropertyList) {
            if(aliexScriptSku.isShipFrom()) return true;
        }
        
        return false;
    }
    
    public boolean isHasShipFromUS() {
        if(productSKUPropertyList == null || productSKUPropertyList.isEmpty()) return false;
        
        for(AliexScriptSkuProperty aliexScriptSku : productSKUPropertyList) {
            if(aliexScriptSku.isHasShipFromUS()) return true;
        }
        
        return false;
    }
}
