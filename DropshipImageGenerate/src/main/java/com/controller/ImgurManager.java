/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.google.gson.Gson;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author PhanDuy
 */
public class ImgurManager {
    ExecutorService executor = Executors.newFixedThreadPool(1);
    private static ImgurManager serviceManager;
    HashMap<String, String> mapUrl = new HashMap<>();
    HashMap<String, String> mapKeyFileName = new HashMap<>();
    HashSet<String> setKey = new HashSet<>();
    HashSet<String> setKeyDone = new HashSet<>();
    
    public int totalDownloadCount = 0;
    public int totalDownloadComplete = 0;
    
    public DownloadListener downloadListener;
    
    public static ImgurManager getInstance() {
        if (serviceManager == null) {
            serviceManager = new ImgurManager();
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
            execute(new ImgUrMachine(key, get(key), target, downloadListener));
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


class ImgUrMachine extends Thread {
    
    private final String imageUrl;
    private final String targetFilePath;
    private final String key;
    private final DownloadListener downloadListener;
    
    public ImgUrMachine(String key, String imageUrl, String targetFilePath, DownloadListener downloadListener) {
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
            
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("image", imageUrl)
                    .addFormDataPart("type", "url")
//                    .addFormDataPart("name", key + ".jpg")
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.imgur.com/3/image")
                    .method("POST", body)
                    .addHeader("Authorization", "Client-ID d85a4c4f0d83090")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                ImgUrResponse imgUrResponse = gson.fromJson(response.body().string(), ImgUrResponse.class);
//            System.out.println("" + response.body().string());
                System.out.println("Convert: " + imageUrl + " ===> " + imgUrResponse.data.link);
            } else {
                System.out.println("Convert fail: " + imageUrl + "\n" + response.toString());
            }
            
            
//            in = new URL(imageUrl).openStream();
//            Files.copy(in, Paths.get(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
            ImgurManager.getInstance().updateCompleteKey(key);
            if (downloadListener != null) {
                downloadListener.onComplete(key);
            }
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
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

class ImgUrResponse {
    public ImgUrData data;
}

class ImgUrData {
    public String link;
}
