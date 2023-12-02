/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.imgur;

import com.models.imgur.Album;
import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class ImgurUtils {
    
    public static Album findAlbum(ArrayList<Album> listAlbums, String albumTitle) {
        if (listAlbums == null || listAlbums.isEmpty()) return null;
        for (Album album : listAlbums) {
            if (albumTitle.equals(album.title)) {
                return album;
            }
        }
        return null;
    }
}
