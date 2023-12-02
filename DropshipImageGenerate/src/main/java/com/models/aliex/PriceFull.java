/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

import com.models.aliex.crawl.SkuPropertyValue;
import com.utils.StringUtils;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class PriceFull {
    public String id;
    public PriceUnit originalPrice;
//    public PriceUnit discountedPrice;
//    public PriceUnit bulkPrice;
    
    public String skuImage;
    
    public ArrayList<PropertyFull> properties;
    
    public boolean hasShipFrom;
    public boolean hasVariation;
    public boolean shipFromUS;

    public PriceUnit getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(PriceUnit originalPrice) {
        this.originalPrice = originalPrice;
    }

//    public PriceUnit getDiscountedPrice() {
//        return discountedPrice;
//    }
//
//    public void setDiscountedPrice(PriceUnit discountedPrice) {
//        this.discountedPrice = discountedPrice;
//    }
//
//    public PriceUnit getBulkPrice() {
//        return bulkPrice;
//    }
//
//    public void setBulkPrice(PriceUnit bulkPrice) {
//        this.bulkPrice = bulkPrice;
//    }

    public ArrayList<PropertyFull> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<PropertyFull> properties) {
        this.properties = properties;
    }
    
    public float getPriceStandard() {
        String priceStr = originalPrice.getValue();
        return Float.parseFloat(priceStr);
    }
    
//    public String getSkuName(int id) {
//        if (properties == null || properties.isEmpty()) {
//            return null;
//        }
//
//        for (PropertyFull skuProperty : properties) {
//            if (skuProperty.getPropertyId() == id) {
//                return skuProperty.getSku();
//            }
//        }
//
//        return null;
//    }

    public String getVariationName(int id, int valueId) {
        if (properties == null || properties.isEmpty()) {
            return null;
        }


        return null;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }
    
    public boolean isHasVariation() {
//        return properties != null && !properties.isEmpty();
        return hasVariation;
    }
    
    public boolean isShipFromUS() {
        
//        if(properties == null || properties.isEmpty()) return false;
//        
//        for (PropertyFull propertyFull : properties) {
//            
//            if(StringUtils.isEmpty(propertyFull.getShipFromCountry())) {
//                continue;
//            }
//            if(propertyFull.isShipFromUS()) {
//                return true;
//            }
//        }
//        return false;
        return shipFromUS;
    }
    
    public boolean isHasShipFrom() {
        
//        if(properties == null || properties.isEmpty()) return false;
//        
//        for (PropertyFull propertyFull : properties) {
//            
////            if(StringUtils.isEmpty(propertyFull.getShipFromCountry())) {
////                continue;
////            }
//            if(propertyFull.isShipFrom()) {
//                return true;
//            }
//        }
//        return false;
        return hasShipFrom;
    }
    
    public void addProperty(SkuPropertyValue skuPropertyValue) {
        
        if(skuPropertyValue == null) return;
        
        if(properties == null) {
            properties = new ArrayList<>();
        }
        
        properties.add(new PropertyFull(skuPropertyValue));
    }
}
