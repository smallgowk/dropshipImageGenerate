/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.aliex.req;


/**
 *
 * @author duyuno
 */
public class GetProductVariationReq {
    public String productId;
    public String currency;
    public String locale;

    public GetProductVariationReq(String productId, String currency, String locale) {
        this.productId = productId;
        this.currency = currency;
        this.locale = locale;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    
}
