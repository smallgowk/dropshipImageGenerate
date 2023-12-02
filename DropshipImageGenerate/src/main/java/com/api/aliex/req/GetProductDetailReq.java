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
public class GetProductDetailReq {
    public String productId;
    public String currency;

    public GetProductDetailReq(String productId, String currency) {
        this.productId = productId;
        this.currency = currency;
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
