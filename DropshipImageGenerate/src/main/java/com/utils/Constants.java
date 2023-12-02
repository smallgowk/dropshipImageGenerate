/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

/**
 *
 * @author duyuno
 */
public class Constants {
    
    public interface FrameHelper {
        public static final String START_MARK = "[";
        public static final String END_MARK = "]";
        public static final String SPLIT = ",";
//        public static final byte[] PACKAGE_START_MARK = "[START]".getBytes();
        public static final byte[] PACKAGE_START_MARK = START_MARK.getBytes();
        //        public static final byte[] PACKAGE_END_MARK = "[END]".getBytes();
        public static final byte[] PACKAGE_END_MARK = END_MARK.getBytes();
        
    }
    
    public interface ConfigPaths {
        public static final String LOG4J_PROPERTY = "etc/log4j.properties";
        public static final String MTR_CORE_PROPERTY = "../etc/mtr_core.properties";
    }
    
    public interface DataType {
        public static final int STORE_COMMON = 1;
        public static final int STORE_PAGE = 2;
        public static final int STORE_FULL = 3;
    }
    
    public interface TCPAction {
        public static final String CRAWL_DATA = "CRAWL_DATA";
        public static final String SIGNUP = "SIGNUP";
        public static final String SIGNIN = "SIGNIN";
        public static final String GET_ACCOUNT = "GET_ACCOUNT";
        public static final String GET_CERT = "GET_CERT";
        public static final String UPDATE_LICENSE = "UPDATE_LICENSE";
        public static final String CHECK_LICENSE = "CHECK_LICENSE";
        public static final String CHECK_SERIAL = "CHECK_SERIAL";
        public static final String SEND_INFO = "SEND_INFO";
        public static final String STORE_INFO = "STORE_INFO";
        public static final String PAGE_INFO = "PAGE_INFO";
        public static final String PRODUCT = "PRODUCT";
        public static final String UPDATE_INFO = "UPDATE_INFO";
        public static final String SEND_USER_INFO = "SEND_USER_INFO";
    }
    
    public interface MarketType {
        public static final String ALIEX = "aliex";
        public static final String BANGGOOD = "banggood";
    }
    
    public interface ResultCode {
        
        public static final int SUCCESS = 1;
        
        //Authen
        public static final int AUTHEN_FAIL = -401;
        
        public static final int FAILURE = -1;
        public static final int FAILURE_ENCRYPT = -2;
        public static final int FAILURE_NO_STORE_INFO = -3;
        public static final int FAILURE_USER_EXISTED = -4;
        public static final int FAILURE_EMAIL_EXISTED = -5;
        
        public static final int VERSION_INVALID = -7;
        public static final int VERSION_OLD = -8;
        public static final int SERIAL_INVALID = -9;
        public static final int TIME_LIMIT = -10;
        public static final int PRODUCT_LIMIT = -11;
        public static final int HOST_EXCEPTION = -100;
    }
}
