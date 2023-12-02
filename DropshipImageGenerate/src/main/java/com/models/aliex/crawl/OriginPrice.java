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
public class OriginPrice {
    public PriceUnit maxAmount;
    public PriceUnit minAmount;
    public float minPrice;
    public float maxPrice;
    
    public float getMaxPrice() {
        try {
            return Float.parseFloat(maxAmount.value);
        } catch (NumberFormatException ex) {
            return -1;
        }
//        return maxAmount.value;
    }
}
