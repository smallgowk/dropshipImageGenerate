/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.store.inputdata;

import java.net.URI;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author duyuno
 */
public class BaseStoreOrderInfo {
    public String link;
    public String product_type;
    public String brand_name;
    public String bullet_points;
    public String category;
    public String acc_no;
    public String status;
    public String storeSign;
    
    public boolean isCrawled;
    
    public boolean hasInfo() {
        return link != null;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBullet_points() {
        return bullet_points;
    }

    public void setBullet_points(String bullet_points) {
        this.bullet_points = bullet_points;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoreSign() {
        return storeSign;
    }

    public void setStoreSign(String storeSign) {
        this.storeSign = storeSign;
    }
    
    

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void initStoreSign() {

        URI uri = URI.create(link);

        String path = uri.getPath();

        String pageId = null;

        if (path.endsWith(".html")) {
            String[] paths = path.split(Pattern.quote("/"));
            String lastPath = paths[paths.length - 1];
            String[] lastPathParts = lastPath.split(Pattern.quote("."));
            pageId = lastPathParts[0];
        } else if (path.endsWith("search")) {
            String[] paths = path.split(Pattern.quote("/"));
            pageId = paths[paths.length - 2];
        }
        
        String searchKey = null;
        String query = uri.getQuery();

        if (query != null && !query.isEmpty()) {
            String[] queryParts = query.split(Pattern.quote("&"));
            for (String s : queryParts) {
                String[] paramsParts = s.split(Pattern.quote("="));
                if (paramsParts[0].equals("SearchText") && paramsParts.length == 2) {
                    searchKey = paramsParts[1].trim();
                }
            }
        }

        if (searchKey != null) {
            searchKey = searchKey.replaceAll(Pattern.quote(" "), "_");
        }
        
        storeSign = searchKey != null ? pageId +"_" + searchKey : pageId;
    }

    @Override
    public int hashCode() {
        return link.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseStoreOrderInfo other = (BaseStoreOrderInfo) obj;
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        return true;
    }
    
    
}
