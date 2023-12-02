/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.dropship.req;

import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class UpdateClientConfigReq {
    public String diskSerialNumber;
    public ArrayList<String> listBannedKeyword;
    public ArrayList<String> listMyBrands;
    public ArrayList<String> listTradeMarks;
    public ArrayList<String> listHiddenKeys;
}
