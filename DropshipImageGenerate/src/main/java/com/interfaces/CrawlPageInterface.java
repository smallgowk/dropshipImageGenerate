/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaces;

import com.models.aliex.store.AliexPageInfo;



/**
 *
 * @author Admin
 */
public interface CrawlPageInterface {
    public void onError(Object message);
    public void onComplete(Object message);
    public void onMessagePopup(String message);
    public void updateProcessState(String message);
    public void onStart();
    public void onReset();
    public void onMessageInfo(String message);
    public boolean onNextStore();
    public void sendPageInfoToServer(AliexPageInfo data);
}
