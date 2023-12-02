/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.income;

import com.models.aliex.AliexProductFull;

/**
 *
 * @author PhanDuy
 */
public class AliexProductInfoWrap extends IncomeObj{
    public AliexProductFull data;
    

    public AliexProductFull getData() {
        return data;
    }

    public void setData(AliexProductFull data) {
        this.data = data;
    }
    
}
