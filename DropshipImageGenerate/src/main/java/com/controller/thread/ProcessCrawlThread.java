/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.thread;

import com.api.RestBase;
import com.api.aliex.AliexApiCall;
import com.api.aliex.response.AliexProductShipResponse;
import com.api.dropship.DropApiCall;
import com.api.dropship.DropUrlFactory;
import com.api.dropship.req.SendLogInfoReq;
import com.api.dropship.req.TransformAliexToAmzReq;
import com.config.Configs;
import com.interfaces.CallApiInterface;
import com.models.aliex.AliexProductFull;
import com.models.aliex.crawl.AliexScriptDetailData;
import com.models.aliex.store.AliexPageInfo;
import com.models.aliex.store.AliexStoreInfo;
import com.models.aliex.crawl.CrawlDataPageBase;
import com.models.aliex.crawl.CrawlDataStoreBase;
import com.models.aliex.crawl.CrawlPageProductItem;
import com.models.aliex.store.inputdata.SnakeBaseStoreOrderInfo;
import com.controller.crawl.aliex.AliexCrawlProductInfoSvs;
import com.controller.crawl.aliex.AliexCrawlSvs;
import com.controller.inputprocess.TransformStoreInput;
import com.controller.CacheSvs;
import com.models.response.TransformCrawlAliexResponse;
import com.controller.transform.ProcessStoreInfoSvs;
import com.google.gson.Gson;
import com.interfaces.CrawlProcessListener;
import com.models.request.ImagePathModel;
import com.models.request.TransformAliexCrawlDataReq;
import com.models.response.TransformResponse;
import com.models.response.TransformCrawlResponse;
import com.utils.ComputerIdentifier;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.log4j.Logger;

/**
 *
 * @author duyuno
 */
public class ProcessCrawlThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(ProcessCrawlThread.class.getSimpleName());

//    static HashMap<String, CrawlDataStoreBase> hashMapStoreInfo = new HashMap<>();
//    static HashMap<String, AliexStorePageWrap> hashMapPageInfo = new HashMap<>();
    SnakeBaseStoreOrderInfo baseStoreOrderInfo;
    CrawlProcessListener crawlProcessListener;
    ProcessStoreInfoSvs processStoreInfoSvs;
    

//    StringBuffer sb;
    public ProcessCrawlThread(SnakeBaseStoreOrderInfo baseStoreOrderInfo, CrawlProcessListener crawlProcessListener) {
        this.baseStoreOrderInfo = baseStoreOrderInfo;
        this.crawlProcessListener = crawlProcessListener;
        processStoreInfoSvs = new ProcessStoreInfoSvs();
    }

    public boolean isStop = false;

    public void doStop() {
        isStop = true;

        try {
            interrupt();
        } catch (Exception ex) {

        }
    }
    
    public int getPercentProcess(int size, int j) {
        int percent = (int) ((((j + 1) * 1f) / size) * 100);
        if (percent == 100) {
            percent = 99;
        }
        return percent;
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
        System.out.println("Start crawl " + baseStoreOrderInfo.storeSign);

        AliexStoreInfo aliexStoreInfo = TransformStoreInput.getInstance().transformRawData(baseStoreOrderInfo);
        crawlProcessListener.onStartProcess(aliexStoreInfo.getStoreSign(), aliexStoreInfo.getAccNo());
        long start = System.currentTimeMillis();
        SendLogInfoReq sendLogInfoReq = new SendLogInfoReq();
        sendLogInfoReq.setComputerSerial(ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-"));
        URI uri = URI.create(aliexStoreInfo.getLink());
        sendLogInfoReq.setStoreUrl("" + uri.getScheme() + "://" + uri.getHost() + uri.getPath());
        Calendar calendar = Calendar.getInstance();
        sendLogInfoReq.setYear(calendar.get(Calendar.YEAR));
        sendLogInfoReq.setMonth(calendar.get(Calendar.MONTH) + 1);

//        if (AuthenConfig.isAllowGetMerchant()) {
//            // CrawlMerchant
//            System.out.println("Getting merchant data... " + aliexStoreInfo.getMain_key());
//            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Getting merchant data...");
//            ArrayList<String> listKeywords = MerchantSearchSvs.getInstance().getFromCache(aliexStoreInfo.getMain_key());
//            if (listKeywords != null && !listKeywords.isEmpty()) {
//                aliexStoreInfo.setListKeyWords(listKeywords);
//            } else {
//                start = System.currentTimeMillis();
//                listKeywords = MerchantSearchSvs.getInstance().searchKeywords(aliexStoreInfo.getMain_key());
//                System.out.println("Time get merchant data: " + (System.currentTimeMillis() - start));
//                if (listKeywords != null) {
//                    System.out.println("Merchantkeys: " + listKeywords);
//                    aliexStoreInfo.setListKeyWords(listKeywords);
//                }
//            }
//        }
//        if(AuthenConfig.isAlloeGetRelate() && (aliexStoreInfo.getListKeyWords() == null || aliexStoreInfo.getListKeyWords().isEmpty())) {
//            ArrayList<String> listRelatedSearch = Utils.getRelatedInfo(productId)
//        }
        // Crawl Store Info
        crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Getting aliex store info...");
//        sb.append("Getting aliex store info...");
//        sb.append("\n");
        System.out.println("Getting aliex store info...");
        start = System.currentTimeMillis();
        CrawlDataStoreBase crawlDataStoreBase = AliexCrawlSvs.getInstance().crawlStoreInfo(aliexStoreInfo);

//        sb.append("Time get aliex store info: " + (System.currentTimeMillis() - start));
//        sb.append("\n");
        System.out.println("Time get aliex store info: " + (System.currentTimeMillis() - start));

        if (crawlDataStoreBase == null || !crawlDataStoreBase.isSuccess()) {
            isStop = true;
            crawlProcessListener.onStopToLogin(aliexStoreInfo.getLink(), aliexStoreInfo.getStoreSign());
            return;
        }

//        AliexCrawlSvs.getInstance().nextPage();
        aliexStoreInfo.setTotalPage(crawlDataStoreBase.getPageTotal());
        processStoreInfoSvs.processStoreInfo(aliexStoreInfo);
//        AliexStoreInfoWrap aliexStoreInfoWrap = TransformToServer.getInstance().transformData(aliexStoreInfo);
//        ResponseObj responseObj = DropApiCall.doSendStoreInfo(aliexStoreInfoWrap, null);
//        if (responseObj == null || !responseObj.isSuccess()) {
//            isStop = true;
//            crawlProcessListener.onStop(aliexStoreInfo.getStoreSign());
//            if (responseObj != null) {
//                crawlProcessListener.onPushErrorRequest(aliexStoreInfo.getStoreSign(), responseObj);
//            }
//            return;
//        }

        
//        System.out.println("" + sb.toString());
//        sb = new StringBuffer();
        // Crawl Page Info and get aliex product info
        crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Getting product info...");

        CrawlDataPageBase crawlDataPageBase = crawlDataStoreBase;
        int page = 1;
        while (crawlDataPageBase != null && page <= crawlDataStoreBase.pageTotal) {

            if(!crawlDataPageBase.isSuccess()) {
                isStop = true;
                crawlProcessListener.onStopToLogin(aliexStoreInfo.getLink(), aliexStoreInfo.getStoreSign());
                return;
            }
            
            if (isStop) {
                return;
            }

            int size = crawlDataPageBase.listProductIds.size();

//            ArrayList<AliexProductFull> pageProducts = new ArrayList<>();
            for (int j = 0; j < size; j++) {

                if (isStop) {
                    return;
                }
                CrawlPageProductItem crawlPageProductItem = crawlDataPageBase.listProductIds.get(j);
                if (!processStoreInfoSvs.updateAliexItemTitle(crawlPageProductItem)) {
                    crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Page (" + page + "/" + crawlDataStoreBase.pageTotal + ") " + getPercentProcess(size, j) + "%");
                    continue;
                }
//                AliexProductFull aliexProductFull = CacheSvs.getInstance().getProductFromCache(crawlPageProductItem.getId(), aliexStoreInfo.getStoreSign());
//                
//                if (aliexProductFull == null) {
//                    sendLogInfoReq.addProductCount();
//                    AliexScriptDetailData data = AliexCrawlProductInfoSvs.getInstance().crawlProductData(crawlPageProductItem.getUrl(), aliexStoreInfo.getStoreSign());
//                    if (data == null || !data.isSuccess()) {
//                        crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Page (" + page + "/" + crawlDataStoreBase.pageTotal + ") " + getPercentProcess(size, j) + "%");
//                        System.out.println(crawlPageProductItem.getId() + ": no data crawl");
//                        continue;
//                    }
//                    try {
//                        data.updateConfig();
//                        TransformAliexCrawlDataReq transformAliexCrawlDataReq = new TransformAliexCrawlDataReq(
//                                crawlPageProductItem.getId(), 
//                                crawlPageProductItem.titleUpdate, 
//                                data, 
//                                aliexStoreInfo
//                        );
////                        TransformCrawlAliexResponse res = DropApiCall.doTransformCrawlDataV2(transformAliexCrawlDataReq, null);
////                        aliexProductFull = res.getData();
////                        if (aliexProductFull == null) {
////                            System.out.println(crawlPageProductItem.getId() + ": can not transform");
////                            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Page (" + page + "/" + crawlDataStoreBase.pageTotal + ") " + getPercentProcess(size, j) + "%");
////                            continue;
////                        }
//                        
//                        String url = DropUrlFactory.createTransformCrawlDataUrlV3();
//                        TransformCrawlResponse res = RestBase.getInstance().postForObject(url, transformAliexCrawlDataReq, TransformCrawlResponse.class);
//                    } catch (Exception e) {
//                        System.out.println(crawlPageProductItem.getId() + ": " + e.toString());
//                        crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Page (" + page + "/" + crawlDataStoreBase.pageTotal + ") " + getPercentProcess(size, j) + "%");
//                        continue;
//                    }
//                    aliexProductFull.setStoreSign(aliexStoreInfo.getStoreSign());
//                    aliexProductFull.setPageIndex(page);
//                    CacheSvs.getInstance().saveProductInfo(aliexProductFull, aliexStoreInfo.getStoreSign());
//                } else {
//                    processStoreInfoSvs.processProduct(aliexProductFull, aliexStoreInfo);
//                }
                TransformCrawlResponse res = CacheSvs.getInstance().getProductResFromCache(crawlPageProductItem.getId(), aliexStoreInfo.getStoreSign());
                ArrayList<ImagePathModel> imagePathModelCache = CacheSvs.getInstance().getAliexScriptDetailDataFromCache(crawlPageProductItem.getId(), aliexStoreInfo.getStoreSign());
                if (res == null || imagePathModelCache == null) {
                    sendLogInfoReq.addProductCount();
                    AliexScriptDetailData data = AliexCrawlProductInfoSvs.getInstance().crawlProductData(crawlPageProductItem.getUrl(), aliexStoreInfo.getStoreSign());
                    if (data == null || !data.isSuccess()) {
                        crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Page (" + page + "/" + crawlDataStoreBase.pageTotal + ") " + getPercentProcess(size, j) + "%");
                        System.out.println(crawlPageProductItem.getId() + ": no data crawl");
                        continue;
                    }
                    
                    try {
                        data.updateConfig();
                        ArrayList<ImagePathModel> imagePathModels = data.toImagePaths();
                        CacheSvs.getInstance().saveAliexScriptDetailData(crawlPageProductItem.getId(), imagePathModels, aliexStoreInfo.getStoreSign());
                        TransformAliexCrawlDataReq transformAliexCrawlDataReq = new TransformAliexCrawlDataReq(
                                crawlPageProductItem.getId(),
                                crawlPageProductItem.titleUpdate,
                                data,
                                aliexStoreInfo,
                                imagePathModels
                        );
                        String url = DropUrlFactory.createTransformCrawlDataUrlV3();
                        TransformResponse response = RestBase.getInstance().postForObject(url, transformAliexCrawlDataReq, TransformResponse.class);
                        if (response != null) {
                            if (response.status == 200) {
                                CacheSvs.getInstance().saveProductInfo(response.getData(), aliexStoreInfo.getStoreSign());
                                processStoreInfoSvs.processProduct(
                                    crawlPageProductItem.getId(),
                                    response.getData(),
                                    aliexStoreInfo,
                                    page,
                                    aliexStoreInfo.getStoreSign(),
                                    imagePathModels
                                );
                            } else {
                                processStoreInfoSvs.processErrorProducts(response, aliexStoreInfo.getStoreSign(), page);
                            }
                        } else {
                            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Page (" + page + "/" + crawlDataStoreBase.pageTotal + ") " + getPercentProcess(size, j) + "%");
                            System.out.println(crawlPageProductItem.getId() + ": no data crawl");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println(crawlPageProductItem.getId() + ": " + e.toString());
                        crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Page (" + page + "/" + crawlDataStoreBase.pageTotal + ") " + getPercentProcess(size, j) + "%");
                        continue;
                    }
                } else {
                    processStoreInfoSvs.processProduct(
                            crawlPageProductItem.getId(),
                            res,
                            aliexStoreInfo,
                            page,
                            aliexStoreInfo.getStoreSign(),
                            imagePathModelCache
                    );
                }
                
                if (!isStop) {
                    crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Page (" + page + "/" + crawlDataStoreBase.pageTotal + ") " + getPercentProcess(size, j) + "%");
                }
            }

            AliexPageInfo aliexPageInfo = new AliexPageInfo();
            aliexPageInfo.setPageIndex(page);
            aliexPageInfo.setTotalProduct(size);
            aliexPageInfo.setStoreSign(aliexStoreInfo.getStoreSign());

            processStoreInfoSvs.processPageInfo(aliexPageInfo);

            DropApiCall.doSendUpdateInfo("Get " + crawlDataPageBase.listProductIds.size() + " from " + aliexStoreInfo.getStoreSign() + " page " + page, null);
            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Page (" + page + "/" + crawlDataStoreBase.pageTotal + ") " + "100%");
            page++;
            processStoreInfoSvs.clearMapData();
            if(page <= crawlDataStoreBase.pageTotal) {
                crawlDataPageBase = AliexCrawlSvs.getInstance().crawlNextPageInfo(aliexStoreInfo, crawlDataStoreBase, page);
            }
        }

        DropApiCall.doSendLogInfo(sendLogInfoReq, new CallApiInterface() {
            @Override
            public void onSuccess(String response) {
                System.out.println("Duyuno send log success: " + response);
            }

            @Override
            public void onFailure(Exception ex) {
                System.out.println("Duyuno send log fail: " + ex.getMessage());
            }
        });

        crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Done");
        crawlProcessListener.onFinishPage(aliexStoreInfo.getStoreSign());
    }
}
