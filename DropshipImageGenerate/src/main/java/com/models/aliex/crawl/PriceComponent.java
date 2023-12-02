/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

import com.models.aliex.PriceUnit;
import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class PriceComponent {
    public DiscountPrice discountPrice;
    public OriginPrice origPrice;
    public ArrayList<AliexScriptSkuPrice> skuPriceList;
    
    public float getMaxPrice() {
        if (origPrice == null) return -1;
        try {
            return Float.parseFloat(origPrice.maxAmount.value);
        } catch (NumberFormatException ex) {
            return -1;
        }
//        return maxAmount.value;
    }
    
    public boolean hasSkuPriceData() {
        return skuPriceList != null && !skuPriceList.isEmpty();
    }
}
