/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.models.aliex.AliexOriginalInfo;
import com.models.aliex.AliexProductFull;
import com.models.aliex.store.BaseStoreInfo;
import com.models.aliex.store.AliexPageInfo;
import com.models.aliex.store.AliexStoreCommon;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Admin
 */
public class Utils {

    public static HashMap<String, AliexOriginalInfo> hashMapOriginalInfo = new HashMap<>();
    public static HashMap<String, ArrayList<String>> hashMapMerchantKeys = new HashMap<>();
    public static HashMap<String, ArrayList<String>> hashMapRelatedKeys = new HashMap<>();

    public static String formatPrice(float price) {
        return String.format("%.2f", price);
    }

    public static String getCEOPrice(float price) {
//        int n = (int) price;
//
//        float ceoPrice = n * 1f + 0.99f;

//        return formatPrice(ceoPrice);
        return formatPrice(price);
    }

    public static String removeSpace(String input) {
        return input.replaceAll(Pattern.quote(" "), "");
    }

    public static String removeSpecialChar(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ]", " ");
    }

    public static void saveProductAliexToCache(String folderPath, AliexOriginalInfo aliexBasicInfo) throws FileNotFoundException, IOException {
    }

    public static void saveStoreInfoToCache(AliexStoreCommon aliexStoreCache) throws FileNotFoundException, IOException {
    }

    public static void saveStorePageCache(AliexStoreCommon aliexStoreCommon, AliexPageInfo aliexPageInfo) throws IOException {
    }

    public static void saveStorePageCache(AliexStoreCommon aliexStoreCommon, String data, int pageIndex) throws IOException {
    }

    public static void saveAliexProduct(AliexProductFull aliexProductFull) {
    }

    public static void saveStoreCommonCache(String cacheFile, String data) throws IOException {
    }

    public static AliexStoreCommon getStoreCache(BaseStoreInfo storePageInfo) {
//        String filePath = Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR + Configs.pathChar + storePageInfo.getLastFileFolder() + Configs.pathChar + storePageInfo.getLastFileFolder() + ".txt";
//
//        File file = new File(filePath);
//        if (!file.exists()) {
//            return null;
//        }
//
//        String object;
//        try {
//            object = FileUtils.readFileToString(file);
//            object = EncryptUtil.decrypt(object);
//            Gson gson = new Gson();
//            return gson.fromJson(object, AliexStoreCommon.class);
//        } catch (IOException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            System.out.println("" + object);
        return null;
    }

    public static AliexStoreCommon getStoreCache(String cacheFileName) {
        return null;
    }

    public static AliexPageInfo getStorePageCache(String cacheFileName, int pageIndex) {
        return null;
    }

    public static AliexOriginalInfo getAliexBasicInfoCache(String folderPath, String aliexId) {
        if (hashMapOriginalInfo.containsKey(aliexId)) {
            return hashMapOriginalInfo.get(aliexId);
        }
        return null;
    }

    public static void saveMerchantInfo(String keyword, ArrayList<String> listKeyword) {

        String id = keyword.trim().toLowerCase().replaceAll(Pattern.quote(" "), "_");

        hashMapMerchantKeys.put(id, listKeyword);
    }

    public static void saveRelatedSearch(String productId, ArrayList<String> listKeyword) {
    }

    public static ArrayList<String> getMerchantInfo(String keyword) {
        return null;
    }

    public static ArrayList<String> getRelatedInfo(String productId) {
        if (hashMapRelatedKeys.containsKey(productId)) {
            return hashMapRelatedKeys.get(productId);
        }
        return null;
    }

    public static void writeToFile(String data, String filePath) throws IOException {
        FileUtils.writeStringToFile(new File(filePath), data, "UTF-8");
    }
}
