/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.aliex.response;

import com.config.Configs;
import com.models.aliex.ShippingObj;
import java.util.ArrayList;

/**
 *
 * @author duyuno
 */
public class AliexProductShipResponse {

    private ArrayList<ShippingObj> options;

    public ArrayList<ShippingObj> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<ShippingObj> options) {
        this.options = options;
    }
    
    public float getShippingPrice() {
//        float price = -1;
//        if(Configs.isFilterEpacket) {
//            price = getEpacketPrice();
//            
//            if(price != -1) return price;
//        }
//        
//        if(Configs.isFilterAliexpress) {
//            price = getAliexStandardPrice();
//            if(price != -1) return price;
//        }
//        
//        if(Configs.isFilterDHL) {
//            price = getAliexDHLPrice();
//            if(price != -1) return price;
//        }
        
        return getMinShippingPrice();
    }
    
//    public boolean hasAliexStandardOption() {
//        if (options == null || options.isEmpty()) {
//            return false;
//        }
//        
//        for (ShippingObj shippingObj : options) {
//            if (shippingObj.getCompany().equals("AliExpress Standard Shipping")) {
//                return true;
//            }
//        }
//        
//        return false;
//    }
    
//    public boolean hasEpacketOption() {
//        
//        if (options == null || options.isEmpty()) {
//            return false;
//        }
//        
//        for (ShippingObj shippingObj : options) {
//            if (shippingObj.getCompany().equals("ePacket")) {
//                return true;
//            }
//        }
//        
//        return false;
//    }
    
    private float getMinShippingPrice() {
        if (options == null) {
            System.out.println("getMinShippingPrice null");
            return -1;
        }
        float min = -1;
        String method = "";
        for (ShippingObj shippingObj : options) {
            String value = shippingObj.getAmount().getValue();
                try {
                    method = shippingObj.getCompany();
                    float price = Float.parseFloat(value);
                    if (min == -1) {
                        min = price;
                    } else if (min > price) {
                        min = price;
                    }
                } catch (NumberFormatException ex) {
                    
                } 
        }
        System.out.println("getMinShippingPrice: " + method);
        return min;
    }

//    private float getEpacketPrice() {
//        if (options == null || options.isEmpty()) {
//            return -1;
//        }
//
//        for (ShippingObj shippingObj : options) {
//            if (shippingObj.getCompany().equals("ePacket")) {
//                String value = shippingObj.getAmount().getValue();
//
//                try {
//                    return Float.parseFloat(value);
//                } catch (NumberFormatException ex) {
//                    return -1;
//                } 
//            }
//        }
//
//        return -1;
//    }
//    
//    private float getAliexStandardPrice() {
//        if (options == null || options.isEmpty()) {
//            return -1;
//        }
//
//        for (ShippingObj shippingObj : options) {
//            if (shippingObj.getCompany().equals("AliExpress Standard Shipping")) {
//                String value = shippingObj.getAmount().getValue();
//
//                try {
//                    return Float.parseFloat(value);
//                } catch (NumberFormatException ex) {
//                    return -1;
//                } 
//            }
//        }
//
//        return -1;
//    }
//    
//    private float getAliexDHLPrice() {
//        if (options == null || options.isEmpty()) {
//            return -1;
//        }
//
//        for (ShippingObj shippingObj : options) {
//            if (shippingObj.getCompany().equals("DHL")) {
//                String value = shippingObj.getAmount().getValue();
//
//                try {
//                    return Float.parseFloat(value);
//                } catch (NumberFormatException ex) {
//                    return -1;
//                } 
//            }
//        }
//
//        return -1;
//    }
    
//    public float getShippingCost() {
//        float price = getEpacketPrice();
//        return price != -1 ? price : getAliexStandardPrice();
//    }
}
