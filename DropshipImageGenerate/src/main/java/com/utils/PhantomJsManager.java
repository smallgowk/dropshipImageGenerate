/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.controller.crawl.aliex.AliexCrawlSvs;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.ErrorHandler;

/**
 *
 * @author PhanDuy
 */
public class PhantomJsManager {
    
    private static PhantomJsManager phantomJsManager;
    
    PhantomJSDriver ghostDriver;
    
    public static PhantomJsManager getInstance() {
        if (phantomJsManager == null) {
            phantomJsManager = new PhantomJsManager();
        }
        return phantomJsManager;
    }
    
    public void init() {
        ghostDriver = new PhantomJSDriver();
        ghostDriver.setLogLevel(Level.OFF);
        ghostDriver.setErrorHandler(new ErrorHandler());
        
            for (Map.Entry<String,String> entry : AliexCrawlSvs.getInstance().cookies.entrySet()) {
                Cookie cookie = new Cookie(entry.getKey(), entry.getValue(), ".aliexpress.com", "/", null);
                ghostDriver.manage().addCookie(cookie);
            }    
    }
    
    public Document renderPage(String url) {
        try {
            ghostDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            ghostDriver.get(url);
            return Jsoup.parse(ghostDriver.getPageSource());
        } finally {
        }
    }
    
    public void quit() {
        if (ghostDriver != null) {
            ghostDriver.close();
            ghostDriver.quit();
        }
    }
}
