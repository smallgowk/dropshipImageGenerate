/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.state;

/**
 *
 * @author PhanDuy
 */
public class ProcessState {
    
    public String storeSign;
    public String accNo;
    public int totalPage;
    public int currentPage;
    public int percent;
    public String status;
    
    public static ProcessState createInstance(String storeSign, String accNo) {
        ProcessState processState = new ProcessState();
        processState.setStoreSign(storeSign);
        processState.setAccNo(accNo);
        
        return processState;
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

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
            
    public String getStatus() {
        
        return status != null ? status : "";
//        
//        if(currentPage == 0) {
//            currentPage = 1;
//        }
//        
//        return "Page (" + currentPage + "/" + totalPage + ") " + percent + "%";
    }
    
}
