/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

import com.utils.CheckingDataUtil;
import com.utils.StringUtils;

/**
 *
 * @author PhanDuy
 */
public class ProductGroup {

//    public String url;
    public String name;

    public String getRelatedWord() {
        if (name == null || !StringUtils.isCharactorOnly(name) || CheckingDataUtil.containBannedKeyword(name) != null) {
            return null;
        }
        if (name.contains("wholesale")) {
            name = name.replaceAll("wholesale", "");
        }

        if (name.contains("price")) {
            name = name.replaceAll("price", "");
        }

        if (name.contains("promotion")) {
            name = name.replaceAll("promotion", "");
        }

        if (!name.isEmpty()) {
            return StringUtils.getPrefixCapitalWord(name);
        } else {
            return null;
        }
    }
}
