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
public class GetProductFullInfoReq {

    public String productId;
    public String currency = "USD";
    public String locale = "en_US";
    public String country = "US";
    public int quantity = 1;
    public String[] components = new String[]{
        "variations",
        "shipping",
        "html_description"
    };

    public GetProductFullInfoReq(String productId) {
        this.productId = productId;
    }
}
