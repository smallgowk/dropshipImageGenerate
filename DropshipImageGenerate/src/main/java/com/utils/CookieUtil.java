/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.config.Configs;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Admin
 */
public class CookieUtil {

//    public static final String COOKIE_CACHE_FILE = "Cookies/Cookie.txt";
//    public static final String COOKIE_CACHE_FOLDER = "Cookie.txt";
    public static final String JSESSIONID = "JSESSIONID";
    public static final String COOKIE_MERCHANT = "CookieMerchant.txt";

    public static String cachePath;

    public static HashMap<String, String> getCookiesFromDriver(WebDriver driver) {

        if (driver == null || driver.manage() == null || driver.manage().getCookies() == null) {
            return null;
        }

        HashMap<String, String> cookies = new HashMap<>();

//        System.out.println("======================");
        for (Cookie ck : driver.manage().getCookies()) {
//            String strCookie = (ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";" + ck.getExpiry() + ";" + ck.isSecure());
//            String strCookie = (ck.getName() + ";" + ck.getValue() + ";");
//            if (ck.getName().equals(JSESSIONID)) {
//                System.out.println("JSESSIONID: " + ck.getValue());
//            }
//            System.out.println("" + strCookie);
            cookies.put(ck.getName(), ck.getValue());
        }
//        System.out.println("======================");

        return cookies;
    }

    public static Map<String, String> getCookies(WebDriver driver, boolean checkFromLocal) {
        return null;
    }

//    public static String getCookiesFileName() {
//        return Configs.CONFIG_FOLDER_PATH + Configs.pathChar + "Cookie.txt";
//    }
//    public static String getCookiesFilePath(String fileName) {
//        return Configs.CONFIG_FOLDER_PATH + Configs.pathChar + fileName;
//    }
    public static Map<String, String> getCookiesFromCache() {
        return null;
    }
    
    public static Map<String, String> getCookiesFromCache(String path) {
        String cookiesData = null;
        try {
            cookiesData = readCookies(path);
        } catch (Exception ex) {
            Logger.getLogger(CookieUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (cookiesData != null && !cookiesData.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(cookiesData, HashMap.class);
        }

        return null;
    }

    public static void saveCookies(Map<String, String> cookies) {
    }
    
    public static void saveCookies(Map<String, String> cookies,String fileName) {
    }

    public static String readCookies(String filePath) throws FileNotFoundException, IOException {

//        InputStream inputStream = CookieUtil.class.getResourceAsStream(fileName);
//        if (inputStream != null) {
//            return IOUtils.toString(inputStream);
//        } else {
//            return null;
//        }
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            return IOUtils.toString(inputStream);
            // do something with everything string
        } catch (Exception ex) {
            return null;
        }
    }

    public static void deleteCookies() {
    }
    
    public static boolean isValidCookie(Map<String, String> cookies) {
        return cookies != null && cookies.containsKey("JSESSIONID");
    }
}
