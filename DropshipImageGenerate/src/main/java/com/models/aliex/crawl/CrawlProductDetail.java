/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.crawl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author PhanDuy
 */
public class CrawlProductDetail {
    private String id;
    private ArrayList<String> listRelatedSearch;
    private HashMap<String, String> hashProperties;
    private String brandName;
    private String html_description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getListRelatedSearch() {
        return listRelatedSearch;
    }

    public void setListRelatedSearch(ArrayList<String> listRelatedSearch) {
        this.listRelatedSearch = listRelatedSearch;
    }

    public HashMap<String, String> getHashProperties() {
        return hashProperties;
    }

    public void setHashProperties(HashMap<String, String> hashProperties) {
        this.hashProperties = hashProperties;
    }

    

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getHtml_description() {
        return html_description;
    }

    public void setHtml_description(String html_description) {
        this.html_description = html_description;
    }
    
    
}
