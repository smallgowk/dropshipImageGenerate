package com.models.response;

import com.models.aliex.AliexProductFull;


public class TransformCrawlAliexResponse {

    private AliexProductFull data;

    public TransformCrawlAliexResponse() {
    }

    public TransformCrawlAliexResponse(AliexProductFull data) {
        this.data = data;
    }

    public AliexProductFull getData() {
        return data;
    }

    public void setData(AliexProductFull data) {
        this.data = data;
    }
}