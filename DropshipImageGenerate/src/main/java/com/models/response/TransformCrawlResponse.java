package com.models.response;

import com.models.amazon.ProductAmz;
import java.util.ArrayList;

public class TransformCrawlResponse {
    public String productId;
    public ProductAmz baseAmzProduct;
    public float origiPrice;
    public float promotionRate;
    public float shippingPrice;
    public ArrayList<ProductAmz> childs;
    public String htmlDescription;
    public String htmlSpecifics;
    public boolean showReason;
    public boolean showTip;
    public BulletPointSearchTerm bulletPointSearchTerm;

    public TransformCrawlResponse() {
    }

    public ProductAmz getBaseAmzProduct() {
        return baseAmzProduct;
    }

    public void setBaseAmzProduct(ProductAmz baseAmzProduct) {
        this.baseAmzProduct = baseAmzProduct;
    }

    public float getOrigiPrice() {
        return origiPrice;
    }

    public void setOrigiPrice(float origiPrice) {
        this.origiPrice = origiPrice;
    }

    public float getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(float promotionRate) {
        this.promotionRate = promotionRate;
    }

    public float getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(float shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public ArrayList<ProductAmz> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<ProductAmz> childs) {
        this.childs = childs;
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    public String getHtmlSpecifics() {
        return htmlSpecifics;
    }

    public void setHtmlSpecifics(String htmlSpecifics) {
        this.htmlSpecifics = htmlSpecifics;
    }

    public boolean isShowReason() {
        return showReason;
    }

    public void setShowReason(boolean showReason) {
        this.showReason = showReason;
    }

    public boolean isShowTip() {
        return showTip;
    }

    public void setShowTip(boolean showTip) {
        this.showTip = showTip;
    }

    public BulletPointSearchTerm getBulletPointSearchTerm() {
        return bulletPointSearchTerm;
    }

    public void setBulletPointSearchTerm(BulletPointSearchTerm bulletPointSearchTerm) {
        this.bulletPointSearchTerm = bulletPointSearchTerm;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
