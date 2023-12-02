/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

import com.models.aliex.store.AliexPageInfo;
import com.models.aliex.store.AliexStoreCommon;
import java.util.ArrayList;

/**
 *
 * @author duyuno
 */
public class AliexStoreFullInfo {
    public AliexStoreCommon aliexStoreCommon;
    
    public ArrayList<AliexPageInfo> listPages;

    public ArrayList<AliexPageInfo> getListPages() {
        return listPages;
    }

    public void setListPages(ArrayList<AliexPageInfo> listPages) {
        this.listPages = listPages;
    }
    
    public void addAliexPageInfo(AliexPageInfo aliexPageInfo) {
        if(listPages == null) {
            listPages = new ArrayList<>();
        }
        
        listPages.add(aliexPageInfo);
    }

    public AliexStoreCommon getAliexStoreCommon() {
        return aliexStoreCommon;
    }

    public void setAliexStoreCommon(AliexStoreCommon aliexStoreCommon) {
        this.aliexStoreCommon = aliexStoreCommon;
    }
    
    
}
