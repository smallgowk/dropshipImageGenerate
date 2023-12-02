/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author PhanDuy
 */
public class CrawlDataBase {

    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_LIMITED = 1;
    public static final int STATUS_NEED_AUTHEN = 2;

    public int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void updateStatus(Document doc) {

        if (doc == null) {
            status = STATUS_NEED_AUTHEN;
            return;
        }

        Elements body = doc.select("body");

        String dataSpm = body.first().attr("data-spm");
        if (dataSpm == null || dataSpm.isEmpty()) {
            status = STATUS_LIMITED;
//            System.out.println("Limited: " + doc.html());
        } else {
            if (dataSpm.equals("buyerloginandregister")) {
                status = STATUS_NEED_AUTHEN;
//                System.out.println("Authen: " + doc.html());
            } else {
                status = STATUS_SUCCESS;
            }
        }

//        Elements skus = doc.select("div[class=ui-unusual ui-unusual-busy]");
//        
//        if(skus != null && !skus.isEmpty()) {   
//            System.out.println("Xe taiiii");
//        } else {
//            skus = doc.select("body");
//            String type = skus.first().attr("data-spm");
//            System.out.println("" + type);
//        }
    }

    private String getCrawlStatus() {
        switch (status) {
            case STATUS_LIMITED:
                return "Limited Truck";
            case STATUS_NEED_AUTHEN:
                return "Need to authen";
                
        }
        return "Success";
    }

    public boolean isSuccess() {
        
        if(status == STATUS_SUCCESS) {
            return true;
        } else {
            System.out.println("Status: " + getCrawlStatus());
            return false;
        }
    }
}
