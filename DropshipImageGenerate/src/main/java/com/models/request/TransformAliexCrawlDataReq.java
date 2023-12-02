package com.models.request;

import com.models.aliex.crawl.AliexScriptDetailData;
import com.models.aliex.store.AliexStoreInfo;
import java.util.ArrayList;

public class TransformAliexCrawlDataReq {
    public String id;
    public String title;
    public AliexScriptDetailData data;
    public ToolAndStoreInfo toolAndStoreInfo;
//    public AliexStoreInfo aliexStoreInfo;
    public ArrayList<ImagePathModel> imagePaths;
    
    public TransformAliexCrawlDataReq(String id, String title, AliexScriptDetailData data, AliexStoreInfo aliexStoreInfo, ArrayList<ImagePathModel> imagePathModels) {
        this.id = id;
        this.title = title;
        this.data = data;
        toolAndStoreInfo = new ToolAndStoreInfo();
        toolAndStoreInfo.updateToolConfig();
        toolAndStoreInfo.updateStoreInfo(aliexStoreInfo);
        this.imagePaths = imagePathModels;
    }

    public TransformAliexCrawlDataReq() {
    }
}
