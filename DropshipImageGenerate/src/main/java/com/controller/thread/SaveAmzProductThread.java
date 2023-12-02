/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.thread;

import com.controller.transform.ProcessPageDataSvs;
import com.models.aliex.store.AliexStoreInfo;
import com.models.amazon.ProductAmz;
import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class SaveAmzProductThread extends Thread {
    
    public ArrayList<ProductAmz> listProducts;
    public AliexStoreInfo aliexStoreInfo;
    public int pageIndex = -1;

    public SaveAmzProductThread(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo, int pageIndex) {
        this.listProducts = listProducts;
        this.aliexStoreInfo = aliexStoreInfo;
        this.pageIndex = pageIndex;
    }
    
    public SaveAmzProductThread(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo) {
        this.listProducts = listProducts;
        this.aliexStoreInfo = aliexStoreInfo;
    }

    @Override
    public void run() {
        ArrayList<ProductAmz> listTemp = new ArrayList<>();
        int productCount = 0;
        boolean isChildProcessing = false;
        int size = listProducts.size();
        int partCount = 0;
        for (ProductAmz productAmz : listProducts) {
            productCount ++;
            isChildProcessing = !productAmz.isParent();
            if (productCount == size - 1 || (!isChildProcessing)) {
                String fileName = null;
                if (productCount == size - 1 && partCount == 0) {
                    fileName = genExcelFileNameWithPage(aliexStoreInfo, pageIndex);
                } else {
                    fileName = genExcelFileNameWithPage(aliexStoreInfo, pageIndex, partCount + 1);
                }
                ProcessPageDataSvs.processPageData(listTemp, aliexStoreInfo, fileName, false);
                listTemp.clear();
                productCount = 1;
                partCount ++;
                listTemp.add(productAmz);
            } else {
                listTemp.add(productAmz);
            }
        }
    }
    
    public String genExcelFileNameWithPage(AliexStoreInfo aliexStoreInfo, int pageIndex, int indexPart) {
        return "";
    }
    
    public String genExcelFileNameWithPage(AliexStoreInfo aliexStoreInfo, int pageIndex) {
        return "";
    }
    
    public String genExcelFileNameForStore(AliexStoreInfo aliexStoreInfo) {
        return "";
    }
}
