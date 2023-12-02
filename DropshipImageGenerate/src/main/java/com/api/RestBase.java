/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import com.google.gson.Gson;
import com.models.request.TransformAliexCrawlDataReq;
import com.models.response.TransformCrawlResponse;
import com.models.response.TransformResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author PhanDuy
 */
public class RestBase {
    private static RestBase restBase;
    
    RestTemplate restTemplate = null;

    public static RestBase getInstance() {
        if (restBase == null) {
            restBase = new RestBase();
        }
        return restBase;
    }

    public RestBase() {
        restTemplate = new RestTemplate();
    }
    
    public <T extends Object> T getForObject(String url, Class<T> typeResponse) {
        try {
            return restTemplate.getForObject(url, typeResponse);
        } catch (Exception e) {
            return null;
        }
    }
    
    public <T extends Object> T postForObject(String url, Object req, Class<T> typeResponse) {
        try {
            return restTemplate.postForObject(url, req, typeResponse);
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException) {
                Gson gson = new Gson();
                String responseBody = ((HttpClientErrorException)e).getResponseBodyAsString();
                T res = gson.fromJson(responseBody, typeResponse);
                return res;
            } else if (e instanceof HttpServerErrorException) {
                Gson gson = new Gson();
                String responseBody = ((HttpServerErrorException)e).getResponseBodyAsString();
                T res = gson.fromJson(responseBody, typeResponse);
                if (req instanceof TransformAliexCrawlDataReq) {
                    TransformCrawlResponse data = new TransformCrawlResponse();
                    data.productId = ((TransformAliexCrawlDataReq) req).id;
                    if (res instanceof TransformResponse) {
                        ((TransformResponse)res).setData(data);
                    }
                }
                return res;
            } else {
                return null;
            }
        }
    }
}
