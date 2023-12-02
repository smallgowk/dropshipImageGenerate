/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;


/**
 *
 * @author PhanDuy
 */
public class AliexScriptSkuPrice {
    public String skuPropIds;
//    public String freightExt;
    public AliexScriptSkuPriceVal skuVal;
    
    public boolean isAvailable() {
//        return freightExt.contains("scItemId") && skuVal.isAvailable();
        return skuVal.isAvailable();
    }
    
    
}
