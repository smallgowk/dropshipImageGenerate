/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.imgur;

/**
 *
 * @author PhanDuy
 */
public class ImgUrImage {
    public String id;
    public String name;
    public String link;
    
    public String getSku(int productIndex, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("-").append(productIndex);
        if (index > 0) {
            sb.append("10000").append(index);
        }
        return sb.toString();
    }
    
    public String getParentSku(int productIndex, int index) {
        if (index == 0) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("-").append(productIndex);
        return sb.toString();
    }
    
    public String getName(int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (index == 0) return sb.toString();
        sb.append(" - Size ");
        sb.append(getSizeName(index));
        return sb.toString();
    }
    
    public String getType(int index) {
        if (index == 0) {
            return "Parent";
        } else {
            return "Child";
        }
    }
    
    public String getSizeName(int index) {
        switch (index) {
            case 0:
                return "";
            case 1:
                return "S";
            case 2:
                return "M";
            case 3:
                return "L";
            case 4:
                return "XL";
            case 5:
                return "2XL";
            case 6:
                return "3XL";
            case 7:
                return "4XL";
            case 8:
                return "5XL";
        }
        return "";
    }
}
