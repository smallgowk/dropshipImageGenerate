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
public class ProductGroupsResult {

    public ArrayList<ProductGroup> groups;

    public ArrayList<String> getRelatedSearch() {

        if (groups == null || groups.isEmpty()) {
            return null;
        }

        ArrayList<String> result = null;

        for (ProductGroup aliexScriptCrossGroup : groups) {
            String keyword = aliexScriptCrossGroup.getRelatedWord();
            if(keyword != null && !keyword.isEmpty()) {
                if(result == null) {
                    result = new ArrayList<>();
                }
                result.add(keyword);
            }
            
            if (result != null && result.size() >= 100) {
                break;
            }
            
        }

        return result;
    }
}
