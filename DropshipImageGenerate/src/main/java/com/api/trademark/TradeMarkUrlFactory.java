/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.trademark;


import com.config.Configs;
import com.utils.Utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author duyuno
 */
public class TradeMarkUrlFactory {
    
    public static final String MARKER_CHECK_TRADEMARK_WORD_URL_PATTERN = "https://www.markerapi.com/api/v1/trademark/search/{keyword}/username/{username}/password/{password}";
    public static final String MARKER_CHECK_TRADEMARK_ALLSTRING_URL_PATTERN = "https://www.markerapi.com/api/v1/trademark/searchall/{keyword}/wordcount/{wordcount}/limit/{limit}/username/{username}/password/{password}";
    
    public static final String KEY_WORD_PATTERN = "{keyword}";
    public static final String USERNAME_PATTERN = "{username}";
    public static final String PASSWORD_PATTERN = "{password}";
    public static final String WORDCOUNT_PATTERN = "{wordcount}";
    public static final String LIMIT_PATTERN = "{limit}";
    
    public static final String AUTHEN_FILE = "/marker_api.conf";
    
    public static String checkTradeMarkWordUrl = null;
    public static String checkTradeMarkAllStringUrl = null;
    
    static {
        
        Properties cnfParamsTmp = new Properties();
        FileInputStream propsFile = null;
        try {
            
            InputStream inputStream = Configs.class.getResourceAsStream(AUTHEN_FILE);
            InputStreamReader clientSecretReader = new InputStreamReader(inputStream);
            
//            propsFile = new FileInputStream(AUTHEN_FILE);
            cnfParamsTmp.load(clientSecretReader);
            
            String username = cnfParamsTmp.getProperty("username", "duyuno");
            String password = cnfParamsTmp.getProperty("password", "nZMFRJdbtk");
            
            checkTradeMarkWordUrl = MARKER_CHECK_TRADEMARK_WORD_URL_PATTERN.replace(USERNAME_PATTERN, username).replace(PASSWORD_PATTERN, password);
            checkTradeMarkAllStringUrl = MARKER_CHECK_TRADEMARK_ALLSTRING_URL_PATTERN.replace(USERNAME_PATTERN, username).replace(PASSWORD_PATTERN, password);
            
            
        } 
        catch (FileNotFoundException ex) {        
            Logger.getLogger(TradeMarkUrlFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TradeMarkUrlFactory.class.getName()).log(Level.SEVERE, null, ex);
        }        
        finally {
            if (propsFile != null) {
                try {
                    propsFile.close();
                } catch (IOException e) {
                    // thong bao dong file loi
                }
            }
        }
        
//        try {
//            File file = new File(AUTHEN_FILE);
//
//            BufferedReader br = new BufferedReader(new FileReader(file));
//
//            String st;
//            while ((st = br.readLine()) != null) {
//                if (st != null && !st.isEmpty()) {
//                    
//
//                }
//            }
//
//        } catch (Exception ex) {
//
//        }
    }
    
    
    public static String createCheckTradeMarkWordUrl(String keyword) {
        String keyParams = URLEncoder.encode(keyword);
        return checkTradeMarkWordUrl.replace(KEY_WORD_PATTERN, keyParams);
    }
    
    public static String createCheckTradeMarkAllStringUrl(String keyword, int count, int limit) {
        keyword = Utils.removeSpecialChar(keyword);
        String keyParams = URLEncoder.encode(keyword);
        return checkTradeMarkAllStringUrl.replace(KEY_WORD_PATTERN, keyParams).replace(WORDCOUNT_PATTERN, "" + count).replace(LIMIT_PATTERN, "" + limit);
    }

}
