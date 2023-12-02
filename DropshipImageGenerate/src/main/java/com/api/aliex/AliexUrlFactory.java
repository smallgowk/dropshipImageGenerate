/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.aliex;

/**
 *
 * @author Admin
 */
public class AliexUrlFactory {
    private static final String DOMAIN = "https://api.aliseeks.com/v1/";
    
//    private static final String STORE_SEARCH = "https://okgo.aliexpress.com/store/3257115/search/1.html";
//    private static final String STORE_SEARCH = "https://{domain}/store/{storeId}/search/{pageNumber}.html";
//    private static final String STORE_SEARCH_ALL = "https://{domain}/store/{storeId}/search?SortType=bestmatch_sort";
    
    
    
    public static String createProductDetailUrl() {
        return DOMAIN + "products/details";
    }
    
    public static String createGetProductDescriptionUrl() {
        return DOMAIN + "products/description/html";
    }
    
    public static String createGetProductShippingUrl() {
        return DOMAIN + "products/shipping";
    }
    
    public static String createGetProductVariationUrl() {
        return DOMAIN + "products/variations";
    }
    
    public static String createGetProductFullInfoUrl() {
        return DOMAIN + "products";
    }
    
//    public static String createStoreSearchUrl(String domain, String storeId, int number) {
//        return STORE_SEARCH.replace("{domain}", domain).replace("{storeId}", storeId).replace("{pageNumber}", "" + number);
//    }
//    
//    public static String createStoreSearchAllUrl(String originUrl) {
//        URI uri = URI.create(originUrl);
//        String domain = uri.getHost();
//        String path = uri.getPath();
//        
//        uri.getHost();
//    }
}
