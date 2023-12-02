/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.interfaces.ActionListener;
import com.interfaces.CrawlProcessListener;
import com.controller.thread.ProcessCrawlThread;
import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import com.models.aliex.store.inputdata.SnakeBaseStoreOrderInfo;
import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class MainController {

    public enum STATE {
        STOP,RUNNING,PAUSING
    }
       
    public STATE state = STATE.STOP;
    
    public int currentIndex;

    public ArrayList<BaseStoreOrderInfo> listOrderStore;

    ProcessCrawlThread processCrawlThread;
    CrawlProcessListener crawlProcessListener;
    ActionListener actionListener;

    public ArrayList<BaseStoreOrderInfo> getListOrderStore() {
        return listOrderStore;
    }

    public void setListOrderStore(ArrayList<BaseStoreOrderInfo> listOrderStore) {
        this.listOrderStore = listOrderStore;
        currentIndex = 0;
    }

    public void setCrawlProcessListener(CrawlProcessListener crawlProcessListener) {
        this.crawlProcessListener = crawlProcessListener;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public BaseStoreOrderInfo getCurrentStore() {
        if (listOrderStore == null) {
            return null;
        }

        return currentIndex < listOrderStore.size() ? listOrderStore.get(currentIndex) : null;
    }
    
    public void doAction() {
        
        if(null != state) switch (state) {
            case STOP:
                SnakeBaseStoreOrderInfo baseStoreOrderInfo = (SnakeBaseStoreOrderInfo) getCurrentStore();
                startCrawl(baseStoreOrderInfo);
                break;
            case RUNNING:
                pause();
                break;
            case PAUSING:
                resume();
                break;
            default:
                break;
        }
    }
    
    public boolean isStop() {
        return state == STATE.STOP;
    }

    public BaseStoreOrderInfo nextStore() {

        if (listOrderStore == null) {
            return null;
        }

        BaseStoreOrderInfo baseStoreOrderInfo = null;
        while (currentIndex < listOrderStore.size() - 1) {
            currentIndex++;
            baseStoreOrderInfo = listOrderStore.get(currentIndex);

            if (!baseStoreOrderInfo.isCrawled && baseStoreOrderInfo.hasInfo()) {
                break;
            }
        }
        return baseStoreOrderInfo;
    }

    public void startCrawl(SnakeBaseStoreOrderInfo baseStoreOrderInfo) {

        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
        
        processCrawlThread = new ProcessCrawlThread(baseStoreOrderInfo, crawlProcessListener);
        processCrawlThread.start();

        state = STATE.RUNNING;
        
        actionListener.onFinish(state);

    }

    public void stopCrawl() {
        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
        
        SnakeBaseStoreOrderInfo snakeBaseStoreOrderInfo = (SnakeBaseStoreOrderInfo) getCurrentStore();
        crawlProcessListener.onPushState(snakeBaseStoreOrderInfo.getStoreSign(), "Stopped");
        
        if (listOrderStore != null) {
            for(BaseStoreOrderInfo baseStoreOrderInfo : listOrderStore) {
                baseStoreOrderInfo.isCrawled = false;
            }
        }
        
        currentIndex = 0;
        state = STATE.STOP;
        
        actionListener.onFinish(state);
        
    }
    
    public void finish() {
        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
        
        currentIndex = 0;
        state = STATE.STOP;
    }
    
    public void pause() {
        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
        
        SnakeBaseStoreOrderInfo snakeBaseStoreOrderInfo = (SnakeBaseStoreOrderInfo) getCurrentStore();
        crawlProcessListener.onPushState(snakeBaseStoreOrderInfo.getStoreSign(), "Stopped");
        
        state = STATE.PAUSING;
        
        actionListener.onFinish(state);
    }
    
    public void resume() {
        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }
        BaseStoreOrderInfo baseStoreOrderInfo = getCurrentStore();
        processCrawlThread = new ProcessCrawlThread((SnakeBaseStoreOrderInfo) baseStoreOrderInfo, crawlProcessListener);
        processCrawlThread.start();
        
        state = STATE.RUNNING;
        
        actionListener.onFinish(state);
    }

}
