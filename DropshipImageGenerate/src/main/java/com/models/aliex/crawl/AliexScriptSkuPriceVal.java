/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

import com.models.aliex.PriceUnit;

/**
 *
 * @author PhanDuy
 */
public class AliexScriptSkuPriceVal {
    
    public PriceUnit skuActivityAmount;
    public PriceUnit skuAmount;
    public long availQuantity;
    
    public boolean isAvailable() {
        return availQuantity > 0;
    }
}
