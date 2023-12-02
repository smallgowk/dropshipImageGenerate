/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaces;

import com.models.aliex.store.BaseStoreInfo;


/**
 *
 * @author duyuno
 */
public interface CrawlInterface {
    public void updateState(String state);
    public void updatedPageInfo(BaseStoreInfo aliexStorePageInfo);
    public void updatedProductInfo(BaseStoreInfo aliexStorePageInfo);
}
