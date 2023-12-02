/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.imgur;

import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class GenerateModel {
    public String sku;
    public String name;
    public String mainImage;
    public String type;
    public String parentSku;
    public String sizeName;

    public GenerateModel(String sku, String name, String mainImage, String type, String parentSku, String sizeName) {
        this.sku = sku;
        this.name = name;
        this.mainImage = mainImage;
        this.type = type;
        this.parentSku = parentSku;
        this.sizeName = sizeName;
    }
    
    

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentSku() {
        return parentSku;
    }

    public void setParentSku(String parentSku) {
        this.parentSku = parentSku;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
    
    public static ArrayList<GenerateModel> genListModels(int productIndex, ImgUrImage imgUrImage) {
        ArrayList<GenerateModel> result = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            result.add(
                    new GenerateModel(
                            imgUrImage.getSku(productIndex, i),
                            imgUrImage.getName(i),
                            imgUrImage.link,
                            imgUrImage.getType(i),
                            imgUrImage.getParentSku(productIndex, i),
                            imgUrImage.getSizeName(i)
                    )
            );
        }
        return result;
    }
}
