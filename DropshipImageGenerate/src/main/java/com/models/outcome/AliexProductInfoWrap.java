/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.outcome;

import com.models.aliex.AliexProductFull;

/**
 *
 * @author PhanDuy
 */
public class AliexProductInfoWrap extends RequestObj{
    public AliexProductFull product;
    

    public AliexProductFull getData() {
        return product;
    }

    public void setData(AliexProductFull data) {
        this.product = data;
    }
}
