/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.models.amazon.ProductAmz;
import com.interfaces.DownloadListener;
import com.utils.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PhanDuy
 */
public class DownloadManager {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    private static DownloadManager serviceManager;
    public HashMap<String, String> mapUrl = new HashMap<>();
    HashMap<String, String> mapKeyFileName = new HashMap<>();
    HashSet<String> setKey = new HashSet<>();
    HashSet<String> setKeyDone = new HashSet<>();
    
    public int totalDownloadCount = 0;
    public int totalDownloadComplete = 0;
    
    public DownloadListener downloadListener;
    
    public static DownloadManager getInstance() {
        if (serviceManager == null) {
            serviceManager = new DownloadManager();
        }
        return serviceManager;
    }
    
    public void setListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }
    
    public void put(String key, String url) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(url)) return;
        mapUrl.put(key, url);
    }
   
    public String get(String key) {
        return mapUrl.get(key);
    }
    
    public void putMapFileName(String key, String fileName) {
        mapKeyFileName.put(key, fileName);
    }
    
    public String getFileName(String key) {
        return mapKeyFileName.get(key);
    }
    
    public void execute(Thread thread) {
        executor.execute(thread);
    }
    
    public void updateCompleteKey(String key) {
        setKeyDone.add(key);
        totalDownloadComplete++;
    }
    
    public void updateDownloadKey(String key) {
        setKey.add(key);
        totalDownloadCount++;
    }
    
    public int getTotalDownload() {
        return totalDownloadCount;
    }
    
    public int getTotalComplete() {
        return totalDownloadComplete;
    }
    
    public void clearData() {
        mapUrl.clear();
        setKey.clear();
        setKeyDone.clear();
        totalDownloadCount = 0;
        totalDownloadComplete = 0;
    }
    
    public void downloadImage(String key, String target) {
        if (StringUtils.isEmpty(key) || !mapUrl.containsKey(key) || StringUtils.isEmpty(target)) {
            return;
        }
        if (!setKey.contains(key)) {
            execute(new DownloadMachine(key, get(key), target, downloadListener));
            updateDownloadKey(key);
        } else {
            totalDownloadCount++;
            totalDownloadComplete++;
            if (downloadListener != null) {
                downloadListener.onComplete(key);
            }
        }
        
    }
    
    public void downloadImageAndUpdate(ProductAmz productAmz, String targetFolder) {
        downloadImage(productAmz.main_image_key, targetFolder + productAmz.main_image_vps_name);
        downloadImage(productAmz.swatch_image_key, targetFolder + productAmz.swatch_image_vps_name);
        downloadImage(productAmz.other_image_key1, targetFolder + productAmz.other_image_vps_name1);
        downloadImage(productAmz.other_image_key2, targetFolder + productAmz.other_image_vps_name2);
        downloadImage(productAmz.other_image_key3, targetFolder + productAmz.other_image_vps_name3);
        downloadImage(productAmz.other_image_key4, targetFolder + productAmz.other_image_vps_name4);
        downloadImage(productAmz.other_image_key5, targetFolder + productAmz.other_image_vps_name5);
        downloadImage(productAmz.other_image_key6, targetFolder + productAmz.other_image_vps_name6);
        downloadImage(productAmz.other_image_key7, targetFolder + productAmz.other_image_vps_name7);
        downloadImage(productAmz.other_image_key8, targetFolder + productAmz.other_image_vps_name8);
    }
    
    public void shutDown() {
        executor.shutdown();
    }
}


class DownloadMachine extends Thread{
    
    private final String imageUrl;
    private final String targetFilePath;
    private final String key;
    private final DownloadListener downloadListener;
    
    public DownloadMachine(String key, String imageUrl, String targetFilePath, DownloadListener downloadListener) {
        this.key = key;
        this.imageUrl = imageUrl;
        this.targetFilePath = targetFilePath;
        this.downloadListener = downloadListener;
    }

    @Override
    public void run() {
        InputStream in = null;
        try {
//            System.out.println("" + imageUrl);
            in = new URL(imageUrl).openStream();
            Files.copy(in, Paths.get(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
            DownloadManager.getInstance().updateCompleteKey(key);
            if (downloadListener != null) {
                downloadListener.onComplete(key);
            }
        } catch (MalformedURLException ex) {
//            System.out.println(imageUrl + " 1: \n" + ex.getMessage());
        } catch (IOException ex) {
//            System.out.println(imageUrl + " 2: \n" + ex.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}
