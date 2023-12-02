/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class CrawlDataPageBase extends CrawlDataBase{
    public int totalProduct;
    public ArrayList<CrawlPageProductItem> listProductIds;

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public ArrayList<CrawlPageProductItem> getListProductIds() {
        return listProductIds;
    }

    public void setListProductIds(ArrayList<CrawlPageProductItem> listProductIds) {
        this.listProductIds = listProductIds;
    }
    
    public boolean isHasData() {
        return listProductIds != null && !listProductIds.isEmpty();
    }
}
