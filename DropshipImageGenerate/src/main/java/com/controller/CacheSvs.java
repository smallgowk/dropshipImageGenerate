/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.google.gson.Gson;
import com.config.Configs;
import com.google.common.reflect.TypeToken;
import com.models.aliex.AliexProductFull;
import com.models.aliex.crawl.AliexScriptDetailData;
import com.models.request.ImagePathModel;
import com.models.response.TransformCrawlResponse;
import com.utils.EncryptUtil;
import com.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author PhanDuy
 */
public class CacheSvs {

    private static CacheSvs fetchAliexProductInfoSvs;

    public static CacheSvs getInstance() {
        if (fetchAliexProductInfoSvs == null) {
            fetchAliexProductInfoSvs = new CacheSvs();
        }
        return fetchAliexProductInfoSvs;
    }
    
    public AliexProductFull getProductFromCache(String id, String storeSign) {
        
        return null;
    }
    
    public TransformCrawlResponse getProductResFromCache(String id, String storeSign) {
        
        return null;
    }
    
    public ArrayList<ImagePathModel> getAliexScriptDetailDataFromCache(String id, String storeSign) {
        
        return null;
    }

//    public AliexProductFull getProductInfo(String id, String storeSign) {
//        
//        AliexProductFull aliexProductFull = null;
//
//        AliexProductFullResponse aliexProductFullResponse = AliexApiCall.getProductFullInfo(id);
//
//        if (aliexProductFullResponse != null && !StringUtils.isEmpty(aliexProductFullResponse.getHtmlDescription())) {
//            aliexProductFull = new AliexProductFull();
//            aliexProductFull.setDataApi(aliexProductFullResponse);
//            saveProductInfo(aliexProductFull, storeSign);
//        }
//
//        return aliexProductFull;
//    }

    public void saveProductInfo(AliexProductFull aliexProductFull, String storeSign) {
        
    }
    
    public void saveProductInfo(TransformCrawlResponse res, String storeSign) {
        if (res == null || res.baseAmzProduct == null || res.baseAmzProduct.aliexId == null) return;
        
    }
    
    public void saveAliexScriptDetailData(String id, AliexScriptDetailData crawlData, String storeSign) {
        
    }
    
    public void saveAliexScriptDetailData(String id, ArrayList<ImagePathModel> imagePaths, String storeSign) {
        
    }
}
