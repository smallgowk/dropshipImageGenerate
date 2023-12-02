/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

import com.config.Configs;

import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class WebGeneralFreightCalculateComponent {
    public ArrayList<OriginalLayoutResult> originalLayoutResultList;

    public ArrayList<OriginalLayoutResult> getOriginalLayoutResultList() {
        return originalLayoutResultList;
    }

    public void setOriginalLayoutResultList(ArrayList<OriginalLayoutResult> originalLayoutResultList) {
        this.originalLayoutResultList = originalLayoutResultList;
    }

    public OriginalLayoutResult getAliexPressShipping() {
        for (OriginalLayoutResult originalLayoutResult: originalLayoutResultList) {
            if (originalLayoutResult.bizData.isAliexPress()) return originalLayoutResult;
        }
        return null;
    }

    public OriginalLayoutResult getEpacketShipping() {
        for (OriginalLayoutResult originalLayoutResult: originalLayoutResultList) {
            if (originalLayoutResult.bizData.isEpacket()) return originalLayoutResult;
        }
        return null;
    }
    
    public OriginalLayoutResult getAliDirectShipping() {
        for (OriginalLayoutResult originalLayoutResult: originalLayoutResultList) {
            if (originalLayoutResult.bizData.isAliDirect()) return originalLayoutResult;
        }
        return null;
    }
    
    public BizData getBizData() {
        if (originalLayoutResultList == null || originalLayoutResultList.isEmpty()) return null;
        if (Configs.filterAliexpress == 0 && Configs.filterEpacket == 0 && Configs.filterAliDirect == 0) {
            return originalLayoutResultList.get(0).bizData;
        }

        if (Configs.filterAliexpress == 1) {
            OriginalLayoutResult originalLayoutResult = getAliexPressShipping();
            if (originalLayoutResult != null) {
                return originalLayoutResult.bizData;
            }
        }

        if (Configs.filterEpacket == 1) {
            OriginalLayoutResult originalLayoutResult = getEpacketShipping();
            if (originalLayoutResult != null) {
                return originalLayoutResult.bizData;
            }
        }
        
        if (Configs.filterAliDirect == 1) {
            OriginalLayoutResult originalLayoutResult = getAliDirectShipping();
            if (originalLayoutResult != null) {
                return originalLayoutResult.bizData;
            }
        }

        return null;
    }

    public float getShippingPrice() {
        if (originalLayoutResultList == null || originalLayoutResultList.isEmpty()) return -1;
        if (Configs.filterAliexpress == 0 && Configs.filterEpacket == 0 && Configs.filterAliDirect == 0) {
            return originalLayoutResultList.get(0).bizData.getShippingPrice();
        }

        if (Configs.filterAliexpress == 1) {
            OriginalLayoutResult originalLayoutResult = getAliexPressShipping();
            if (originalLayoutResult != null) {
                return originalLayoutResult.bizData.getShippingPrice();
            }
        }

        if (Configs.filterEpacket == 1) {
            OriginalLayoutResult originalLayoutResult = getEpacketShipping();
            if (originalLayoutResult != null) {
                return originalLayoutResult.bizData.getShippingPrice();
            }
        }
        
        if (Configs.filterAliDirect == 1) {
            OriginalLayoutResult originalLayoutResult = getAliDirectShipping();
            if (originalLayoutResult != null) {
                return originalLayoutResult.bizData.getShippingPrice();
            }
        }

        return -1;
    }
}
