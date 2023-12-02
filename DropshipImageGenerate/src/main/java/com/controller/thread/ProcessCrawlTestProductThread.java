/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.thread;

import com.api.RestBase;
import com.api.dropship.DropApiCall;
import com.api.dropship.DropUrlFactory;
import com.api.dropship.req.TransformAliexToAmzReq;
import com.config.Configs;
import com.google.gson.Gson;
import com.models.aliex.AliexProductFull;
import com.models.aliex.crawl.AliexScriptDetailData;
import com.models.aliex.store.AliexStoreInfo;
import com.models.aliex.store.inputdata.SnakeBaseStoreOrderInfo;
import com.models.amazon.ProductAmz;
import com.controller.crawl.aliex.AliexCrawlProductInfoSvs;
import com.controller.inputprocess.TransformStoreInput;
import com.models.response.TransformResponse;
import com.models.response.ResponseObj;
import com.models.response.TransformAliexToAmzResponse;
import com.models.response.TransformCrawlAliexResponse;
import com.controller.transform.ProcessPageDataSvs;
import com.controller.transform.ProcessStoreInfoSvs;
import com.controller.transform.ProcessTransformAliexToAmz;
import com.models.request.ImagePathModel;
import com.models.request.TransformAliexCrawlDataReq;
import com.models.response.TransformCrawlResponse;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author duyuno
 */
public class ProcessCrawlTestProductThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(ProcessCrawlTestProductThread.class.getSimpleName());

    String productUrl;
    String cookieType;
    ProcessStoreInfoSvs processStoreInfoSvs;
    

//    StringBuffer sb;
    public ProcessCrawlTestProductThread(String productUrl) {
        this.productUrl = productUrl;
        processStoreInfoSvs = new ProcessStoreInfoSvs();
    }

    public void setCookieType(String cookieType) {
        this.cookieType = cookieType;
    }

    public boolean isStop = false;

    public void doStop() {
        isStop = true;

        try {
            interrupt();
        } catch (Exception ex) {

        }
    }

    @Override
    public void run() {
        // Transform raw data

//        sb = new StringBuffer();
//        crawlProcessListener.onPushState("Processing input data...");
//        sb.append("================= \n");
//        sb.append("Start crawl " + baseStoreOrderInfo.storeSign);
//        sb.append("\n");
        System.out.println("=================");
        System.out.println("Start crawl " + productUrl);
        AliexStoreInfo aliexStoreInfo = TransformStoreInput.getInstance().transformRawData(SnakeBaseStoreOrderInfo.buildTestData());
        AliexScriptDetailData data = AliexCrawlProductInfoSvs.getInstance().crawlProductData(productUrl, cookieType + ".txt", "TestStore");
        data.updateConfig();
        
//        TransformCrawlAliexResponse res = DropApiCall.doTransformCrawlData(data, null);
//        System.out.println("Response: " + res.getData());
//        
//        AliexProductFull aliexProductFull = res.getData();
//        aliexProductFull.updateSearchTerms();
//        aliexProductFull.updateDescription();
//        processStoreInfoSvs.updateAliexProductFullSku(aliexProductFull, aliexStoreInfo);
//        processStoreInfoSvs.updateAliexProductFullGenericKeywords(aliexProductFull, aliexStoreInfo);
//        processStoreInfoSvs.updateAliexProductFullItemSpecifics(aliexProductFull, aliexStoreInfo);
//        processStoreInfoSvs.updateAliexProductFullDescription(aliexProductFull, aliexStoreInfo);
//        processStoreInfoSvs.removeBrandNameInfo(aliexProductFull);
        
        ArrayList<ImagePathModel> imagePathModels = data.toImagePaths();
        TransformAliexCrawlDataReq transformAliexCrawlDataReq = new TransformAliexCrawlDataReq(
                data.productInfoComponent.idStr,
                data.productInfoComponent.subject,
                data,
                aliexStoreInfo,
                imagePathModels
        );
        Gson gson = new Gson();
        System.out.println("===> " + gson.toJson(transformAliexCrawlDataReq));
        String url = DropUrlFactory.createTransformCrawlDataUrlV3();
        
//        TransformCrawlAliexResponse res = DropApiCall.doTransformCrawlDataV2(transformAliexCrawlDataReq, null);
        TransformResponse res = RestBase.getInstance().postForObject(url, transformAliexCrawlDataReq, TransformResponse.class);
        if (res != null) {
            if (res.status == 200) {
                ArrayList<ProductAmz> listProduct = ProcessTransformAliexToAmz.transform(
                        res.getData(),
                        aliexStoreInfo,
                        imagePathModels
                );
                ProcessPageDataSvs.processPageData(listProduct, aliexStoreInfo);
            } else {
                ArrayList<TransformResponse> list = new ArrayList<>();
                list.add(res);
                ProcessPageDataSvs.processPageErrorData(list, aliexStoreInfo);
            }
        } else {
            System.out.println("No info");
        }
        
        System.out.println("Done");
    }
}
