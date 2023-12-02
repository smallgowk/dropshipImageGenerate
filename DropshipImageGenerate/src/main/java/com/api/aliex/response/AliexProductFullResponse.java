/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.aliex.response;

import com.models.aliex.*;
import com.utils.MarketUtil;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class AliexProductFullResponse {

    public String id;
    public String categoryId;
    public String title;
    public ArrayList<String> productImages;
    public ArrayList<Promotion> promotions;
    public Promotion promotion;
    public ArrayList<ProductAttribute> attributes;
    public ArrayList<PriceFull> prices;
    public String htmlDescription;
    private ArrayList<ShippingObj> shipping; 
    
    public int pageIndex;
    public String storeSign;
    public ArrayList<String> listSearchTerms;

    public String getStoreSign() {
        return storeSign;
    }

    public void setStoreSign(String storeSign) {
        this.storeSign = storeSign;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(ArrayList<String> productImages) {
        this.productImages = productImages;
    }

    public ArrayList<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(ArrayList<Promotion> promotions) {
        this.promotions = promotions;
    }

    public ArrayList<ProductAttribute> getAttributes() {
        return attributes;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    
    
    public void setAttributes(ArrayList<ProductAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getAttributeValue(String attribute) {
        if (attributes == null || attributes.isEmpty()) {
            return null;
        }

        for (ProductAttribute productAttribute : attributes) {
            if (productAttribute.getName().equals(attribute)) {
                return productAttribute.getValue();
            }
        }

        return null;
    }

    public float getPromotionRate() {
        if(promotions == null || promotions.isEmpty()) {
            return 0;
        }
        
        String promotionDiscount = promotions.get(0).discount;
        
        return Float.parseFloat(promotionDiscount) / 100;
        
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public ArrayList<PriceFull> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<PriceFull> prices) {
        this.prices = prices;
    }

    public ArrayList<String> getListSearchTerms() {
        return listSearchTerms;
    }

    public void setListSearchTerms(ArrayList<String> listSearchTerms) {
        this.listSearchTerms = listSearchTerms;
    }

    

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    public ArrayList<ShippingObj> getShipping() {
        return shipping;
    }

    public void setShipping(ArrayList<ShippingObj> shipping) {
        this.shipping = shipping;
    }
    
    public String getBranName() {
        if(attributes == null || attributes.isEmpty()) return null;
        
        for(ProductAttribute productAttribute : attributes) {
            if(productAttribute.isBrandName()) {
                return productAttribute.getValue();
            }
        }
        
        return null;
    }
    
    public void addBrandName(String brandName) {
        if(attributes == null) {
            attributes = new ArrayList<>();
        }
        
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setName("Brand Name");
        productAttribute.setValue(brandName);
        attributes.add(productAttribute);
    }
    
    public void repairVariationImage(HashMap<String, String> hashmapProperties) {
        
        if(prices == null || prices.isEmpty() || hashmapProperties == null) return;
        
        for(PriceFull priceFull : prices) {
            if(priceFull.getProperties() != null) {
                for(PropertyFull propertyFull : priceFull.getProperties()) {
                    if(hashmapProperties.containsKey("" + propertyFull.getValueId())) {
                        priceFull.setSkuImage(MarketUtil.getVariationImageUrl(hashmapProperties.get("" + propertyFull.getValueId())));
                    } else if(propertyFull.getValueDisplayName() != null && hashmapProperties.containsKey("" + propertyFull.getValueDisplayName().toLowerCase())){
                        priceFull.setSkuImage(MarketUtil.getVariationImageUrl(hashmapProperties.get("" + propertyFull.getValueDisplayName().toLowerCase())));
                    }
                }
            }
        }
    }
    
    public float getShippingPrice() {
        float price = getEpacketPrice();
        return price != -1 ? price : getAliexStandardPrice();
    }

    public float getEpacketPrice() {
        if (shipping == null || shipping.isEmpty()) {
            return -1;
        }

        for (ShippingObj shippingObj : shipping) {
            if (shippingObj.getCompany().equals("ePacket")) {
                String value = shippingObj.getAmount().getValue();

                try {
                    return Float.parseFloat(value);
                } catch (NumberFormatException ex) {
                    return -1;
                }
            }
        }

        return -1;
    }

    private float getAliexStandardPrice() {
        if (shipping == null || shipping.isEmpty()) {
            return -1;
        }

        for (ShippingObj shippingObj : shipping) {
            if (shippingObj.getCompany().equals("AliExpress Standard Shipping")) {
                String value = shippingObj.getAmount().getValue();

                try {
                    return Float.parseFloat(value);
                } catch (NumberFormatException ex) {
                    return -1;
                }
            }
        }

        return -1;
    }
    
    public boolean isHasVarition() {
        if (prices == null || prices.isEmpty()) {
            return false;
        }

        if (prices.size() == 1) {
            PriceFull priceFull = prices.get(0);
            return priceFull.isHasVariation();
        }

        for (PriceFull priceFull : prices) {
            if (priceFull.isHasVariation()) {
                return true;
            }
        }

        return false;
    }
    
    public float getFirstPrice() {
        if (prices == null || prices.isEmpty()) {
            return -1;
        }

        String priceStr = prices.get(0).getOriginalPrice().getValue();

        return Float.parseFloat(priceStr);
    }
    
    public boolean isHasShipFrom() {
        for(PriceFull priceFull : prices) {
            if(priceFull.isHasVariation()) {
                if(priceFull.isHasShipFrom()) {
                    return true;
                }
            }
        }
        
        return false;
    }

    public boolean isHasShipFromUS() {
        
        for(PriceFull priceFull : prices) {
            if(priceFull.isHasVariation()) {
                if(priceFull.isShipFromUS()) {
                    return true;
                }
            }
        }

//        ArrayList<Variation> listVariation = aliexProductFull.getVariation();
//
//        if (listVariation == null || listVariation.isEmpty()) {
//            return null;
//        }
//
//        boolean isHasShipFromUS = false;
//
//        for (Variation variation : listVariation) {
//            if (variation.getPropertyIdentifiers() == null || variation.getPropertyIdentifiers().isEmpty()) {
//                continue;
//            }
//
//            for (VariationProperty variationProperty : variation.getPropertyIdentifiers()) {
//                int variId = variationProperty.getPropertyId();
//                if (aliexOriginalInfo.getDetail().isShipFrom(variationProperty.getPropertyId())) {
//                    String variationName = aliexOriginalInfo.getDetail().getVariationName(variId, variationProperty.getPropertyValueId());
//                    if (variationName == null || variationName.isEmpty()) {
//                        continue;
//                    }
//
//                    if (variationName.toLowerCase().contains("united states") || variationName.toLowerCase().contains("us")) {
//                        isHasShipFromUS = true;
//                        break;
//                    }
//
//                }
//            }
//
//            if (isHasShipFromUS) {
//                break;
//            }
//        }
//
//        if (!isHasShipFromUS) {
//            return null;
//        }

        return false;
    }
    
    public float getProductPrice(float price, float priceRate) {
        float promotionRate = getPromotionRate();
//        return (price * (1 - promotionRate) + getShippingPrice()) * (priceRate - promotionRate);
        return (price * (1 - promotionRate) + getShippingPrice()) * (priceRate);
    }
}
