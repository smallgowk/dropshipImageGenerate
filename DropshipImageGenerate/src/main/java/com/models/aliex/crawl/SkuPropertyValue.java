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
public class SkuPropertyValue {
    public int propertyId;
    public int propertyValueId;
    public String propertyValueDisplayName;
    public String skuPropertyName;
//    public String propertyValueName;
    public String skuPropertySendGoodsCountryCode;
    public String skuPropertyImagePath;
    public boolean showTypeColor;
    public boolean sizeProperty;
    
    public boolean isShipFromUs() {
        return skuPropertySendGoodsCountryCode != null && skuPropertySendGoodsCountryCode.equals("US");
    }

    public void setSkuPropertyName(String skuPropertyName) {
        this.skuPropertyName = skuPropertyName;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public void setShowTypeColor(boolean showTypeColor) {
        this.showTypeColor = showTypeColor;
    }

    public void setSizeProperty(boolean sizeProperty) {
        this.sizeProperty = sizeProperty;
    }
}
