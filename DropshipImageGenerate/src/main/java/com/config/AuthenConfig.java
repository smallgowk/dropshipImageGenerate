/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.config;


/**
 *
 * @author PhanDuy
 */
public class AuthenConfig {
    
    public static final int BASIC = 0;// Basic data
    public static final int BASIC_MERCHANT = 1;// Basic data + merchant
    public static final int BASIC_RELATE = 2;// Basic data + relate
    public static final int BASIC_MERCHANT_RELATE = 3;// Basic data + Merchant + relate
    
    public static int userLvel = BASIC;
    public static String merchantUser;
    public static String merchantPassword;
    public static String apiKey;
    
    public static boolean isAllowGetMerchant() {
        return userLvel == BASIC_MERCHANT || userLvel == BASIC_MERCHANT_RELATE;
    }
    
    public static boolean isAlloeGetRelate() {
        return userLvel == BASIC_RELATE || userLvel == BASIC_MERCHANT_RELATE;
    }
}
