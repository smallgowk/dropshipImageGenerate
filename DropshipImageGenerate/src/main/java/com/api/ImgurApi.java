/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import com.config.Configs;
import com.google.gson.Gson;
import com.models.imgur.Album;
import com.models.imgur.CreateAlbumResponse;
import com.models.imgur.GetAlbumsResponse;
import com.models.imgur.ImgUrImage;
import com.models.imgur.ImgurAccessToken;
import com.models.imgur.ListImagesResponse;
import java.io.IOException;
import java.util.ArrayList;
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
public class ImgurApi {
    
    public static void generateToken() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("refresh_token", Configs.imgurRefreshToken)
                .addFormDataPart("client_id", Configs.imgurClientId)
                .addFormDataPart("client_secret", Configs.imgurClientSecret)
                .addFormDataPart("grant_type", "refresh_token")
                .build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/oauth2/token")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() == null) return ;
        Gson gson = new Gson();
        ImgurAccessToken imgurAccessToken = gson.fromJson(response.body().string(), ImgurAccessToken.class);
        ImgurInfo.accessToken = imgurAccessToken.access_token;
        ImgurInfo.username = imgurAccessToken.account_username;
        ImgurInfo.accountId = imgurAccessToken.account_id;
        System.out.println("ImgurInfo.accessToken: " + ImgurInfo.accessToken);
    }
    
    public static CreateAlbumResponse createAlbum(String albumName) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("title", albumName)
                .build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/album")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + ImgurInfo.accessToken)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() == null) return null;
        Gson gson = new Gson();
        CreateAlbumResponse createAlbumResponse = gson.fromJson(response.body().string(), CreateAlbumResponse.class);
        return createAlbumResponse;
    }
    
    public static ArrayList<Album> getAllAlbums() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/account/" + ImgurInfo.username + "/albums")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + ImgurInfo.accessToken)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() == null) return null;
        Gson gson = new Gson();
        GetAlbumsResponse getAlbumsResponse = gson.fromJson(response.body().string(), GetAlbumsResponse.class);
        if (getAlbumsResponse != null && getAlbumsResponse.data != null) {
            return getAlbumsResponse.data;
        }
        return null;
    }
    
    public static ArrayList<ImgUrImage> getImagesFromAlbums(Album album) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/album/" + album.id + "/images")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + ImgurInfo.accessToken)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() == null) return null;
        Gson gson = new Gson();
        ListImagesResponse listImagesResponse = gson.fromJson(response.body().string(), ListImagesResponse.class);
        if (listImagesResponse != null && listImagesResponse.data != null) {
            return listImagesResponse.data;
        }
        return null;
    }
}
