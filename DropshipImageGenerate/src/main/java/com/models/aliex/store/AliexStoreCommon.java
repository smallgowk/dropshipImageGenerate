/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.store;

import com.config.Configs;
import com.utils.MarketUtil;
import com.models.aliex.AliexOriginalInfo;
import com.models.aliex.AliexProductDetail;
import com.models.aliex.ProductAttribute;
import com.models.aliex.Variation;
import com.models.aliex.VariationProperty;
import com.models.amazon.BTGNode;
import com.models.amazon.BrowseTreeManager;
import com.models.amazon.ProductAmz;
import com.models.aliex.crawl.ItemSpecifics;
import com.utils.AWSUtil;
import com.utils.CheckingDataUtil;
import com.utils.FuncUtil;
import com.utils.StringUtils;
import com.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Whitelist;

/**
 *
 * @author duyuno
 */
public class AliexStoreCommon {

    public String storeId;
    public String categoryId;
    public String searchKey;
    public String brandName;
    public String productType;
    public String accNo;
    public BTGNode btgNode;
    public String categoryPath;
    public String mainKey;
    public String marketName;
    public ArrayList<String> listKeyWords;
    public String tips;
    public ArrayList<String> listBulletPoints;
    public String reasons;
    public String descriptionForm;
    public String url;
    public boolean isCheckBannedWords;
    public boolean isOnlyUS;
    public float priceRate;
    public float priceLimit;
    public int totalPage;
    public int sendEmailMode;
    public String btgTempleFile;
    public String variationThemeBoth;
    public String variationThemeColor;
    public String variationThemeSize;
    public String colorMap;
    public String sizeMap;
    public String storeSign;
    public ArrayList<String> fullAudienceTarget;
    
    public String getStoreId() {
        return storeId;
    }
    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getBtgTempleFile() {
        return btgTempleFile;
    }

    public void setBtgTempleFile(String btgTempleFile) {
        this.btgTempleFile = btgTempleFile;
    }

    public ArrayList<String> getFullAudienceTarget() {
        return fullAudienceTarget;
    }

    public void setFullAudienceTarget(ArrayList<String> fullAudienceTarget) {
        this.fullAudienceTarget = fullAudienceTarget;
    }
    
    

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    public String getVariationThemeBoth() {
        return variationThemeBoth;
    }

    public void setVariationThemeBoth(String variationThemeBoth) {
        this.variationThemeBoth = variationThemeBoth;
    }

    public String getVariationThemeColor() {
        return variationThemeColor;
    }

    public void setVariationThemeColor(String variationThemeColor) {
        this.variationThemeColor = variationThemeColor;
    }

    public String getVariationThemeSize() {
        return variationThemeSize;
    }

    public void setVariationThemeSize(String variationThemeSize) {
        this.variationThemeSize = variationThemeSize;
    }

    public String getColorMap() {
        return colorMap;
    }

    public void setColorMap(String colorMap) {
        this.colorMap = colorMap;
    }

    public String getSizeMap() {
        return sizeMap;
    }

    public void setSizeMap(String sizeMap) {
        this.sizeMap = sizeMap;
    }

    public String getStoreSign() {
        return storeSign;
    }

    public void setStoreSign(String storeSign) {
        this.storeSign = storeSign;
    }
    
    public void initBTG() {
        String[] btgParts = categoryPath.split(Pattern.quote("/"));
        btgNode = BrowseTreeManager.getBTGNode(btgParts[btgParts.length - 1].trim());
    }
    
    
    public String getTempleFile() {
        if (btgNode == null) {
            return "Home.xlsx";
        }
        return btgNode.getTemplateFile();
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public ArrayList<String> getListKeyWords() {
        return listKeyWords;
    }

    public int getSendEmailMode() {
        return sendEmailMode;
    }

    public void setSendEmailMode(int sendEmailMode) {
        this.sendEmailMode = sendEmailMode;
    }

    public void setListKeyWords(ArrayList<String> listKeyWords) {
        this.listKeyWords = listKeyWords;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public float getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(float priceRate) {
        this.priceRate = priceRate;
    }

    public float getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(float priceLimit) {
        this.priceLimit = priceLimit;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BTGNode getBtgNode() {
        return btgNode;
    }

    public void setBtgNode(BTGNode btgNode) {
        this.btgNode = btgNode;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public ArrayList<String> getListBulletPoints() {
        return listBulletPoints;
    }

    public void setListBulletPoints(ArrayList<String> listBulletPoints) {
        this.listBulletPoints = listBulletPoints;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getDescriptionForm() {
        return descriptionForm;
    }

    public void setDescriptionForm(String descriptionForm) {
        this.descriptionForm = descriptionForm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public ArrayList<AliexPageInfo> getListPages() {
//        return listPages;
//    }
//
//    public void setListPages(ArrayList<AliexPageInfo> listPages) {
//        this.listPages = listPages;
//    }
//    
//    public void addAliexPageInfo(AliexPageInfo aliexPageInfo) {
//        if(listPages == null) {
//            listPages = new ArrayList<>();
//        }
//        
//        listPages.add(aliexPageInfo);
//    }
    public String getCacheFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(storeId);

        if (!StringUtils.isEmpty(categoryId)) {
            sb.append("_").append(categoryId);
        }

        if (!StringUtils.isEmpty(searchKey)) {
            sb.append("_").append(searchKey);
        }

        return sb.toString();
    }

    public String prefix;

    public String getPrefix() {

        if (prefix != null) {
            return prefix;
        }

        String brandBrief = StringUtils.getStringBrief(brandName);
        if (brandBrief.length() > 2) {
            brandBrief = brandBrief.substring(0, 2);
        }
        prefix = brandBrief.toUpperCase();
        return prefix;
    }

//    public AliexPageInfo getPageInfo(int pageIndex) {
//        if(listPages == null || listPages.isEmpty()) {
//            return null;
//        }
//        
//        for(AliexPageInfo aliexPageInfo : listPages) {
//            if(aliexPageInfo.getPageIndex() == pageIndex) {
//                return aliexPageInfo;
//            }
//        }
//        
//        return null;
//    }
    public boolean isIsCheckBannedWords() {
        return isCheckBannedWords;
    }

    public void setIsCheckBannedWords(boolean isCheckBannedWords) {
        this.isCheckBannedWords = isCheckBannedWords;
    }

    public boolean isIsOnlyUS() {
        return isOnlyUS;
    }

    public void setIsOnlyUS(boolean isOnlyUS) {
        this.isOnlyUS = isOnlyUS;
    }

//    private void fetchListProductAmz(AliexStoreCommon aliexStoreCache, AliexPageInfo aliexPageInfo) {
//        if (aliexPageInfo.getListItems() == null || aliexPageInfo.getListItems().isEmpty()) {
//            return;
//        }
//        ArrayList<ProductAmz> result = null;
//
//        for (AliexProductFull aliexProductFull : aliexPageInfo.getListItems()) {
//            ArrayList<ProductAmz> listItems = fetchListProductAmz(aliexProductFull.getId());
//
//            if (listItems != null && !listItems.isEmpty()) {
//                if (result == null) {
//                    result = new ArrayList<>();
//                }
//
//                result.addAll(listItems);
//            }
//        }
//
//    }

    public ArrayList<ProductAmz> fetchListProductAmz(String id) {
        AliexOriginalInfo aliexOriginalInfo = Utils.getAliexBasicInfoCache(getCacheFile(), id);
        
        if(id.equals("32825116993")) {
            System.out.println("Kump ");
        }

        if (aliexOriginalInfo == null || !aliexOriginalInfo.isHasInfo()) {
            return null;
//            aliexOriginalInfo = new AliexOriginalInfo();
//            aliexOriginalInfo.setId(id);
//
//            // Getting detail information
//            String detail = AliexApiCall.getProductDetail(id, null);
//            AliexProductDetail aliexProductDetail = AliexParseUtil.parseProductDetailResponse(detail);
//            if (aliexProductDetail != null && aliexProductDetail.isHasInfo()) {
//                aliexOriginalInfo.setDetail(aliexProductDetail);
//            } else {
//                System.out.println("No Detail for " + id);
//                return null;
//            }
//
//            // Getting variations
//            String variation = AliexApiCall.getProductVariation(id, null);
//            AliexProductVariationResponse aliexProductVariationResponse = AliexParseUtil.parseProductVariationResponse(variation);
//
//            if (aliexProductVariationResponse != null) {
//                aliexOriginalInfo.setVariation(aliexProductVariationResponse.getVariations());
//            } else {
//                return null;
//            }
//
//            // Getting description html
//            String description = AliexApiCall.getProductDescription(id, null);
//            AliexProductDescriptionRes aliexProductDescriptionRes = AliexParseUtil.parseProductDesResponse(description);
//            if (aliexProductDescriptionRes != null) {
//                aliexOriginalInfo.setDescriptionHtml(aliexProductDescriptionRes.getDescription());
//            } else {
//                return null;
//            }
//
//            // Getting shipping price
//            String shippingResponse = AliexApiCall.getProductShipping(id, null);
//            AliexProductShipResponse aliexProductShipResponse = AliexParseUtil.parseProductShipResponse(shippingResponse);
//            if (aliexProductShipResponse == null || aliexProductShipResponse.getShippingCost() < 0) {
////                aliexOriginalInfo.setShippingPrice(0);
//                try {
//                    aliexOriginalInfo.setShippingPrice(-1);
//                    aliexOriginalInfo.setStatus("DONE");
//                    Utils.saveProductAliexToCache(getCacheFile(), aliexOriginalInfo);
//                } catch (IOException ex) {
//                    java.util.logging.Logger.getLogger(AliexStoreCommon.class.getName()).log(Level.SEVERE, null, ex);
//                }
////                crawlPageInterface.onMessageInfo("Remove " + aliexProductBasicItem.getId() + " because of no shipping method");
//                System.out.println("Remove " + id + " because of no shipping method");
//                return null;
//            } else {
//                float shippingCost = aliexProductShipResponse.getShippingCost();
//                aliexOriginalInfo.setShippingPrice(shippingCost);
//            }
//
//            try {
//                aliexOriginalInfo.setStatus("DONE");
//                Utils.saveProductAliexToCache(getCacheFile(), aliexOriginalInfo);
//            } catch (IOException ex) {
//                java.util.logging.Logger.getLogger(AliexStoreCommon.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return getAmzProducts(this, aliexOriginalInfo);
        } else {
            return getAmzProducts(this, aliexOriginalInfo);
        }
    }

    public ArrayList<ProductAmz> getAmzProducts(AliexStoreCommon aliexStoreCache, AliexOriginalInfo aliexOriginalInfo) {

        if(priceRate == 0) priceRate = 2.5f;
        
        if (aliexOriginalInfo.getShippingPrice() < 0) {
            System.out.println("Remove " + aliexOriginalInfo.getId() + " because of no shipping method");
            return null;
        }

        if (aliexOriginalInfo.getProductPrice(priceRate) > priceLimit) {
            System.out.println("Remove " + aliexOriginalInfo.getId() + " because of over the price limit");
            return null;
        }

        if (isOnlyUS) {
            ArrayList<Variation> listVariation = aliexOriginalInfo.getVariation();

            if (listVariation == null || listVariation.isEmpty()) {
                return null;
            }

            boolean isHasShipFromUS = false;

            for (Variation variation : listVariation) {
                if (variation.getPropertyIdentifiers() == null || variation.getPropertyIdentifiers().isEmpty()) {
                    continue;
                }

                for (VariationProperty variationProperty : variation.getPropertyIdentifiers()) {
                    int variId = variationProperty.getPropertyId();
                    if (aliexOriginalInfo.getDetail().isShipFrom(variationProperty.getPropertyId())) {
                        String variationName = aliexOriginalInfo.getDetail().getVariationName(variId, variationProperty.getPropertyValueId());
                        if (variationName == null || variationName.isEmpty()) {
                            continue;
                        }

                        if (variationName.toLowerCase().contains("united states") || variationName.toLowerCase().contains("us")) {
                            isHasShipFromUS = true;
                            break;
                        }

                    }
                }

                if (isHasShipFromUS) {
                    break;
                }
            }

            if (!isHasShipFromUS) {
                return null;
            }
        }

        ArrayList<ProductAmz> results = new ArrayList<>();
        ProductAmz productAmz = createBasicProductAmz(aliexOriginalInfo, aliexStoreCache);

        productAmz.setStandard_price("" + Utils.getCEOPrice(aliexOriginalInfo.getProductPrice(priceRate)));

        if (productAmz.getItem_name() == null || productAmz.getItem_name().isEmpty()) {
            productAmz.setItem_name(aliexOriginalInfo.getDetail().getTitle());
        }
        genDescriptionHtml(productAmz, aliexOriginalInfo.getDescriptionHtml());
        getSpecificInfos(aliexOriginalInfo, productAmz);

        results.add(productAmz);
        
        if (StringUtils.isEmpty(productAmz.getItem_name())) {
            return null;
        }
//        ArrayList<String> listValues = new ArrayList<>();

        if (CheckingDataUtil.containBannedKeyword(productAmz.getItem_name()) != null) {
            return null;
        }

//        String name = StringUtils.removeTradeMark(productAmz.getItem_name(), null);

//        if (name == null || name.isEmpty()) {
//            return null;
//        }
//        String item_name = null;
        if (!StringUtils.isEmpty(mainKey)) {
            String item_name = StringUtils.getFirstCapitalWord(mainKey) + " - " + productAmz.getItem_name();
            productAmz.setItem_name(item_name);
        } 
//        else {
//            item_name = productAmz.getItem_name();
//        }
        
        

//        listValues.add(item_name);

//        String keyword = StringUtils.removeTradeMark(productAmz.getGeneric_keywords(), null);
        
//        productAmz.setGeneric_keywords(listKeyWords);

//        String generic_keywords = keyword == null ? "" : keyword;

//        listValues.add(generic_keywords);

//        validateData();
//        if (storePageInfo != null) {
//            genDescriptions(hashMap, storePageInfo);
//        } else if (aliexStoreCache != null) {
//            genDescriptions(hashMap, aliexStoreCache);
//        }
        
//        productAmz.genDescriptions(null, this);

//        listValues.add(productAmz.getProduct_description());

//        ProductCache.hashMapProduct.put(aliexId, listValues);

        ArrayList<ProductAmz> listChilds = createChilds(aliexOriginalInfo, aliexStoreCache, productAmz);

        if (listChilds != null && !listChilds.isEmpty()) {
            results.addAll(listChilds);
        }

        return results;
    }
    
    public ArrayList<ProductAmz> createChilds(AliexOriginalInfo aliexOriginalInfo, AliexStoreCommon aliexStoreCache, ProductAmz productAmz) {
        ArrayList<ProductAmz> results = null;

        if (aliexOriginalInfo == null || !aliexOriginalInfo.isHasVariation()) {
            return null;
        }

        AliexProductDetail aliexProductDetail = aliexOriginalInfo.getDetail();

        if (aliexProductDetail.getSkuProperties() == null || aliexProductDetail.getSkuProperties().isEmpty()) {
            return null;
        }

        if (aliexProductDetail.getId().equals("32847119419")) {
            System.out.println("Jump");
        }

        ArrayList<Variation> listVariation = aliexOriginalInfo.getVariation();

        if (listVariation == null || listVariation.isEmpty()) {
            return null;
        }

        if (isOnlyUS) {
            for (int size = listVariation.size(), i = size - 1; i >= 0; i--) {
                Variation variation = listVariation.get(i);
                if (variation.getPropertyIdentifiers() == null || variation.getPropertyIdentifiers().isEmpty()) {
                    continue;
                }
                boolean isHasShipFromUS = false;
                for (VariationProperty variationProperty : variation.getPropertyIdentifiers()) {
                    int variId = variationProperty.getPropertyId();
                    if (aliexProductDetail.isShipFrom(variationProperty.getPropertyId())) {
                        String variationName = aliexProductDetail.getVariationName(variId, variationProperty.getPropertyValueId());
                        if (variationName == null || variationName.isEmpty()) {
                            continue;
                        }

                        if (variationName.toLowerCase().contains("united states") || variationName.toLowerCase().contains("us")) {
                            isHasShipFromUS = true;
                            break;
                        }

                    }
                }

                if (!isHasShipFromUS) {
                    listVariation.remove(i);
                }
            }

            if (listVariation.size() == 1) {
                Variation variation = listVariation.get(0);
                if (variation.getPropertyIdentifiers().size() == 1) {
                    float variationPrice = variation.getPriceValue();
                    float promotionRate = aliexOriginalInfo.getPromotionRate();
                    float productPrice = (variationPrice * (1 - promotionRate) + aliexOriginalInfo.getUPSShippringPrice()) * 2;
                    productAmz.setStandard_price("" + Utils.getCEOPrice(productPrice));
                    return null;
                }
            }

        }

        boolean isHasColor = false;
        boolean isHasSize = false;

        HashMap<Integer, String> hashMapImageUrl = null;
        HashMap<String, Integer> hashMapSku = null;

        for (int i = 0, size = listVariation.size(); i < size; i++) {
            Variation variation = listVariation.get(i);

            ProductAmz productAmzChild = productAmz.createChild((i + 1), variation, aliexOriginalInfo, aliexStoreCache);

            if (productAmzChild == null) {
                continue;
            }

            if (variation.getImageUrl() != null) {
                String imageUrl = MarketUtil.processImgUrl(variation.getImageUrl().trim());
                String hash = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));
                int colorProperyId = variation.getColorPropertyValueId(aliexProductDetail);
                if (hash.length() >= 29) {
                    productAmzChild.setMain_image_url(imageUrl);

                    if (colorProperyId > 0) {
                        if (hashMapImageUrl == null) {
                            hashMapImageUrl = new HashMap<>();
                        }
                        hashMapImageUrl.put(colorProperyId, imageUrl);
                    }
                } else {
                    if (hashMapSku == null) {
                        hashMapSku = new HashMap<>();
                    }
                    hashMapSku.put(productAmzChild.getItem_sku(), colorProperyId);
                }
            } else {
                productAmzChild.setMain_image_url(productAmz.getMain_image_url());
            }

//            else {
//                int colorProperyId = variation.getColorPropertyValueId(aliexProductDetail);
//                imageUrl = hashMapImageUrl.get(colorProperyId);
//                if(imageUrl != null) {
//                    productAmzChild.setMain_image_url(imageUrl);
//                }
//            }
            if (results == null) {
                results = new ArrayList<>();
            }
            results.add(productAmzChild);

//            if (isHasColor && isHasSize) {
//                continue;
//            }
//
//            if (productAmzChild.getVariation_theme().equals(AWSUtil.VARIATION_THEME_BOTH)) {
//                isHasColor = true;
//                isHasSize = true;
//            } else if (productAmzChild.getVariation_theme().equals(AWSUtil.VARIATION_THEME_COLOR)) {
//                isHasColor = true;
//            } else {
//                isHasSize = true;
//            }
        }

//        if (results == null || results.isEmpty()) {
//            return null;
//        }
//
//        for (int size = results.size(), i = size - 1; i >= 0; i--) {
//            ProductAmz productAmz1 = results.get(i);
//            if (productAmz1.getMain_image_url() == null) {
//                if (!hashMapSku.containsKey(productAmz1.getItem_sku())) {
//                    results.remove(i);
//                } else {
//                    int colorPropertyId = hashMapSku.get(productAmz1.getItem_sku());
//                    if (colorPropertyId < 0) {
//                        results.remove(i);
//                    } else {
//                        if (hashMapImageUrl == null) {
//                            results.remove(i);
//                            continue;
//                        }
//                        String imageUrl = hashMapImageUrl.get(colorPropertyId);
//                        if (imageUrl == null) {
//                            results.remove(i);
//                        } else {
//                            productAmz1.setMain_image_url(imageUrl);
//
//                            if (isHasColor && isHasSize) {
//                                continue;
//                            }
//
//                            if (productAmz1.getVariation_theme().equals(variationThemeBoth)) {
//                                isHasColor = true;
//                                isHasSize = true;
//                            } else if (productAmz1.getVariation_theme().equals(variationThemeColor)) {
//                                isHasColor = true;
//                            } else {
//                                isHasSize = true;
//                            }
//                        }
//                    }
//                }
//            } else {
//                if (isHasColor && isHasSize) {
//                    continue;
//                }
//
//                if (productAmz1.getVariation_theme().equals(variationThemeBoth)) {
//                    isHasColor = true;
//                    isHasSize = true;
//                } else if (productAmz1.getVariation_theme().equals(variationThemeColor)) {
//                    isHasColor = true;
//                } else {
//                    isHasSize = true;
//                }
//            }
//        }
//
//        if (isHasColor && isHasSize) {
//            productAmz.setType(ProductTypes.TYPE_PARENT_BOTH, this);
//        } else if (isHasColor) {
//            productAmz.setType(ProductTypes.TYPE_PARENT_COLOR, this);
//        } else if (isHasSize) {
//            productAmz.setType(ProductTypes.TYPE_PARENT_SIZE, this);
//        } else {
//            productAmz.setType(ProductTypes.TYPE_NORMAL, this);
//        }

        return results;
    }

    public ProductAmz createBasicProductAmz(AliexOriginalInfo aliexOriginalInfo, AliexStoreCommon aliexStoreCache) {
        ProductAmz productAmz = new ProductAmz();
        productAmz.setAliexId(aliexOriginalInfo.getId());
        productAmz.setExternal_product_id_type("UPC");
        productAmz.setFeed_product_type(aliexStoreCache.getProductType());
        productAmz.setQuantity("100");
        productAmz.setFulfillment_latency("5");
        productAmz.setMfg_minimum("10");
        productAmz.setUnit_count("1");
        productAmz.setUnit_count_type("PC");
        productAmz.setItem_package_quantity("1");
        productAmz.setNumber_of_items("1");
        productAmz.setMaterial_type("other");
        productAmz.setBrand_name(aliexStoreCache.getBrandName());
//        productAmz.setImageUrl(aliexOriginalInfo.getDetail().getProductImages());

//        productAmz.setBulletPoints(aliexStoreCache.getListBulletPoints());

        productAmz.setItem_sku(aliexStoreCache.getPrefix().toUpperCase() + FuncUtil.createSaltNumber(5) + "_" + aliexOriginalInfo.getId());
        productAmz.setPart_number(productAmz.getItem_sku().substring(0, productAmz.getItem_sku().length() - 2));

        productAmz.setItem_name(aliexOriginalInfo.getDetail().getTitle());

        BTGNode btgNode = aliexStoreCache.getBtgNode();

        if (btgNode != null && btgNode.getKeyword() != null) {
            productAmz.setItem_type(btgNode.getKeyword());
            productAmz.setTarget_audience_keywords(btgNode.getAudienceKeyword());
            productAmz.setTarget_audience_keywords1(btgNode.getAudienceKeyword());
            productAmz.setDepartment_name(btgNode.getDepartMentName());

            String audienKey = btgNode.getAudienceKeyword();
            productAmz.setTarget_audience_keywords(audienKey);
            productAmz.setTarget_audience_keywords1(audienKey);
        } else {
            productAmz.setItem_type("multitools");
            productAmz.setFeed_product_type("Tools");
        }

        productAmz.setUnit_count_type("PC");
        productAmz.setMaterial_type("other");
        productAmz.setManufacturer(productAmz.getBrand_name());
//        productAmz.setType(ProductTypes.TYPE_NORMAL, this);

//        if(audienKey == null || audienKey.isEmpty()) {
//            audienKey = "";
//            ArrayList<String> list = AWSUtil.hashMapValidValues.get("target_audience_keywords1");
//            for(String s : list) {
//                audienKey = " " + s;
//            }
//            productAmz.setTarget_audience_keywords(audienKey.trim());
//            productAmz.setTarget_audience_keywords1(audienKey.trim());
//        } else {
//            
//        }
        return productAmz;
    }

    public void genDescriptionHtml(ProductAmz productAmz, String descriptionHtmlOrigin) {
//        Pattern pt = Pattern.compile("[^!-~ ]");
//        Matcher match = pt.matcher(descriptionHtmlOrigin);
//        while (match.find()) {
//            String s = match.group();
//            descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\" + s, "");
//        }
//        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\<a href=.*?>", "");
//        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\<img.*?>", "");
//        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\<span.*?>", "");
//        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\</span>", "");
//        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("&nbsp;", "");
//
//        ArrayList<Element> results = new ArrayList<>();
//        Whitelist myCustomWhitelist = new Whitelist();
//        myCustomWhitelist.addTags("b", "br", "p", "ul", "div", "li", "strong", "border", "span", "stype");
//        String clean = Jsoup.clean(descriptionHtmlOrigin, myCustomWhitelist);
//
//        clean = clean.replaceAll("\\<\\/h2", "\\<\\/h3");
//        clean = clean.replaceAll("\\<h2\\>", "\\<h3\\>");
//
//        clean = clean.replaceAll("\\<\\/h1", "\\<\\/h3");
//        clean = clean.replaceAll("\\<h1\\>", "\\<h3\\>");
//
//        Document doc = Jsoup.parse(clean);
//
//        List<Node> childs = doc.body().childNodes();
//
//        Element preElement = null;
//        for (int i = 0, size = childs.size(); i < size; i++) {
//
//            Node childNodeLv1 = childs.get(i);
//
//            if (childNodeLv1 instanceof Element) {
//                Element elementLv1 = (Element) childNodeLv1;
//                if (elementLv1.tagName().equals("div")) {
//                    List<Node> listNode = elementLv1.childNodes();
//                    if (listNode != null) {
//                        for (Node node : listNode) {
//                            if (!(node instanceof Element)) {
//                                String nodeText = node.toString().trim();
//                                if (nodeText.isEmpty()) {
//                                    continue;
//                                }
//                                Element element = new Element(Tag.valueOf("p"), nodeText);
//                                element.appendText(nodeText);
//                                results.add(element);
//                                preElement = element;
//                            } else {
//                                Element element = (Element) node;
//                                if (element.tagName().equals("br")) {
//                                    if (preElement != null) {
//                                        String preTag = preElement.tagName();
//                                        if (!preTag.equals("p") && !preTag.equals("br")) {
//                                            results.add(element);
//                                            preElement = element;
//                                        }
//                                    } else {
//                                        results.add(element);
//                                        preElement = element;
//                                    }
//                                } else {
//                                    String txt = element.text();
//                                    if (StringUtils.isTextVisible(txt)) {
//                                        results.add(element);
//                                        preElement = element;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    if (elementLv1.tagName().equals("br")) {
//                        if (preElement != null) {
//                            String preTag = preElement.tagName();
//                            if (!preTag.equals("p") && !preTag.equals("br")) {
//                                results.add(elementLv1);
//                                preElement = elementLv1;
//                            }
//                        } else {
//                            results.add(elementLv1);
//                            preElement = elementLv1;
//                        }
//                    } else {
//                        String text = elementLv1.text();
//                        if (StringUtils.isTextVisible(text)) {
//                            results.add(elementLv1);
//                            preElement = elementLv1;
//                        }
//
//                    }
//                }
//            } else {
//                String text = childNodeLv1.toString().trim();
//                if (StringUtils.isTextVisible(text)) {
//                    Element element = new Element(Tag.valueOf("p"), text);
//                    element.appendText(text);
//                    results.add(element);
//                    preElement = element;
//                }
//            }
//
//        }
//        productAmz.setListDesParams(results);
    }

    public void getSpecificInfos(AliexOriginalInfo aliexOriginalInfo, ProductAmz productAmz) {
//        if (aliexOriginalInfo.getProductDetailUrl() == null) {
//            return;
//        }
//
//        productAmz.setMain_keywords(mainKey);
//
//        ArrayList<ItemSpecifics> listSpecifics = null;
//
//        if (aliexOriginalInfo.getDetail().getAttributes() != null) {
//            for (ProductAttribute productAttribute : aliexOriginalInfo.getDetail().getAttributes()) {
//                if (listSpecifics == null) {
//                    listSpecifics = new ArrayList<>();
//                }
//                listSpecifics.add(new ItemSpecifics(productAttribute.name, productAttribute.value));
//            }
//            productAmz.setItemSpecific(listSpecifics, fullAudienceTarget);
//        }
    }

    public String genExcelFileNameWithPage(int pageIndex) {
        return "";
    }
    
    

}
