/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.crawl.aliex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.models.aliex.AliexProductFull;
import com.models.aliex.PriceFull;
import com.models.aliex.crawl.AliexScriptDetail;
import com.models.aliex.crawl.AliexScriptDetailData;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 *
 * @author PhanDuy
 */
public class AliexCrawlProductInfoSvs {

    private static AliexCrawlProductInfoSvs aliexCrawlProductInfoSvs;

    public static AliexCrawlProductInfoSvs getInstance() {
        if (aliexCrawlProductInfoSvs == null) {
            aliexCrawlProductInfoSvs = new AliexCrawlProductInfoSvs();
        }
        return aliexCrawlProductInfoSvs;
    }

    public String getXPath(Node node) {
        return getXPath(node, "");
    }

    public String getXPath(Node node, String xpath) {
        if (node == null) {
            return "";
        }
        String elementName = "";
        if (node instanceof Element) {
            elementName = ((Element) node).nodeName();
        }
        Node parent = node.parentNode();
        if (parent == null) {
            return xpath;
        }
        return getXPath(parent, "/" + elementName + xpath);
    }

//    public CrawlProductDetail crawlProductInfo(CrawlPageProductItem crawlPageProductItem, AliexStoreInfo aliexStoreInfo, boolean isGetBrand, boolean isGetDescription, boolean isGetRelate) {
////        StringBuffer sb = new StringBuffer();
//        long start1 = System.currentTimeMillis();
//        long start = System.currentTimeMillis();
//        AliexCrawlSvs.getInstance().goToPage(crawlPageProductItem.getUrl());
//
//        System.out.println("Time load page " + (System.currentTimeMillis() - start));
//        String pageSource = AliexCrawlSvs.getInstance().getPageSource();
//        Document doc = Jsoup.parse(pageSource);
//
////        sb.append("Time get page source 1 " + (System.currentTimeMillis() - start));
////        sb.append("\n");
//        System.out.println("Time get page source 1 " + (System.currentTimeMillis() - start));
//        CrawlProductDetail crawlProductInfo = new CrawlProductDetail();
//
////        AliexProductFull aliexProductFull = getAliexProductFull(doc);
//        // Get variation image url
//        Elements skus = doc.select("li[class='item-sku-image'] > a");
//
////        ArrayList<PropertyFull> properties = null;
//        HashMap<String, String> hashmapProperties = null;
//
//        if (skus != null && !skus.isEmpty()) {
//            for (Element elementSku : skus) {
//                String skuId = elementSku.attr("data-sku-id");
//                Elements elements = elementSku.select("img");
//                if (elements == null || elements.isEmpty()) {
//                    continue;
//                }
//
//                String url = elements.first().attr("src");
//
//                if (hashmapProperties == null) {
//                    hashmapProperties = new HashMap<>();
//                }
//
//                hashmapProperties.put(skuId, url);
//            }
//        } else {
//            skus = doc.select("div[class='sku-property-image'] > img");
//            if (skus != null && !skus.isEmpty()) {
//                for (Element elementSku : skus) {
//                    String url = elementSku.attr("src");
//                    String title = elementSku.attr("title");
//
//                    if (hashmapProperties == null) {
//                        hashmapProperties = new HashMap<>();
//                    }
//
//                    hashmapProperties.put(title.trim().toLowerCase(), url);
//                }
//            }
//        }
//
//        crawlProductInfo.setHashProperties(hashmapProperties);
//
//        // Scroll to get related search or description
//        if (isGetDescription || isGetRelate) {
//            start = System.currentTimeMillis();
//            AliexCrawlSvs.getInstance().scrollToBottom(10000);
//
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(AliexCrawlProductInfoSvs.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            pageSource = AliexCrawlSvs.getInstance().getPageSource();
//            doc = Jsoup.parse(pageSource);
//
////            sb.append("Time get page source 2 " + (System.currentTimeMillis() - start));
////            sb.append("\n");
//            System.out.println("Time get page source 2 " + (System.currentTimeMillis() - start));
//            if (isGetDescription) {
//                System.out.println("Get Description");
//                Elements elementDescription = doc.select("div[class='detailmodule_html']");
//
//                if (elementDescription == null || elementDescription.isEmpty()) {
//                    elementDescription = doc.select("div[id='product-description']");
//                }
//
//                if (elementDescription != null && !elementDescription.isEmpty() && !StringUtils.isEmpty(elementDescription.html())) {
//                    crawlProductInfo.setHtml_description(elementDescription.html());
//                    System.out.println(crawlPageProductItem.getId() + ": crawl des success");
//                } else {
//                    System.out.println(crawlPageProductItem.getId() + ": crawl des fail");
//                }
////               else {
////                   elementDescription = doc.select("div[id='product-description']");
////               }
//            }
//
//            if (isGetRelate) {
//                System.out.println("Get Related");
//                ArrayList<String> listRelatedSearchs = null;
//
//                Elements elements = doc.select("a[class='width-fix-link']");
//                if (elements != null && !elements.isEmpty()) {
//
//                    for (Element element : elements) {
//                        String text = element.text().toLowerCase().trim();
//
//                        if (text.contains("wholesale")) {
//                            text = text.replaceAll("wholesale", "");
//                        }
//
//                        if (text.contains("price")) {
//                            text = text.replaceAll("price", "");
//                        }
//
//                        if (text.contains("promotion")) {
//                            text = text.replaceAll("promotion", "");
//                        }
//
//                        if (!text.isEmpty()) {
////                        fullKeys += " " + p;
//                            if (listRelatedSearchs == null) {
//                                listRelatedSearchs = new ArrayList<>();
//                            }
//                            listRelatedSearchs.add(StringUtils.getPrefixCapitalWord(text));
//                        }
//                        if (listRelatedSearchs != null && listRelatedSearchs.size() >= 100) {
//                            break;
//                        }
//                    }
//
//                }
//
//                if (listRelatedSearchs != null) {
//                    crawlProductInfo.setListRelatedSearch(listRelatedSearchs);
//                }
//
//                Utils.saveRelatedSearch(crawlPageProductItem.getId(), listRelatedSearchs);
//            }
//
//        }
//
//        if (isGetBrand) {
//            System.out.println("Get Brand");
//            // Get brandname
////            List<WebElement> listWebElements = AliexCrawlSvs.getInstance().driver.findElements(By.className("detail-tab-bar"));
//            start = System.currentTimeMillis();
//            List<WebElement> listWebElements = (new WebDriverWait(AliexCrawlSvs.getInstance().driver, 10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("detail-tab-bar")));
//
//            System.out.println("Get Brand 1");
//            if (listWebElements != null && listWebElements.size() > 1) {
//                List<WebElement> listWebElements1 = listWebElements.get(1).findElements(By.className("tab-item"));
//
////                System.out.println("Get Brand 2 " + listWebElements1.size());
////                List<WebElement> listWebElements1 = (new WebDriverWait(AliexCrawlSvs.getInstance().driver, 10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("tab-item")));
//                if (listWebElements1 != null) {
//                    for (WebElement webElement : listWebElements1) {
//                        if ("specs".equals(webElement.getAttribute("ae_object_type"))) {
//                            AliexCrawlSvs.getInstance().actions.moveToElement(webElement).click().build().perform();
//                            AliexCrawlSvs.getInstance().actions.moveToElement(webElement).click().build().perform();
//
//                            pageSource = AliexCrawlSvs.getInstance().getPageSource();
//                            doc = Jsoup.parse(pageSource);
//
//                            Elements specificsElements = doc.select("ul[class='product-specs-list util-clearfix'] > li[class='product-prop line-limit-length']");
//                            if (!specificsElements.isEmpty()) {
//                                HashMap<String, String> hashmapSpecifics = null;
//                                for (Element element : specificsElements) {
//                                    Elements elements = element.select("span[class='property-title']");
//
//                                    String text = elements.text().trim().toLowerCase();
//
//                                    Elements elementsValue = element.select("span[class='property-desc line-limit-length']");
//                                    String value = elementsValue.text();
//
//                                    System.out.println("Text: " + text + " | value: " + value);
//
//                                    if (text.contains("brand name")) {
//                                        crawlProductInfo.setBrandName(value);
//                                        continue;
//                                    }
//
//                                    if (hashmapSpecifics == null) {
//                                        hashmapSpecifics = new HashMap<>();
//                                    }
//
//                                    if (!hashmapSpecifics.containsKey(text)) {
//                                        hashmapSpecifics.put(text, value);
//                                    }
//                                }
//                            }
//                            break;
//                        }
//                    }
//
//                }
//            }
//
//        }
//
////        sb.append("Total crawl product time " + (System.currentTimeMillis() - start1));
////        sb.append("\n");
//        System.out.println("Total crawl product time " + (System.currentTimeMillis() - start1));
////        System.out.println("" + sb.toString());
////        if (crawlProductInfo.getBrandName() != null) {
////            Gson gson = new Gson();
////            String dataClean = gson.toJson(crawlProductInfo);
////            String encrytData = EncryptUtil.encrypt(dataClean);
////
////            File file = new File(Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR);
////            if (!file.exists()) {
////                file.mkdir();
////            }
////
////            file = new File(file.getAbsolutePath() + Configs.pathChar + aliexStoreInfo.getStoreSign());
////            if (!file.exists()) {
////                file.mkdir();
////            }
////
////            file = new File(Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR + Configs.pathChar + aliexStoreInfo.getStoreSign() + Configs.pathChar + crawlPageProductItem.getId() + ".txt");
////
////            try {
////                FileUtils.writeStringToFile(file, encrytData);
////            } catch (IOException ex) {
////                Logger.getLogger(AliexCrawlSvs.class.getName()).log(Level.SEVERE, null, ex);
////            }
////        }
////        crawlProductInfo.setAliexProductFull(aliexProductFull);
//        return crawlProductInfo;
//    }

//    public CrawlProductDetail parseProductDocument(Document document) {
//        
//    }
//    public boolean isNeedToScroll(AliexStoreInfo aliexStoreInfo, boolean isGetDescription) {
//        return isGetDescription || isNeedToGetRelate(aliexStoreInfo);
//    }
//    public boolean isNeedToGetRelate(AliexStoreInfo aliexStoreInfo) {
//        if (AuthenConfig.userLvel <= 1) {
//            return false;
//        }
//
//        return aliexStoreInfo.getListKeyWords() == null || aliexStoreInfo.getListKeyWords().isEmpty();
//    }
    public AliexProductFull getAliexProductFull(Document doc) {
        //
        Elements elementMainInfo = doc.select("div[class='product-main-wrap']");

        if (elementMainInfo == null || elementMainInfo.isEmpty()) {
            return null;
        }

        AliexProductFull aliexProductFull = new AliexProductFull();

        // Get images
        Elements mainImagesWrap = elementMainInfo.select("div[class='images-view-wrap']");
        Elements imageElements = mainImagesWrap.select("div[class='images-view-item'] > img");

        if (imageElements == null || imageElements.isEmpty()) {
            return null;
        }

        ArrayList<String> imageUrls = new ArrayList<>();
//        String[] imageUrls = new String[imageElements.size()];

        for (int i = 0, length = imageElements.size(); i < length; i++) {
            imageUrls.add(imageElements.get(i).attr("src"));
        }

        aliexProductFull.setProductImages(imageUrls);

        // Get title
        Elements titleElement = elementMainInfo.select("div[class='product-title']");
        if (titleElement == null || titleElement.isEmpty()) {
            return null;
        }
        aliexProductFull.setTitle(titleElement.text());

        // Get price
        Elements priceInfo = elementMainInfo.select("div[class='product-price']");
        ArrayList<PriceFull> prices = new ArrayList<>();

        return aliexProductFull;
    }
    
    static int count = 0;

    public AliexScriptDetailData crawlProductData(String url, String storeSign) {
        
        AliexScriptDetailData data = new AliexScriptDetailData();
        
        Document doc = AliexCrawlSvs.getInstance().processPage(url);
//        Document doc = PhantomJsManager.getInstance().renderPage(url);

        if (doc == null) {
            System.out.println("Can not crawl " + url);
            return null;
        }
//        System.out.println("" + url);
//        if (url.contains("https://www.aliexpress.com/item/1005003717996042.html") ||
//                url.contains("https://www.aliexpress.com/item/1005003800330570.html")) {
//            System.out.println("============");
//            System.out.println("1: " + doc.html());
//            Document document = AliexCrawlSvs.getInstance().processPage(url);
//            System.out.println("============");
//            System.out.println("2:" + document.html());
//            System.out.println("============");
//        }
        
        data.updateStatus(doc);
        
        if(!data.isSuccess()) {
            return data;
        }

        Elements skus = doc.select("script");

        if (skus == null || skus.isEmpty()) {
            System.out.println("Can not get script " + url);
            return null;
        }

        for (Element elementSku : skus) {
            String html = elementSku.html();

            if (html.startsWith("window.runParams")) {
                String s = html.substring("window.runParams = ".length(), html.indexOf("};") + 1);
                
//                System.out.println("===============================");
//                System.out.println("" + s);
//                System.out.println("===============================");

                Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
                AliexScriptDetail scriptDetail = gson.fromJson(s, AliexScriptDetail.class);
                data.copy(scriptDetail.getData());
//                try {
//                    Utils.writeToFile(data.productInfoComponent.subject, Configs.CACHE_PATH + Configs.RELATED_CACHE_DIR + Configs.pathChar + storeSign + Configs.pathChar + data.productInfoComponent.idStr + ".txt");
//                    System.out.println("Log success 1");
//                } catch (IOException ex) {
//                    System.out.println("Log fail 1: " + ex.getMessage());
//                }
                data.buildShippingPrice();
                break;
            }
        }
//        if (data.shippingPrice < 0) {
//            Elements shippingPrice = doc.select("div[class='product-shipping-price'");
//            if (shippingPrice == null || shippingPrice.isEmpty()) {
//                shippingPrice = doc.select("div[class='product-dynamic-shipping'");
//            }
//            if (shippingPrice != null && !shippingPrice.isEmpty()) {
//                String text = shippingPrice.text().trim().toLowerCase();
//                if (text.contains("free shipping")) {
//                    data.shippingPrice = 0;
//                } else {
//                    text = text.replaceAll("[^0-9.]", " ").trim();
//                    try {
//                        data.shippingPrice = Float.parseFloat(text);
//                    } catch (NumberFormatException ex) {
//                        return null;
//                    }
//                }
//            }
//        }
        return data;
    }
    
    public AliexScriptDetailData crawlProductData(String url, String cookieFile, String storeSign) {
        
        AliexScriptDetailData data = new AliexScriptDetailData();
        System.out.println("Crawl with " + cookieFile);
        Document doc = AliexCrawlSvs.getInstance().processPage(url, cookieFile);
//        Document doc = PhantomJsManager.getInstance().renderPage(url);

        if (doc == null) {
            System.out.println("Can not crawl " + url);
            return null;
        }
//        try {
            //        System.out.println("" + url);
//        if (url.contains("https://www.aliexpress.com/item/1005003717996042.html") ||
//                url.contains("https://www.aliexpress.com/item/1005003800330570.html")) {
//            System.out.println("============");
//            Utils.writeToFile(doc.html(), Configs.CACHE_PATH + Configs.RELATED_CACHE_DIR + Configs.pathChar + "Test" + ".txt");
//            System.out.println("Log success");
//        } catch (IOException ex) {
//            System.out.println("Log fail: " + ex.getMessage());
//        }
//            System.out.println("1: " + doc.html());
//            Document document = AliexCrawlSvs.getInstance().processPage(url);
//            System.out.println("============");
//            System.out.println("2:" + document.html());
//            System.out.println("============");
//        }
        
        data.updateStatus(doc);
        
        if(!data.isSuccess()) {
            return data;
        }

        Elements skus = doc.select("script");

        if (skus == null || skus.isEmpty()) {
            System.out.println("Can not get script " + url);
            return null;
        }

        for (Element elementSku : skus) {
            String html = elementSku.html();

            if (html.startsWith("window.runParams")) {
                String s = html.substring("window.runParams = ".length(), html.indexOf("};") + 1);
                
//                System.out.println("===============================");
//                System.out.println("" + s);
//                System.out.println("===============================");
//            try {
//                Utils.writeToFile(s, Configs.CACHE_PATH + Configs.RELATED_CACHE_DIR + Configs.pathChar + "TestData" + ".txt");
//                System.out.println("Log success 1");
//            } catch (IOException ex) {
//                System.out.println("Log fail 1: " + ex.getMessage());
//            }

                Gson gson = new Gson();
                AliexScriptDetail scriptDetail = gson.fromJson(s, AliexScriptDetail.class);
                data = scriptDetail.getData();
//                data.copy(scriptDetail.getData());
                
//                try {
//                    Utils.writeToFile(data.productInfoComponent.subject, Configs.CACHE_PATH + Configs.RELATED_CACHE_DIR + Configs.pathChar + data.productInfoComponent.idStr + ".txt");
//                    System.out.println("Log success " + data.productInfoComponent.idStr);
//                } catch (IOException ex) {
//                    System.out.println("Log fail 1: " + data.productInfoComponent.idStr + " : " + ex.getMessage());
//                }
                
//                data.buildShippingPrice();
                break;
            }
        }
//        if (data.shippingPrice < 0) {
//            Elements shippingPrice = doc.select("div[class='product-shipping-price'");
//            if (shippingPrice == null || shippingPrice.isEmpty()) {
//                shippingPrice = doc.select("div[class='product-dynamic-shipping'");
//            }
//            if (shippingPrice != null && !shippingPrice.isEmpty()) {
//                String text = shippingPrice.text().trim().toLowerCase();
//                if (text.contains("free shipping")) {
//                    data.shippingPrice = 0;
//                } else {
//                    text = text.replaceAll("[^0-9.]", " ").trim();
//                    try {
//                        data.shippingPrice = Float.parseFloat(text);
//                    } catch (NumberFormatException ex) {
//                        return null;
//                    }
//                }
//            }
//        }
        return data;
    }
}
