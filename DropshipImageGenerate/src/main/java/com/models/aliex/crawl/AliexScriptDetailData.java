/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

import com.config.Configs;
import com.models.request.ImagePathModel;
import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class AliexScriptDetailData extends CrawlDataBase{
    public ProductInfoComponent productInfoComponent;
    public ProductDescComponent productDescComponent;
    public ImageComponent imageComponent;
    public PriceComponent priceComponent;
    public PromotionComponent promotionComponent;
    public ProductPropComponent productPropComponent;
    public SkuComponent skuComponent;
    public ShopCategoryComponent shopCategoryComponent;
    public WebGeneralFreightCalculateComponent webGeneralFreightCalculateComponent;
    public float shippingPrice = -1;
    public String shippingMethod;
    public boolean isOnlyUs;
    public int filterAliexpress;
    public int filterEpacket;
    public int filterAliDirect;

    public AliexScriptDetailData() {
    }
    
    
    
    public void updateConfig() {
        this.isOnlyUs = Configs.isOnlyUS;
        this.filterAliexpress = Configs.filterAliexpress;
        this.filterEpacket = Configs.filterEpacket;
        this.filterAliDirect = Configs.filterAliDirect;
    }
    
    public ArrayList<String> getProductImages() {
        if (imageComponent != null) {
            return imageComponent.imagePathList;
        }
        return null;
    }
    
    public float getPromotionRate() {
        if (promotionComponent == null) return 0f;
        return promotionComponent.discount * 1f / 100;
    }
    
    public float getShippingPrice() {
        return shippingPrice;
    }
    
    public boolean isHasShipFrom() {
        if (skuComponent == null) return false;
        return skuComponent.isHasShipFrom();
    }
    
    public boolean isHasShipFromUS() {
        if (skuComponent == null) return false;
        return skuComponent.isHasShipFrom();
    }
    
    public ArrayList<String> getRelatedSearch() {
        if (shopCategoryComponent == null || shopCategoryComponent.productGroupsResult == null) return null;
        return shopCategoryComponent.productGroupsResult.getRelatedSearch();
    }
    
    public void copy(AliexScriptDetailData data) {
        this.productInfoComponent = data.productInfoComponent;
        this.productDescComponent = data.productDescComponent;
        this.imageComponent = data.imageComponent;
        this.priceComponent = data.priceComponent;
        this.promotionComponent = data.promotionComponent;
        this.productPropComponent = data.productPropComponent;
        this.skuComponent = data.skuComponent;
        this.shopCategoryComponent = data.shopCategoryComponent;
        this.webGeneralFreightCalculateComponent = data.webGeneralFreightCalculateComponent;
    }
    
    public float getFirstPrice() {
        if (priceComponent == null) return 0f;
        return priceComponent.getMaxPrice();
    }
    
    public boolean hasShippingModuleInfo() {
        return webGeneralFreightCalculateComponent != null &&
                webGeneralFreightCalculateComponent.originalLayoutResultList != null &&
                !webGeneralFreightCalculateComponent.originalLayoutResultList.isEmpty();
    }
    
    public void buildShippingPrice() {
        if (hasShippingModuleInfo()) {
            BizData bizData = webGeneralFreightCalculateComponent.getBizData();
            if (bizData != null) {
                shippingPrice = bizData.getShippingPrice();
                shippingMethod = bizData.company;
            } else {
                shippingPrice = -1;
                shippingMethod = null;
            }
        }
    }
    
    public ArrayList<ImagePathModel> toImagePaths() {
        ArrayList<ImagePathModel> imagePathModels = new ArrayList<>();
        for(int i = 0, size = imageComponent.imagePathList.size(); i < size; i++) {
            imagePathModels.add(new ImagePathModel(i, imageComponent.imagePathList.get(i)));
        }
        imageComponent.imagePathList.clear();
        imageComponent = null;
        return imagePathModels;
    }
}