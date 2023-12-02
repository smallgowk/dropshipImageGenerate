/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.transform;

import com.config.Configs;
import com.models.amazon.ProductAmz;
import com.models.aliex.store.AliexStoreInfo;
import com.models.response.TransformResponse;
import com.utils.ExcelUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author PhanDuy
 */
public class ProcessPageDataSvs {

    public static void processPageData(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo, int pageIndex) {
        String fileName = aliexStoreInfo.genExcelFileNameWithPage(pageIndex, false);
        processPageData(listProducts, aliexStoreInfo, fileName, false);
    }
    
    public static void processPageData(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo) {
        String fileName = aliexStoreInfo.genExcelFileNameForStore(false);
        processPageData(listProducts, aliexStoreInfo, fileName, true);
    }
    
    public static void processPageData(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo, String fileName, boolean isSaveAll) {
        
    }
    
    public static void processPageErrorData(ArrayList<TransformResponse> listErrorProducts, AliexStoreInfo aliexStoreInfo, int pageIndex) {
        String fileName = aliexStoreInfo.genExcelFileNameWithPage(pageIndex, true);
        processPageErrorData(listErrorProducts, fileName);
    }
    
    public static void processPageErrorData(ArrayList<TransformResponse> listErrorProducts, AliexStoreInfo aliexStoreInfo) {
        String fileName = aliexStoreInfo.genExcelFileNameForStore(true);
        processPageErrorData(listErrorProducts, fileName);
    }
    
    public static void processPageErrorData(ArrayList<TransformResponse> listProducts, String fileName) {
        try {
            ExcelUtils.saveErrorProducts(fileName, listProducts);
        } catch (EncryptedDocumentException | IOException ex) {
            Logger.getLogger(ProcessPageDataSvs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
