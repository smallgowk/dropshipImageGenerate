/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.aliex.response;

import com.models.aliex.Variation;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AliexProductVariationResponse {
    public ArrayList<Variation> variations;

    public ArrayList<Variation> getVariations() {
        return variations;
    }

    public void setVariations(ArrayList<Variation> variations) {
        this.variations = variations;
    }
}
