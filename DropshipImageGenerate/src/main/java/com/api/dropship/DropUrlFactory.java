/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.dropship;

import static com.api.WebService.*;

/**
 *
 * @author PhanDuy
 */
public class DropUrlFactory {
//    public static String createDropUrl() {
//        return WebService.PONG_IP + ":" + AppConfig.port;
//    }
    
    public static String createAuthenUrl() {
//        return "http://localhost:69";
        return AUTHEN_URL + "/api/v1/clientinfo/checkserial";
    }

    public static String createLogUrl() {
//        return "http://localhost:69";
        return AUTHEN_URL + "/api/v1/client_log/create";
    }
    
    public static String createContentUrl() {
        return SERVER_DOMAIN + ":" + AUTHEN_PORT;
    }
            
    public static String createTransformCrawlDataUrl() {
        return AUTHEN_URL + "/api/v1/pltool/transform/aliex/crawl";
    }
    
    public static String createTransformCrawlDataUrlV2() {
        return AUTHEN_URL + "/api/v1/pltool/transform/aliex/crawl/v2";
    }
    
    public static String createTransformCrawlDataUrlV3() {
        return AUTHEN_URL + "/api/v1/pltool/transform/aliex/crawl/v3";
    }
    
    public static String createUpdateConfigUrl() {
        return AUTHEN_URL + "/api/v1/pltool/configs";
    }
    
    public static String createTransformAliexToAmzUrl() {
        return AUTHEN_URL + "/api/v1/pltool/transform_amz_product";
    }
}
