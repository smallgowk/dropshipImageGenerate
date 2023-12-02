/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.thread.ProcessCrawlTestProductThread;
import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import com.models.aliex.store.inputdata.SnakeBaseStoreOrderInfo;
import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class TestController {

    ProcessCrawlTestProductThread processCrawlThread;
    String productUrl;
    String cookieType;

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setCookieType(String cookieType) {
        this.cookieType = cookieType;
    }

    public void startCrawl() {

        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
        
        processCrawlThread = new ProcessCrawlTestProductThread(productUrl);
        processCrawlThread.setCookieType(cookieType);
        processCrawlThread.start();
    }

    public void stopCrawl() {
        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
    }
    
    public void finish() {
        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
    }
    
    public void pause() {
        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
    }
    
    public void resume() {
        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
    }

}
