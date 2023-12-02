/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.crawl.machine;

import com.config.Configs;
import com.interfaces.CrawlPageInterface;
import com.run.StartClientApp;
import com.controller.crawl.aliex.AliexCrawlSvs;
import com.utils.CookieUtil;
import com.utils.DialogUtil;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;

/**
 *
 * @author duyuno
 */
public class CrawlerMachine {

    public static final String EDT_ACCOUNT_ID_MERCHANT = "field-email-3#";
    public static final String EDT_PASSWORD_MERCHANT = "field-pass-3#";
    public static final String BTN_SUBMIT_MERCHANT = "submitButton";

    public static final String EDT_ACCOUNT_ID_ALIEX = "fm-login-id";
    public static final String EDT_PASSWORD_ALIEX = "fm-login-password";
    public static final String BTN_SUBMIT_ALIEX = "fm-login-submit";

    public static final int STATE_SUCCESS = 0;
    public static final int STATE_CAN_NOT_INIT_DRIVER = 1;
    public static final int STATE_OTHER_ERROR = 2;

    public Map<String, String> cookies;
    public WebDriver driver;
    public JavascriptExecutor executor;
    public Actions actions;
    public String currentAccount;

    public static int currentAccountIndex;
    public static int startAccountIndex;

    public CrawlerMachine() {

    }

    public void autoLoginMerchant() {
        if (driver == null) {
            return;
        }

        WebElement webElementAccount = driver.findElement(By.id(EDT_ACCOUNT_ID_MERCHANT));
        WebElement webElementPassword = driver.findElement(By.id(EDT_PASSWORD_MERCHANT));
        WebElement webElementSubmit = driver.findElement(By.id(BTN_SUBMIT_MERCHANT));

        if (webElementAccount != null) {
            webElementAccount.sendKeys("hiepkk3@gmail.com");
        }

        if (webElementPassword != null) {
            webElementPassword.sendKeys("hoanghiep9x");
        }

        webElementSubmit.submit();
    }

    public boolean autoLoginAliex(String redirectUrl) {
        String[] account = findAccountActive();

        if (account == null) {
            return false;
        }
        currentAccount = account[0];
        return loginAliex(account[0], account[1], redirectUrl);
    }

    private String[] findAccountActive() {
        for (String[] account : Configs.listAccount) {
            if (!Configs.hashMapAccountState.get(account[0])) {
                return account;
            }
        }

        return null;
    }
    
    public boolean initDriver(String profileName) throws Exception{

        if (driver != null) {
            System.out.println("driver ready");
            return true;
        }
//        System.out.println("init driver");
//        ChromeOptions options = new ChromeOptions();
//        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//        options.addArguments("start-maximized");
//        options.addArguments("disable-infobars");
//        options.addArguments("disable-javascript");
//        options.addArguments("user-data-dir=C:\\Users\\PhanDuy\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 16");
//
////        options.addArguments("start-maximized");
////        options.addArguments("enable-automation");
////        options.addArguments("--no-sandbox");
////        options.addArguments("--disable-infobars");
////        options.addArguments("--disable-dev-shm-usage");
////        options.addArguments("--disable-browser-side-navigation");
////        options.addArguments("--disable-gpu");
//        options.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);

//        String userProfile = "C:\\Users\\PhanDuy\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 16\\";
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("user-data-dir=" + userProfile);
        if (profileName != null) {
            String userDir = System.getProperty("user.home") + Configs.pathChar + "AppData/Local/Google/Chrome/User Data/";
            options.addArguments("--user-data-dir=" + userDir);
            options.addArguments("--profile-directory=" + profileName);
        }

        options.addArguments("--start-maximized");
        options.addArguments("disable-infobars");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("disable-javascript");
        options.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);

//        options.addArguments("disable-javascript");
//        options.addArguments("user-data-dir", Configs.CONFIG_FOLDER_PATH);
//        options.setCapability("pageLoadStrategy", "eager");
        try {
            driver = new ChromeDriver(options);
            executor = (JavascriptExecutor) driver;
            actions = new Actions(driver);

            return true;
        } catch (Exception ex) {
            throw ex;
        }

    }
    
    private boolean loginAliex(String account, String password, String redirectUrl) {
//        if (driver == null) {
//            return false;
//        }

        if (!goToLogin(redirectUrl)) {
            return false;
        }

//        List<WebElement> we = driver.findElements(By.xpath("//iframe[@id='alibaba-login-box']"));
//        driver.switchTo().frame(we.get(0)); //Switch to iframe.
//        WebElement activeElement = driver.switchTo().activeElement();
        WebElement activeElement = driver.findElement(By.id("login"));

        WebElement webElementAccount = activeElement.findElement(By.id("fm-login-id"));
        WebElement webElementPassword = activeElement.findElement(By.id("fm-login-password"));
//        WebElement webElementSubmit = activeElement.findElement(By.id("submit"));
//        WebElement webElementSubmit = activeElement.findElement(By.className("fm-button fm-submit password-login"));
        WebElement webElementSubmit = activeElement.findElement(By.className("fm-btn"));

        if (webElementAccount != null) {
            webElementAccount.sendKeys(account);
        }

        if (webElementPassword != null) {
            webElementPassword.sendKeys(password);
        }

//        webElementSubmit.submit();
        webElementSubmit.click();

        String curUrl = getCurrentUrl();

        Configs.hashMapAccountState.put(account, Boolean.TRUE);

        if (curUrl.contains("login")) {
            return false;
        } else {
            return true;
        }
//        driver.switchTo().parentFrame();
    }

    public boolean nextPage() {
        try {
            WebElement activeElement = driver.findElement(By.className("ui-pager-next"));
            if (activeElement != null) {
                activeElement.click();
                return true;
            } else {
                return false;
            }
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }

    }

//    public void close() {
//        driver.manage().deleteAllCookies();
////        driver.quit();
//        driver.close();
//        driver = null;
//        Configs.hashMapAccountState.put(currentAccount, Boolean.TRUE);
//    }
    public boolean initDriver() {

        if (driver != null) {
            System.out.println("driver ready");
            return true;
        }
        System.out.println("init driver");
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("disable-javascript");

//        options.addArguments("start-maximized");
//        options.addArguments("enable-automation");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-infobars");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--disable-browser-side-navigation");
//        options.addArguments("--disable-gpu");

        options.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);
        
//        options.addArguments("disable-javascript");
//        options.addArguments("user-data-dir", Configs.CONFIG_FOLDER_PATH);
//        options.setCapability("pageLoadStrategy", "eager");
        try {
            driver = new ChromeDriver(options);
            actions = new Actions(driver);

            return true;
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return false;
        }

    }
    
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void saveNewCookies() {
        cookies = CookieUtil.getCookiesFromDriver(driver);

        if (cookies != null) {
            CookieUtil.saveCookies(cookies);
        }
    }
    
    public void saveNewCookies(String fileName) {
        HashMap<String, String> cookies = CookieUtil.getCookiesFromDriver(driver);

        if (cookies != null) {
            CookieUtil.saveCookies(cookies, fileName);
        }
    }

    public boolean isHasCookies() {
        return false;
    }

    public void clearCache() {
    }

    public void refreshCookies() {
        if (cookies != null) {
            cookies.clear();
        }
        rechiveCookies();
    }
    
    public boolean rechiveCookies() {
//        long start = System.currentTimeMillis();
            cookies = CookieUtil.getCookiesFromDriver(driver);
//            System.out.println("Time get cookies: " + (System.currentTimeMillis() - start));
//
//            if (cookies == null) {
//                if (driver == null) {
//                    initDriver();
//                }
//
////                if (driver.manage() != null) {
////                    driver.manage().deleteAllCookies();
////                }
//                cookies = CookieUtil.getCookies(driver);
//
//                if (cookies != null) {
//                    CookieUtil.saveCookies(cookies);
//                } else {
//                    return null;
//                }
//
////                if (cookies == null) {
////                    driver.get("https://login.aliexpress.com");
////                    return null;
////                }
//            }

            if (cookies == null) {
                if (driver == null) {
                    initDriver();
                }
                return false;
            } else {
                if(!CookieUtil.isValidCookie(cookies)) {
                    return false;
                } else {
//                    start = System.currentTimeMillis();
                    CookieUtil.saveCookies(cookies);
//                    System.out.println("Time save cookies: " + (System.currentTimeMillis() - start));
                    return true;
                }
            }
    }

    public Document processPage(String URL) {
        
        if(!rechiveCookies()) {
            System.out.println("Invalid cookies!");
            return null;
        }

        Document doc = null;
        try {
//            driver.get(URL);
//            return Jsoup.parse(driver.getPageSource());
            Connection connection = Jsoup.connect(URL)
                    .validateTLSCertificates(false)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .header("Content-Type", "text/html; charset=utf-8")
                    .header("Accept", "text/html,text/plain,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Language", "*")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .cookies(cookies);
//            Thread.sleep(500);
            doc = connection.get();
            return doc;
        } catch (IOException e1) {
            return null;
        }
    }
    
    public Document processPage(String URL, String cookieName) {
        
        if(!rechiveCookies()) {
            System.out.println("Invalid cookies!");
            return null;
        }

        Document doc = null;
        try {
//            driver.get(URL);
//            return Jsoup.parse(driver.getPageSource());
            Connection connection = Jsoup.connect(URL)
                    .validateTLSCertificates(false)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .header("Content-Type", "text/html; charset=utf-8")
                    .header("Accept", "text/html,text/plain,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Language", "*")
                    .header("Accept-Encoding", "gzip, deflate, br");
//            Thread.sleep(500);
            doc = connection.get();
            return doc;
        } catch (IOException e1) {
            return null;
        }
    }

//    public void updateCookies(String fileName) {
//
//        Map<String, String> newCookies = CookieUtil.getCookies(driver);
//
//        if (newCookies != null && newCookies.containsKey("JSESSIONID")) {
//            CookieUtil.deleteCookies(fileName);
//            CookieUtil.saveCookies(newCookies, fileName);
//        }
//
//    }
    public boolean isReady() {
//        return driver != null && !isBrowserClosed(driver);
        return driver != null;
    }

    public boolean isBrowserClosed(WebDriver driver) {
        boolean isClosed = false;
        try {
            driver.getTitle();
        } catch (Exception ex) {
            isClosed = true;
        }
        return isClosed;
    }

    public String getCurrentUrl() {
        if (driver != null) {
            return driver.getCurrentUrl();
        }

        return null;
    }

    public void goToPage(String url, Map<String, String> cookies, CrawlPageInterface crawlPageInterface) {

        try {
            if (driver != null) {

//                if (isBrowserClosed(driver)) {
//                    initDriver();
//                }
                driver.get(url);

//                Map<String, String> cookies = CookieUtil.getCookiesFromCache(CookieUtil.COOKIE_MERCHANT);
//                if(cookies)
//                if (cookies != null) {
//                    addCookies(CookieUtil.getCookiesFromCache(CookieUtil.COOKIE_MERCHANT));
//                    driver.navigate().to(url);
//                }
            } else {
                initDriver();
//                addCookies(CookieUtil.getCookiesFromCache(CookieUtil.COOKIE_MERCHANT));
                driver.get(url);

//                if (cookies != null) {
//                    addCookies(CookieUtil.getCookiesFromCache(CookieUtil.COOKIE_MERCHANT));
//                    driver.get(url);
//                }
            }

        } catch (Exception ex) {
//            ex.printStackTrace();
            if (crawlPageInterface != null) {
                crawlPageInterface.onMessageInfo(ex.toString());
            }
        }

    }
    
    public boolean goToPage(String url) {
        
//        if(!AliexCrawlSvs.getInstance().rechiveCookies()) {
//            DialogUtil.showInfoMessage(null, "Vui lòng đăng nhập Aliexpress!");
//            return false;
//        }
        
        try {
//            if (!isReady()) {
//                initDriver();
//            }
            if (driver != null) {
                driver.get(url);
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public void scrollPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,5000)");
    }

    public void scrollPage(int dy) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + dy + ")");
    }
    public void addCookies(Map<String, String> cookiesMap) {

        if (cookiesMap == null) {
            return;
        }
        Iterator it = cookiesMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            driver.manage().deleteCookieNamed((String) pair.getKey());
            driver.manage().addCookie(new Cookie((String) pair.getKey(), (String) pair.getValue()));
//            System.out.println(pair.getKey() + " = " + pair.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
        }
    }

//    public void initCookies() {
//        if (cookies == null) {
//            cookies = CookieUtil.getCookies(driver);
//        }
//    }
    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,5000)");
    }

    public String getPageSource() {
        if (driver != null) {
            return driver.getPageSource();
        }

        return null;
    }

    String retrieveCookie(URL url) throws URISyntaxException, IOException {
        String cookieValue = null;

        CookieHandler handler = CookieHandler.getDefault();
        if (handler != null) {
            Map<String, List<String>> headers = handler.get(url.toURI(), new HashMap<String, List<String>>() {
            });
            List<String> values = headers.get("Cookie");
            for (Iterator<String> iter = values.iterator(); iter.hasNext();) {
                String v = iter.next();

                if (cookieValue == null) {
                    cookieValue = v;
                } else {
                    cookieValue = cookieValue + ";" + v;
                }
            }
        }
        return cookieValue;
    }

    public boolean goToLogin(String redirectUrl) {
        System.out.println("Go To Login");
        
        if (driver == null) {
            initDriver();
        }
//        driver.get("https://passport.aliexpress.com/mini_login.htm?lang=en_us&appName=aebuyer&appEntrance=default&styleType=auto&bizParams=&notLoadSsoView=false&notKeepLogin=true&isMobile=false");
        if (driver != null) {
            CookieUtil.deleteCookies();
            driver.manage().deleteAllCookies();
            driver.get("https://login.aliexpress.com/?flag=1" + (StringUtils.isEmpty(redirectUrl) ? "" : "&return=" + redirectUrl));
            return true;
        } else {
//            int option = DialogUtil.showOptionsQuestionDialog(null, null, "Phiên bản trình duyệt chrome và phiên bản chromedriver không tương thích.\n"
//                    + "Vui lòng download chromedriver theo phiên bản trình duyệt chrome trên máy tính!\n"
//                    + "Sau đó thực hiện cập nhật như hướng dẫn.",
//                    "Cập nhật", "Đóng");
//            if (option == 0) {
//
//                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
//                    try {
//                        Desktop.getDesktop().browse(new URI("http://103.130.213.180/dropship2G.html"));
//                        Desktop.getDesktop().browse(new URI("https://chromedriver.chromium.org/downloads?fbclid=IwAR1I_wfvE-sipnM0-ZsU-nTBZhLYX3exGq9u1ive6mEDZ8922fWQQ_B1p1M"));
//                    } catch (IOException | URISyntaxException ex) {
//                        Logger.getLogger(StartClientApp.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
            return false;
        }

    }

    public void aliexCookies() {

//            Map<String, String> mapCookies = new HashMap<String, String>();
//            mapCookies.put("_bl_uid", "XbjRFrg0fm5yXjmI19vnuwm3dLhp");
//            mapCookies.put("ali_apache_id", "11.180.246.132.154865454374.166419.1");
//            mapCookies.put("intl_locale", "en_US");
//            mapCookies.put("_ga", "GA1.2.772457543.1548654546");
//            mapCookies.put("_gid", "GA1.2.1463909624.1548654546");
//            mapCookies.put("cna", "7eGIFEuEJQQCAXUA1MzPAuOA");
//            mapCookies.put("_uab_collina", "154865457902592921864446");
//            mapCookies.put("aep_common_f", "kZ7jmchDS8jOGj/yWHC6ANB3hXqODRhRtw3zn+AGV6/bbFQB1ko8+w==");
//            mapCookies.put("_ym_uid", "1548667603103185325");
//            mapCookies.put("_ym_d", "1548667603");
//            mapCookies.put("googtrans", "/en/en");
//            mapCookies.put("aeu_cid", "dfbb617233bf455d8a67c3cc14084c84-1548668317023-02612-UneMJZVf");
//            mapCookies.put("_gac_UA-17640202-1", "1.1548668319.Cj0KCQiA7briBRD7ARIsABhX8aC-HFZUh8t53uzuTZ9iWazJCA_PQXH6dLygViXTH7au5pr7G2OhotAaAmBFEALw_wcB");
//            mapCookies.put("_fbp", "fb.1.1548743464093.1468901999");
//            mapCookies.put("AKA_A2", "A");
//            mapCookies.put("_mle_tmp0", "iiCGajxLJhPRfqiVFROq8kpgwBcA7yfr%2Fb68hCHrHgcOZaamUcHMY5r4v8LNrGU42bhJbhSthS2NPmStlz7d271gMYMS60gDm7wDAgww5%2FmkLyaR0lMtEITaqB4XAjJn");
//            mapCookies.put("acs_usuc_t", "acs_rt=0df44f66c4c14d628faa59ef31a0ecf6&x_csrf=1d06lrrhy95lb");
//            mapCookies.put("_m_h5_tk", "6e37520ec2d9adbd15a223faf440812d_1548753263694");
//            mapCookies.put("_m_h5_tk_enc", "2c6b7f80118e817219694aa287f9e2ed");
//            mapCookies.put("_hvn_login", "13");
//            mapCookies.put("xman_us_t", "x_lid=vn316045415ahvae&sign=y&x_user=N7+olAtukRVesVV2WRCoK3U2AWbA8NUBxx+VdQcrQjM=&ctoken=oubl87b1dd1r&need_popup=y&l_source=aliexpress");
//            mapCookies.put("aep_usuc_t", "ber_l=A0");
//            mapCookies.put("xman_f", "jBbPWtOpxwq/vWID3j4KjqoHa3dK61bK9SkEDLP6Yfb5kcypkdLwcRXik7VBbPXIi75xpFwy/kvOF6BEd9f7/s5aM7tAsYIdgfCnfSJLc4vKRi6NSQsm0IlrvbKsUG1MKg1VkbwZnUZg4iMFx4UInIjkQuhnmtFjLG/wDyrTHVWQseLb0/iuvaWUbSFIHVZZtQ9S2yD3COMAyaKKMj8eLSURQzkOhm/SsvbvMCyPOzNgW3FbetcyjCxn1WdW+KzJbgduIm4YYhaVldt7wmlECU3/0b1DPZbD4hrGmppPZSS3+Rg6rYJiyJmomWrgmgAtUw6MZ7TOsRQM5rBcqtPi3Ky9i3NhwL+5q7E+CuPyHtw+A8DIBwk9QhyzK7B/ravSU0MqxBI1VirKh2Q0ioztfsl2Cm/sbbLH0dpOk4xaSFE=");
//            mapCookies.put("xman_us_f", "zero_order=y&x_locale=en_US&x_l=1&last_popup_time=1548669635633&x_user=VN|VN|shopper|ifm|1917967415&no_popup_today=n&x_as_i=%7B%22aeuCID%22%3A%22dfbb617233bf455d8a67c3cc14084c84-1548668317023-02612-UneMJZVf%22%2C%22af%22%3A%22178094261%22%2C%22affiliateKey%22%3A%22UneMJZVf%22%2C%22channel%22%3A%22PREMINUM%22%2C%22cv%22%3A%222%22%2C%22ms%22%3A%220%22%2C%22src%22%3A%22aaf%22%2C%22tagtime%22%3A1548668317103%7D");
//            mapCookies.put("aep_usuc_f", "site=glo&c_tp=USD&x_alimid=1917967415&isb=y&region=VN&b_locale=en_US");
//            mapCookies.put("_mle_tmp_harden0", "COMyBPKnGxIg1PC5i8Kdr%2BDgBVWXsSqKsn8jXeYfVPKzm4s9J9p0YR%2B15zqLvHG4Wa%2FRJwQiOOPsBULWZKeO%2BTLfbSJzML6pHvoIM1mCxmzT400bn92%2F0kb7B7e48Fkm");
//            mapCookies.put("aep_history", "keywords%5E%0Akeywords%09%0A%0Aproduct_selloffer%5E%0Aproduct_selloffer%0932969009966%0932970665663%0932866169186%0932884199032%0932837481304%0932822914196%0932969822304%0932919586591");
//            mapCookies.put("_mle_tmp_enc0", "Ey%2Fp8LswzxA3J47VsqxI%2B%2F34bBn%2BEBBwelwEZWKKhCY%2BFnjbC09EFNG0GhdIRAMOCQvsUzm3P5Y%2BudHSaRzFCXRFppBJrsKWUsn2XTLrMCIVEITtnj%2FmnMuG%2BD43rXKO");
//            mapCookies.put("ali_apache_tracktmp", "W_signed=Y");
//            mapCookies.put("ali_apache_track", "mt=1|ms=|mid=vn316045415ahvae");
//            mapCookies.put("_gat", "1");
//            mapCookies.put("intl_common_forever", "8/LUtPkTP0tEx7JsMjBDPrhRNwaL6rRIMPFfy6c2dGF9+RrUgKgJ4Q==");
//            mapCookies.put("isg", "BMbGq-S85oEef7J7d43g7fK2F7yIj2hZ1I7b0rDvpenEs2bNGbLs8YBBj7faGwL5");
//            mapCookies.put("xman_t", "Md/1jZCnA7CLi4QTrxMtQrQaRWQU9yE22tHAxRVTbjzN/57F3mQwUmrw2sU7VAQxtnI5lUDlEfcrzs6ABAodQSrXODKLlAsKytZhsge5B67CyZP7fYtFKMKyvq7/dOC2kR1cSzsFQ/tSt1ATzsGk9VXkdv3kQL1tsCaO5IbNg9Tc1vLaanjj4GJdTLJ6RdjOMT5m7eXd0/x1Tb5PLDReCrVDkptKOYBk8uuOHqebKZSBHosvWGpxmUoGBfF7n2DjcEenx376Pu9cpvI/ow6z7Q9LLMdfSp0MEBtx2G1EKeeYjEsiX5kZ/weTH4DKD+igG1cRnyMp5JiuMAsBlFbLpcW2UeSxa0NmSUFVnqf55ydFk4dp0qnZQc1+LYRg2RBXa+IZpeCx83xbEDqfPDVckvKRtTd0hA6uKDOVBi0Y+uXQv/P3UOOPqknhpPbUWBYcE9iB3a6tdQvRdaZZJc9u+oXIjTkpGZ8M1oxTpB0bHQ21v2GOv4GfbTex44VTg7MGqfeiMJc4mjEKVG3/8AGQaZ7q5fOdLUQVoBTrdO3uFszd+ljE8czg6cJ+0H6kv4ad1FDdHKJJYgrYQV+C+nLgKKBf+1Kop3LQGAoZES2Hvay/qCD/A+X9CsodiJcVjRa8guxzf+19pdc=");
//            mapCookies.put("RT", "\"sl=9&ss=1548750663855&tt=7085&obo=6&sh=1548751533979%3D9%3A6%3A7085%2C1548751521347%3D8%3A5%3A7085%2C1548751423306%3D7%3A4%3A7085%2C1548751258313%3D6%3A3%3A7085%2C1548751225688%3D5%3A3%3A4682&dm=aliexpress.com&si=48b1d38e-0e08-4521-88cf-0bea9b094185&se=900&rl=1&ld=1548751533980&r=https%3A%2F%2Fwww.aliexpress.com%2Fstore%2F4390004&ul=1548751533981\"");
//            mapCookies.put("JSESSIONID", "9494BECA2440033078B584087C9C54B2");
    }

//    public static void writeToFile(PlaylistObject playlistObject) {
//        if (playlistObject == null || !playlistObject.checkData()) {
//            return;
//        }
//        BufferedWriter bw = null;
//        FileWriter fw = null;
//
//        try {
//
//            PrintWriter pw = new PrintWriter("cache.txt");
//            pw.close();
//
//            fw = new FileWriter("cache.txt");
//            bw = new BufferedWriter(fw);
//            bw.write(playlistObject.getHotTrend());
//            bw.write("\n");
//            bw.write(playlistObject.getPlaylistTitle());
//            bw.write("\n");
//            for (int i = 0, size = playlistObject.getListVideoId().size(); i < size; i++) {
//                bw.write(playlistObject.getListVideoId().get(i));
//                if (i < size - 1) {
//                    bw.write("\n");
//                }
//            }
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        } finally {
//
//            try {
//
//                if (bw != null) {
//                    bw.close();
//                }
//
//                if (fw != null) {
//                    fw.close();
//                }
//
//            } catch (IOException ex) {
//
//                ex.printStackTrace();
//
//            }
//
//        }
//    }
//    public static void writeInfoToFile() {
//
//        BufferedWriter bw = null;
//        FileWriter fw = null;
//
//        try {
//
//            PrintWriter pw = new PrintWriter("info.txt");
//            pw.close();
//
//            fw = new FileWriter("info.txt");
//            bw = new BufferedWriter(fw);
//            bw.write("appName=" + GlobalInfo.APP_NAME);
//            bw.write("\n");
//            bw.write("apiKey=" + GlobalInfo.apiKey);
//            bw.write("\n");
//            bw.write("clientId=" + GlobalInfo.clientId);
//            bw.write("\n");
//            bw.write("clientSecret=" + GlobalInfo.clientSecret);
//            bw.write("\n");
//            bw.write("numberOfPll=" +  GlobalInfo.numberOfPll);
//            bw.write("\n");
//            bw.write("regionName=" + GlobalInfo.regionName);
//            bw.write("\n");
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        } finally {
//
//            try {
//
//                if (bw != null) {
//                    bw.close();
//                }
//
//                if (fw != null) {
//                    fw.close();
//                }
//
//            } catch (IOException ex) {
//
//                ex.printStackTrace();
//
//            }
//
//        }
//    }
//
//    public static void readInfo() {
//        Properties properties = new Properties();
//        InputStream in = null;
//        try {
//            in = new FileInputStream("info.txt");
//            properties.load(in);
//            GlobalInfo.APP_NAME = properties.getProperty("appName","");
//            GlobalInfo.apiKey = properties.getProperty("apiKey","");
//            GlobalInfo.clientId = properties.getProperty("clientId","");
//            GlobalInfo.clientSecret = properties.getProperty("clientSecret","");
//            GlobalInfo.numberOfPll = Integer.parseInt(properties.getProperty("numberOfPll","10000"));
//            GlobalInfo.regionName = properties.getProperty("regionName",GlobalInfo.INDIA_NAME);
//
//        } catch (IOException e) {
//            System.err.println("There was an error reading " + e.getCause()
//                    + " : " + e.getMessage());
//        } finally {
//            if(in != null) {
//                try {
//                    in.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(CrawlerHottrend.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        
//        
//
//    }
    public static boolean checkExist(String s, File fin) throws IOException {

        FileInputStream fis = new FileInputStream(fin);
        // //Construct the BufferedReader object
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        String aLine = null;
        while ((aLine = in.readLine()) != null) {
            // //Process each line
            if (aLine.trim().contains(s)) {
                //System.out.println("contains " + s);
                in.close();
                fis.close();
                return true;
            }
        }

        // do not forget to close the buffer reader
        in.close();
        fis.close();

        return false;
    }
}
