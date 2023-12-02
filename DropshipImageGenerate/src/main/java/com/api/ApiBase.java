/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import com.api.dropship.DropParseUtil;
import com.interfaces.CallApiInterface;
import com.interfaces.PostApiInteface;
import com.models.response.ResponseObj;
import com.utils.Constants;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Admin
 */
public class ApiBase {

    private static ApiBase apiBase;

    public static ApiBase getInstance() {
        if (apiBase == null) {
            apiBase = new ApiBase();
            apiBase.init();
        }

        return apiBase;
    }

    public void init() {
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            client = HttpClientBuilder.create().setSSLContext(sslContext).build();
        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(ApiBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    HttpClient client;

    final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    }};
    SSLContext sslContext;

//    static {
//        try {
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts,
//                    new java.security.SecureRandom());
//            client = HttpClientBuilder.create().setSSLContext(sslContext).build();
//        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
//            Logger.getLogger(ApiBase.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public String sendGet(String url, CallApiInterface callApiInterface) {

        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);
            String responseStr = EntityUtils.toString(response.getEntity());
//            System.out.println("" + responseStr);
            if (callApiInterface != null) {
                callApiInterface.onSuccess(responseStr);
            }

            return responseStr;
        } catch (IOException e) {
            if (callApiInterface != null) {
                callApiInterface.onFailure(e);
            }

            return null;
        }
    }

    public void sendPostStringEntity(String url, StringEntity entity, CallApiInterface callApiInterface) {
//        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);

        try {
            HttpResponse response = client.execute(post);
            String responseStr = EntityUtils.toString(response.getEntity());
//            System.out.println("" + responseStr);
            callApiInterface.onSuccess(responseStr);
        } catch (IOException e) {
            e.printStackTrace();
            callApiInterface.onFailure(e);
        }
    }

    public String sendPostStringEntity(String url, StringEntity entity, String[][] headers, CallApiInterface callApiInterface) {
        HttpPost post = new HttpPost(url);

        if (headers
                != null) {
            for (String[] header : headers) {
                if (header[0] != null && header[1] != null) {
                    post.setHeader(header[0], header[1]);
                }
            }
        }

        post.setEntity(entity);

        try {
//            long start = System.currentTimeMillis();
            HttpResponse response = client.execute(post);
//            long end = System.currentTimeMillis();

//            System.out.println("Time Request: " + (end - start));
            String responseStr = EntityUtils.toString(response.getEntity());
            if (callApiInterface != null) {
                callApiInterface.onSuccess(responseStr);
            }
            return responseStr;
        } catch (IOException e) {
            e.printStackTrace();

            if (callApiInterface != null) {
                callApiInterface.onFailure(e);
            }
            return null;
        }
    }
    
    public ResponseObj sendPostStringEntityIgnoreError(String url, StringEntity entity, String[][] headers, CallApiInterface callApiInterface) {
        HttpPost post = new HttpPost(url);

        if (headers
                != null) {
            for (String[] header : headers) {
                if (header[0] != null && header[1] != null) {
                    post.setHeader(header[0], header[1]);
                }
            }
        }

        post.setEntity(entity);

        try {
            HttpResponse response = client.execute(post);
            String responseStr = EntityUtils.toString(response.getEntity());
            if (callApiInterface != null) {
                callApiInterface.onSuccess(responseStr);
            }
            return DropParseUtil.parseRespone(responseStr);
        } catch (IOException e) {
            e.printStackTrace();

            if (callApiInterface != null) {
                callApiInterface.onFailure(e);
            }
            
            if (e instanceof HttpHostConnectException) {
                ResponseObj ro = new ResponseObj();
                ro.setResultCode(Constants.ResultCode.HOST_EXCEPTION);
                return ro;
            } else {
                return null;
            }
        }
    }
    
    public Object sendPostStringEntityIgnoreErrorWithBaseResponse(String url, StringEntity entity, String[][] headers, PostApiInteface postApiInteface) {
        HttpPost post = new HttpPost(url);

        if (headers
                != null) {
            for (String[] header : headers) {
                if (header[0] != null && header[1] != null) {
                    post.setHeader(header[0], header[1]);
                }
            }
        }

        post.setEntity(entity);

        try {
            HttpResponse response = client.execute(post);
            String responseStr = EntityUtils.toString(response.getEntity());
//            return DropParseUtil.parseAliexProductFullRespone(responseStr);
            return postApiInteface.parseJsonAfterSuccess(responseStr);
        } catch (IOException e) {
            e.printStackTrace();
            
            if (e instanceof HttpHostConnectException) {
                ResponseObj ro = new ResponseObj();
                ro.setResultCode(Constants.ResultCode.HOST_EXCEPTION);
                return null;
            } else {
                return null;
            }
        }
    }
}
