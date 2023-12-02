/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaces;

/**
 *
 * @author PhanDuy
 */
public interface CrawlListener {
    public void startCrawl(String link);
    public void finishCrawl(Object data);
}
