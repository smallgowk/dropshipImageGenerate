/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.transform;

import com.config.Configs;
import com.google.gson.Gson;
import com.utils.MarketUtil;
import com.models.aliex.AliexProductFull;
import com.models.aliex.PriceFull;
import com.models.aliex.ProductAttribute;
import com.models.amazon.ProductAmz;
import com.models.amazon.ProductTypes;
import com.models.aliex.crawl.ItemSpecifics;
import com.models.aliex.store.AliexStoreInfo;
import com.models.amazon.AmzContentFormat;
import com.models.amazon.DataStore;
import com.models.request.ImagePathModel;
import com.models.response.BulletPointSearchTerm;
import com.models.response.TransformCrawlResponse;
import com.utils.AWSUtil;
import com.utils.CheckingDataUtil;
import com.utils.FuncUtil;
import com.utils.StringUtils;
import com.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
 *
 * @author PhanDuy
 */
public class ProcessTransformAliexToAmz {

    public static HashMap<String, String> setBannedProduct = new HashMap<>();

    public static ArrayList<ProductAmz> transform(AliexProductFull aliexProductFull, AliexStoreInfo aliexStoreInfo, float origiPrice) {
        ArrayList<ProductAmz> results = null;
        results = new ArrayList<>();
        ProductAmz productAmz = createBasicProductAmz(aliexProductFull, aliexStoreInfo, origiPrice);
        results.add(productAmz);
        if (aliexProductFull.isHasVarition()) {
            ArrayList<ProductAmz> listChilds = createChilds(aliexProductFull.getPrices(), aliexStoreInfo, productAmz, aliexProductFull.getPromotionRate(), aliexProductFull.getShippingPrice());
            if (listChilds != null && !listChilds.isEmpty()) {
                listChilds.forEach((child) -> {
                    child.copyProduct(productAmz);
                    child.setQuantity("100");
                });
                results.addAll(listChilds);
            } else {
                productAmz.setStandard_price("" + Utils.getCEOPrice(origiPrice));
            }
        } else {
            productAmz.setStandard_price("" + Utils.getCEOPrice(origiPrice));
        }
        return results;
    }
    
    public static ArrayList<ProductAmz> transform(ProductAmz productAmz, ArrayList<PriceFull> prices, float origiPrice, float promotionRate, float shippingPrice, AliexStoreInfo aliexStoreInfo) {
        ArrayList<ProductAmz> results = null;
        results = new ArrayList<>();
        results.add(productAmz);
        if (isHasVarition(prices)) {
            ArrayList<ProductAmz> listChilds = createChilds(prices, aliexStoreInfo, productAmz, promotionRate, shippingPrice);
            if (listChilds != null && !listChilds.isEmpty()) {
                listChilds.forEach((child) -> {
                    child.copyProduct(productAmz);
                    child.setQuantity("100");
                });
                results.addAll(listChilds);
            } else {
                productAmz.setStandard_price("" + Utils.getCEOPrice(origiPrice));
            }
        } else {
            productAmz.setStandard_price("" + Utils.getCEOPrice(origiPrice));
        }
        return results;
    }
    
    public static ArrayList<ProductAmz> transform(TransformCrawlResponse res, AliexStoreInfo aliexStoreInfo, ArrayList<ImagePathModel> imagePathModels) {
        ArrayList<ProductAmz> results = null;
        results = new ArrayList<>();
        ProductAmz parent = new ProductAmz();
        parent.setItem_sku(res.baseAmzProduct.item_sku);
        parent.copyAllProduct(res.baseAmzProduct);
        updateDescription(parent, res, aliexStoreInfo);
        updateBulletPoints(parent, res, aliexStoreInfo);
        parent.updateImageUrl(imagePathModels);
        results.add(parent);
        if (res.childs != null && !res.childs.isEmpty()) {
            res.childs.forEach((child) -> {
                child.copyProduct(parent);
                if (StringUtils.isEmpty(child.main_image_url)) {
                    child.main_image_url = parent.main_image_url;
                }
                child.updateImageUrlForChild(imagePathModels);
            });
            results.addAll(res.childs);
        } else {
            if (StringUtils.isEmpty(res.baseAmzProduct.standard_price)) {
                parent.setStandard_price("" + Utils.getCEOPrice(res.origiPrice));
            } else {
                parent.setStandard_price(res.baseAmzProduct.standard_price);
            }
        }
        return results;
    }
    
    public static void updateDescription(ProductAmz productAmz, TransformCrawlResponse res, AliexStoreInfo storePageInfo) {
        String descriptionForm = storePageInfo.getDescription();
        String tips = storePageInfo.getTip();
        String reasons = storePageInfo.getReasons();
        
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.TITLE_KEY, res.baseAmzProduct.item_name, false);
        descriptionForm = replaceAllPattern(descriptionForm, AmzContentFormat.MAIN_KEYWORD_KEY, storePageInfo.getMain_key(), true);
        descriptionForm = replaceAllPattern(descriptionForm, AmzContentFormat.BRANDNAME_KEY, storePageInfo.getBrandName(), false);
        
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.SPECIFIC_KEY, !StringUtils.isEmpty(res.htmlSpecifics) ? res.htmlSpecifics : "", false);
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.TIPS_KEY, !StringUtils.isEmpty(tips) && res.showTip ? tips : "", false);
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.REASON_KEY, !StringUtils.isEmpty(tips) && res.showReason ? tips : "", false);
        
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.DESCRIPTION_KEY, !StringUtils.isEmpty(res.htmlDescription) ? res.htmlDescription : "", false);
        productAmz.setProduct_description(descriptionForm);
    }
    
    public static void updateBulletPoints(ProductAmz productAmz, TransformCrawlResponse res, AliexStoreInfo storePageInfo) {
        BulletPointSearchTerm bulletPointSearchTerm = res.bulletPointSearchTerm != null ? res.bulletPointSearchTerm : new BulletPointSearchTerm("", "", "", "", "");
        if (storePageInfo.listBulletPoints != null && !storePageInfo.listBulletPoints.isEmpty()) {
            if (storePageInfo.listBulletPoints.size() > 0) {
                productAmz.setBullet_point1(
                        storePageInfo.listBulletPoints.get(0).replace(
                                AmzContentFormat.SEARCH_TERM_1,
                                bulletPointSearchTerm.searchTerm1
                        )
                );
                if (storePageInfo.listBulletPoints.size() > 1) {
                    productAmz.setBullet_point2(
                            storePageInfo.listBulletPoints.get(1).replace(
                                    AmzContentFormat.SEARCH_TERM_2,
                                    bulletPointSearchTerm.searchTerm2
                            )
                    );
                    if (storePageInfo.listBulletPoints.size() > 2) {
                        productAmz.setBullet_point3(
                                storePageInfo.listBulletPoints.get(2).replace(
                                        AmzContentFormat.SEARCH_TERM_3,
                                        bulletPointSearchTerm.searchTerm3
                                )
                        );
                        if (storePageInfo.listBulletPoints.size() > 3) {
                            productAmz.setBullet_point4(
                                    storePageInfo.listBulletPoints.get(3).replace(
                                            AmzContentFormat.SEARCH_TERM_4,
                                            bulletPointSearchTerm.searchTerm4
                                    )
                            );
                            if (storePageInfo.listBulletPoints.size() > 4) {
                                productAmz.setBullet_point5(
                                        storePageInfo.listBulletPoints.get(4).replace(
                                                AmzContentFormat.SEARCH_TERM_5,
                                                bulletPointSearchTerm.searchTerm5
                                        )
                                );
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static String replacePattern(String form, String key, String content, boolean isNeedUpperPrefix) {
        if (content == null || content.isEmpty()) {
            return form.replace(key, "");
        }

        return form.replace(key, isNeedUpperPrefix ? StringUtils.getPrefixCapitalWord(content) : content);
    }
    
    public static String replaceAllPattern(String form, String key, String content, boolean isNeedUpperPrefix) {
        if (content == null || content.isEmpty()) {
            return form.replaceAll(Pattern.quote(key), "");
        }

        return form.replaceAll(Pattern.quote(key), isNeedUpperPrefix ? StringUtils.getPrefixCapitalWord(content) : content);
    }
    
    public static boolean isHasVarition(ArrayList<PriceFull> prices) {
        if (prices == null || prices.isEmpty()) {
            return false;
        }

        if (prices.size() == 1) {
            PriceFull priceFull = prices.get(0);
            return priceFull.isHasVariation();
        }

        for (PriceFull priceFull : prices) {
            if (priceFull.isHasVariation()) {
                return true;
            }
        }

        return false;
    }
    
    public static ArrayList<ProductAmz> createChilds(ArrayList<PriceFull> prices, AliexStoreInfo aliexStoreInfo, ProductAmz productAmz, float promotionRate, float shippingPrice) {
        if (productAmz == null) return null;
        ArrayList<ProductAmz> results = null;
        HashSet<Integer> set = new HashSet<>();
        for (PriceFull priceFull : prices) {
            if (aliexStoreInfo.isOnlyUS && !priceFull.isShipFromUS()) {
                continue;
            }
            
            if (priceFull.properties == null || priceFull.properties.isEmpty()) {
                continue;
            }

            ProductAmz child = productAmz.createChild(results == null ? 1 : results.size() + 1, priceFull, promotionRate, shippingPrice, aliexStoreInfo);

            if (child == null) {
                continue;
            }
            if (results == null) {
                results = new ArrayList<>();
            }

            results.add(child);
        }

        if (results == null || results.isEmpty()) {
            return null;
        }

        boolean isHasColor = false;
        boolean isHasSize = false;

        for (int size = results.size(), i = size - 1; i >= 0; i--) {
            ProductAmz productAmz1 = results.get(i);
            if (isHasColor && isHasSize) {
                continue;
            }
            if (productAmz1.getVariation_theme().equals(aliexStoreInfo.variationThemeBoth)) {
                isHasColor = true;
                isHasSize = true;
            } else if (productAmz1.getVariation_theme().equals(aliexStoreInfo.variationThemeColor)) {
                isHasColor = true;
            } else {
                isHasSize = true;
            }
        }

        if (isHasColor && isHasSize) {
            productAmz.setType(ProductTypes.TYPE_PARENT_BOTH, aliexStoreInfo);
        } else if (isHasColor) {
            productAmz.setType(ProductTypes.TYPE_PARENT_COLOR, aliexStoreInfo);
        } else if (isHasSize) {
            productAmz.setType(ProductTypes.TYPE_PARENT_SIZE, aliexStoreInfo);
        } else {
            productAmz.setType(ProductTypes.TYPE_NORMAL, aliexStoreInfo);
        }

        return results;
    }
    
    public static ArrayList<String> genAllImageUrlFromDes(String descriptionHtmlOrigin) {
        Document doc = Jsoup.parse(descriptionHtmlOrigin);
        Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        ArrayList<String> results = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (Element image : images) {
                results.add(image.attr("src"));
            }
        }
        return results;
    }

    public static ArrayList<Element> genDescriptionHtml(String descriptionHtmlOrigin, String productId) {
        Pattern pt = Pattern.compile("[^!-~ ]");
        Matcher match = pt.matcher(descriptionHtmlOrigin);
        while (match.find()) {
            String s = match.group();
            descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\" + s, "");
        }
//        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\<a href=.*?>", "");
//        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\<img.*?>", "");
        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\<span.*?>", "");
        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\</span>", "");
        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("&nbsp;", "");

//        System.out.println("Des after: " + descriptionHtmlOrigin);
        ArrayList<Element> results = new ArrayList<>();
        Whitelist myCustomWhitelist = new Whitelist();
//            myCustomWhitelist.addTags("b", "br", "p", "ul", "div", "li", "strong", "border", "tbody", "span", "stype", "h2", "h1");
//        myCustomWhitelist.addTags("b", "br", "p", "ul", "div", "li", "strong", "border", "tbody", "span", "stype");
        myCustomWhitelist.addTags("b", "br", "p", "ul", "div", "li", "strong", "border", "span", "stype", "kse:widget");
        String clean = Jsoup.clean(descriptionHtmlOrigin, myCustomWhitelist);

//        clean = clean.replaceAll("\\<\\/div\\>", "\\<\\/p\\>");
//        clean = clean.replaceAll("\\<div\\>", "\\<p\\>");
//        clean = clean.replaceAll("\\<\\/b\\>", "\\<\\/strong\\>");
//        clean = clean.replaceAll("\\<b\\>", "\\<strong\\>");
//        clean = clean.replaceAll(Pattern.quote("●"), "-");
//        clean = clean.replaceAll(Pattern.quote(" ● "), "-");
//        clean = clean.replaceAll("\\<\\/span\\>", "");
//        clean = clean.replaceAll("\\<span\\>", "");
        clean = clean.replaceAll("\\<\\/h2", "\\<\\/h3");
        clean = clean.replaceAll("\\<h2\\>", "\\<h3\\>");

        clean = clean.replaceAll("\\<\\/h1", "\\<\\/h3");
        clean = clean.replaceAll("\\<h1\\>", "\\<h3\\>");
        clean = clean.replaceAll("<p><strong>", "<p>");
        clean = clean.replaceAll("</strong></p>", "</p>");

        clean = removeTagAndContent(clean, "kse:widget");

//        clean = clean.replaceAll("\\<kse:widget*?>", "");
//        clean = clean.replaceAll("\\</kse:widget>", "");
//        System.out.println("Clean: " + clean);
//                results.add(clean);
//                productAmz.setListDesParams(results);
        Document doc = Jsoup.parse(clean);
        
        List<Node> childs = doc.body().childNodes();

        Element preElement = null;

        for (int i = 0, size = childs.size(); i < size; i++) {
            Node childNodeLv1 = childs.get(i);
            parseElement(results, childNodeLv1, productId);
        }
        return results;
    }

    public static void parseElement(ArrayList<Element> results, Node node, String productId) {

        if (setBannedProduct.containsKey(productId)) {
            return;
        }

        List<Node> listNode = node.childNodes();
        if (!isHasElementNode(listNode)) {
            if (node instanceof Element) {
                Element element = (Element) node;
                if (!isElementNode(element)) {
                    results.add(element);
//                    System.out.println("" + element.outerHtml());
                } else if (element.tagName().equals("kse:widget")) {
                    return;
                } else {
                    if (listNode != null && !listNode.isEmpty()) {
                        for (Node no : listNode) {
                            if (no instanceof TextNode) {
                                String txt = ((TextNode) no).text();
                                if (StringUtils.isTextVisible(txt)) {
                                    String bannedKey = CheckingDataUtil.containBannedKeyword(txt);
                                    if (bannedKey != null) {
                                        setBannedProduct.put(productId, bannedKey);
                                        return;
                                    }

                                    txt = CheckingDataUtil.processTrademarkAndBrandname(txt);

                                    Element newElement = new Element(Tag.valueOf("p"), "");
                                    newElement.append(txt);

                                    results.add(newElement);
                                }
                            }
                        }
                    }

//                    String txt = element.text();
//
//                    if (StringUtils.isTextVisible(txt)) {
//                        String elementHtml = element.html();
//
//                        if (productId.equals("4000168195664")) {
//                            elementHtml = element.html();
//                            System.out.println("" + elementHtml);
//                        }
//
//                        String bannedKey = AWSUtil.containBannedKeyword(elementHtml);
//                        if (bannedKey != null) {
//                            setBannedProduct.put(productId, bannedKey);
//                            return;
//                        }
//
//                        Tag tag = element.tag();
//
//                        elementHtml = AWSUtil.processTrademarkAndBrandname(elementHtml);
////                        element.text(txt);
//
//                        Element newElement = new Element(Tag.valueOf("p"), "");
//                        newElement.append(elementHtml);
//
//                        results.add(newElement);
//
//                    }
                }
            } else {
                String nodeText = node.toString().trim();
                if (StringUtils.isTextVisible(nodeText)) {

                    return;
                }
                Element element = new Element(Tag.valueOf("p"), "");
                element.appendText(nodeText);
                results.add(element);
//                System.out.println("" + element.outerHtml());
            }

        } else {
            for (Node no : listNode) {
                parseElement(results, no, productId);
            }
        }

    }

    public static boolean isHasElementNode(List<Node> listNode) {
        if (listNode == null || listNode.isEmpty()) {
            return false;
        }

        for (Node no : listNode) {
            if (isElementNode(no)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isElementNode(Node node) {

        if (!(node instanceof Element)) {
            return false;
        }
        Element element = (Element) node;
        String tag = element.tagName();
        return !tag.equals("br") && !tag.equals("b") && !tag.equals("strong");
    }

    public static String removeTagAndContent(String html, String tag) {
        String startTag = "<" + tag + ">";
        int start = html.indexOf(startTag);
        while (start > 0) {
            String endTag = "</" + tag + ">";
            int end = html.indexOf(endTag);
            html = html.substring(0, start) + html.substring(end + endTag.length(), html.length());
            start = html.indexOf("<" + tag + ">");
        }

        return html;
    }

    public static ProductAmz createBasicProductAmz(AliexProductFull aliexProductFull, AliexStoreInfo aliexStoreInfo, float origiPrice) {
        ProductAmz productAmz = new ProductAmz();
//        productAmz.setItem_sku(aliexStoreInfo.getPrefix().toUpperCase() + FuncUtil.createSaltNumber(5) + "_" + aliexProductFull.getId());
        productAmz.setItem_sku(aliexProductFull.item_sku);
        productAmz.setAliexId(aliexProductFull.getId());
        productAmz.setExternal_product_id_type("UPC");
        productAmz.setFeed_product_type(aliexStoreInfo.getProductType());
        productAmz.setQuantity("100");
        productAmz.setFulfillment_latency("5");
        productAmz.setMfg_minimum("10");
        productAmz.setUnit_count("1");
        productAmz.setUnit_count_type("PC");
        productAmz.setItem_package_quantity("1");
        productAmz.setNumber_of_items("1");
        productAmz.setMaterial_type("other");
        productAmz.setBrand_name(aliexStoreInfo.getBrandName());
        productAmz.setImageUrl(aliexProductFull.getProductImages());
        productAmz.setShipping_method(aliexProductFull.shipingMethod);
        
        if (!StringUtils.isEmpty(aliexStoreInfo.getMain_key())) {
            productAmz.setMain_keywords(aliexStoreInfo.getMain_key());
        }

        productAmz.setBullet_point1(aliexProductFull.bullet_point1);
        productAmz.setBullet_point2(aliexProductFull.bullet_point2);
        productAmz.setBullet_point3(aliexProductFull.bullet_point3);
        productAmz.setBullet_point4(aliexProductFull.bullet_point4);
        productAmz.setBullet_point5(aliexProductFull.bullet_point5);
        productAmz.setPart_number(productAmz.getItem_sku().substring(0, productAmz.getItem_sku().length() - 2));

        productAmz.setItem_name(aliexProductFull.getTitle());
        productAmz.setGeneric_keywords(aliexProductFull.generic_keywords);
        productAmz.setProduct_description(aliexProductFull.description);
        productAmz.setItem_name(aliexProductFull.getTitle());

        productAmz.setItem_type(aliexStoreInfo.getItemType());
        productAmz.setTarget_audience_keywords(aliexStoreInfo.getAudienceKeyword());
        productAmz.setTarget_audience_keywords1(aliexStoreInfo.getAudienceKeyword());
        productAmz.setDepartment_name(aliexStoreInfo.getDepartmentName());

        productAmz.setUnit_count_type("PC");
        productAmz.setMaterial_type("other");
        productAmz.setManufacturer(productAmz.getBrand_name());
        productAmz.setType(ProductTypes.TYPE_NORMAL, aliexStoreInfo);
        

        return productAmz;
    }
}
