/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.transform;

import com.api.ApiBase;
import com.api.dropship.DropApiCall;
import com.api.dropship.req.TransformAliexToAmzReq;
import com.config.Configs;
import com.controller.thread.SaveAmzProductThread;
import com.models.aliex.AliexProductFull;
import com.models.aliex.ProductAttribute;
import com.models.aliex.crawl.CrawlPageProductItem;
import com.models.aliex.crawl.ItemSpecifics;
import com.models.amazon.ProductAmz;
import com.models.aliex.store.AliexPageInfo;
import com.models.aliex.store.AliexStoreInfo;
import com.models.amazon.AmzContentFormat;
import com.models.amazon.DataStore;
import com.models.response.TransformAliexToAmzResponse;
import static com.controller.transform.ProcessTransformAliexToAmz.parseElement;
import static com.controller.transform.ProcessTransformAliexToAmz.removeTagAndContent;
import static com.controller.transform.ProcessTransformAliexToAmz.setBannedProduct;
import com.models.request.ImagePathModel;
import com.models.response.TransformCrawlResponse;
import com.models.response.TransformResponse;
import com.utils.AWSUtil;
import com.utils.CheckingDataUtil;
import com.utils.ComputerIdentifier;
import com.utils.Constants;
import com.utils.FuncUtil;
import com.utils.StringUtils;
import java.io.File;
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
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
 *
 * @author PhanDuy
 */
public class ProcessStoreInfoSvs {

    static HashMap<String, AliexStoreInfo> mapStoreInfo = new HashMap<>();
    static HashMap<String, ArrayList<ProductAmz>> mapProducts = new HashMap<>();
    static HashMap<String, ArrayList<TransformResponse>> mapErrorsProducts = new HashMap<>();
    static HashMap<String, ArrayList<String>> mapBrandName = new HashMap<>();
    
    public void clearMapData() {
//        mapStoreInfo.clear();
        mapProducts.clear();
        mapErrorsProducts.clear();
        mapBrandName.clear();
    }

    public void processStoreInfo(AliexStoreInfo aliexStoreInfo) {
        mapStoreInfo.put(genKey(ComputerIdentifier.diskSerial, aliexStoreInfo.getStoreSign()), aliexStoreInfo);

        for (int i = 0; i < aliexStoreInfo.getTotalPage(); i++) {
            String keyPage = genKey(ComputerIdentifier.diskSerial, aliexStoreInfo.getStoreSign(), (i + 1));

            if (mapProducts.containsKey(keyPage) && mapProducts.get(keyPage) != null) {
                mapProducts.get(keyPage).clear();
            }
        }
    }
    
    public boolean updateAliexItemTitle(CrawlPageProductItem item) {
        String titleKeyword = CheckingDataUtil.containBannedKeyword(item.title);
        if (titleKeyword != null) {
            setBannedProduct.put(item.getId(), titleKeyword);
            System.out.println("Remove " + item.getId() + " because of banned words in title: " + titleKeyword);
            return false;
        }
        item.titleUpdate = CheckingDataUtil.processTrademarkAndBrandname(item.title);
        return true;
    }
    
    public void updateAliexProductFullSku(AliexProductFull aliexProductFull, AliexStoreInfo aliexStoreInfo) {
        aliexProductFull.item_sku = aliexStoreInfo.getPrefix().toUpperCase() + FuncUtil.createSaltNumber(5) + "_" + aliexProductFull.getId();
    }
    
    public void updateAliexProductFullGenericKeywords(AliexProductFull aliexProductFull, AliexStoreInfo aliexStoreInfo) {
        aliexProductFull.generic_keywords = genKeywordsInfo(aliexStoreInfo.getMain_key(), aliexProductFull.title, Configs.fetchingAliexKeywords == 1 ? aliexProductFull.listSearchTerms : null);

        String bannedKey = CheckingDataUtil.containBannedKeyword(aliexProductFull.generic_keywords);
        if (bannedKey != null) {
            ProcessTransformAliexToAmz.setBannedProduct.put(aliexProductFull.id, bannedKey);
        } else {
            aliexProductFull.generic_keywords = CheckingDataUtil.removeTrademark(aliexProductFull.generic_keywords);
            aliexProductFull.generic_keywords = CheckingDataUtil.removeBrandname(aliexProductFull.generic_keywords);
        }
        DataStore.putProductData(aliexProductFull.item_sku, "generic_keywords", aliexProductFull.generic_keywords);
    }
    
    public String genKeywordsInfo(String mainKey, String title, ArrayList<String> listKeywords) {
        Set<String> hashMap = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isTextVisible(mainKey)) {
            String[] mainKeyParts = mainKey.split(Pattern.quote(" "));
            for (String s : mainKeyParts) {
                String lower = s.trim().toLowerCase();

//                if (AWSUtil.isAvoidKeyword(lower) || !StringUtils.isTextOnly(lower)) {
                if (AWSUtil.isAvoidKeyword(lower) || CheckingDataUtil.containMyBrandKeyword(lower)) {
                    continue;
                }

                if (!hashMap.contains(lower)) {
                    hashMap.add(lower);
                    if (sb.length() == 0) {
                        sb.append(s);
                    } else {
                        sb.append(" ").append(s);
                    }
                }

            }

        }
        String[] titlePart = title.split(Pattern.quote(" "));

        for (String s : titlePart) {
            String lower = s.trim().toLowerCase();
            if (AWSUtil.isAvoidKeyword(lower) || CheckingDataUtil.containMyBrandKeyword(lower)) {
                continue;
            }
            if (!hashMap.contains(lower)) {
                hashMap.add(lower);
                if (sb.length() == 0) {
                    sb.append(s);
                } else {
                    sb.append(" ").append(s);
                }
            }
        }

        if (sb.length() < 249 && listKeywords != null) {
            for (String key : listKeywords) {

                String[] subParts = key.split(" ");

                for (String s : subParts) {
                    String lower = s.trim().toLowerCase();

                    if (sb.length() + s.length() > 249) {
                        continue;
                    }

//                    if (AWSUtil.isAvoidKeyword(lower) || !StringUtils.isTextOnly(lower)) {
                    if (AWSUtil.isAvoidKeyword(lower) || CheckingDataUtil.containMyBrandKeyword(lower)) {
                        continue;
                    }
                    if (!hashMap.contains(lower)) {
                        hashMap.add(lower);
                        if (sb.length() == 0) {
                            sb.append(s);
                        } else {
                            sb.append(" ").append(s);
                        }
                        if (sb.length() >= 248) {
                            break;
                        }
                    }
                }

//                if (sb.length() == 0) {
//                    sb.append(key);
//                } else {
//                    sb.append(" ").append(key);
//                }
                if (sb.length() >= 248) {
                    break;
                }
            }
        }

        return sb.toString();
    }
    
    public void updateAliexProductFullItemSpecifics(AliexProductFull aliexProductFull, AliexStoreInfo aliexStoreInfo) {
        ArrayList<ItemSpecifics> listSpecifics = null;

        if (aliexProductFull.getAttributes() != null) {
            for (ProductAttribute productAttribute : aliexProductFull.getAttributes()) {
                if (listSpecifics == null) {
                    listSpecifics = new ArrayList<>();
                }
                listSpecifics.add(new ItemSpecifics(productAttribute.name, productAttribute.value));
            }
            aliexProductFull.updateItemSpecific(listSpecifics, aliexStoreInfo.getFullAudienceTarget());
        }
    }
    
    public void updateAliexProductFullDescription(AliexProductFull aliexProductFull, AliexStoreInfo storePageInfo) {
        ArrayList<Element> listDesParams = genDescriptionHtml(aliexProductFull.htmlDescription, aliexProductFull.id);
        String descriptionForm = storePageInfo.getDescription();
        String tips = storePageInfo.getTip();
        String reasons = storePageInfo.getReasons();

        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.TITLE_KEY, aliexProductFull.title, false);
        descriptionForm = replaceAllPattern(descriptionForm, AmzContentFormat.MAIN_KEYWORD_KEY, storePageInfo.getMain_key(), true);
        descriptionForm = replaceAllPattern(descriptionForm, AmzContentFormat.BRANDNAME_KEY, storePageInfo.getBrandName(), false);

//        descriptionForm = descriptionForm.replace(AmzContentFormat.TITLE_KEY, item_name);
//        descriptionForm = descriptionForm.replaceAll(Pattern.quote(AmzContentFormat.MAIN_KEYWORD_KEY), StringUtils.getPrefixCapitalWord(main_keywords));
//        descriptionForm = descriptionForm.replaceAll(Pattern.quote(AmzContentFormat.BRANDNAME_KEY), brand_name);
        if (tips != null) {
            tips = replaceAllPattern(tips, AmzContentFormat.MAIN_KEYWORD_KEY, storePageInfo.getMain_key(), true);
            tips = replaceAllPattern(tips, AmzContentFormat.BRANDNAME_KEY, storePageInfo.getBrandName(), false);
//            tips = tips.replaceAll(Pattern.quote(AmzContentFormat.MAIN_KEYWORD_KEY), StringUtils.getPrefixCapitalWord(main_keywords));
//            tips = tips.replaceAll(Pattern.quote(AmzContentFormat.BRANDNAME_KEY), brand_name);
        }

        if (reasons != null) {
            reasons = replaceAllPattern(reasons, AmzContentFormat.MAIN_KEYWORD_KEY, storePageInfo.getMain_key(), true);
            reasons = replaceAllPattern(reasons, AmzContentFormat.BRANDNAME_KEY, storePageInfo.getBrandName(), false);
//            reasons = reasons.replaceAll(Pattern.quote(AmzContentFormat.MAIN_KEYWORD_KEY), StringUtils.getPrefixCapitalWord(main_keywords));
//            reasons = reasons.replaceAll(Pattern.quote(AmzContentFormat.BRANDNAME_KEY), brand_name);
        }

        String specifics = getItemSpecificsHtml(aliexProductFull);
//        if (specifics != null) {
//            
//            descriptionForm = descriptionForm.replace(AmzContentFormat.SPECIFIC_KEY, specifics);
//        } else {
//            descriptionForm = descriptionForm.replace(AmzContentFormat.SPECIFIC_KEY, "");
//        }
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.SPECIFIC_KEY, specifics, false);

//        descriptionForm = descriptionForm.replace(AmzContentFormat.BULLET_KEY, "★★★ ");
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.BULLET_KEY, "★★★ ", false);

        int tipsLength = !StringUtils.isEmpty(tips) ? tips.length() : 0;

        int reasonLength = !StringUtils.isEmpty(reasons) ? reasons.length() : 0;

        String descriptionContent = getItemDescriptionHtml(listDesParams, 10000, true);

        int deslength = descriptionContent != null ? descriptionContent.length() : 0;
        if (deslength == 0) {

            descriptionForm = replacePattern(descriptionForm, AmzContentFormat.DESCRIPTION_KEY, "", false);
            descriptionForm = replacePattern(descriptionForm, AmzContentFormat.TIPS_KEY, !StringUtils.isEmpty(tips) ? tips : "", false);
            descriptionForm = replacePattern(descriptionForm, AmzContentFormat.REASON_KEY, !StringUtils.isEmpty(reasons) ? reasons : "", false);

//            descriptionForm = descriptionForm.replace(AmzContentFormat.DESCRIPTION_KEY, "");
//            descriptionForm = descriptionForm.replace(AmzContentFormat.TIPS_KEY, !StringUtils.isEmpty(tips) ? tips : "");
//            descriptionForm = descriptionForm.replace(AmzContentFormat.REASON_KEY, !StringUtils.isEmpty(reasons) ? reasons : "");
        } else {
            int currentLengh = descriptionForm.length();

            if (currentLengh + tipsLength + reasonLength + deslength <= 2000) {

                descriptionForm = replacePattern(descriptionForm, AmzContentFormat.DESCRIPTION_KEY, descriptionContent, false);
                descriptionForm = replacePattern(descriptionForm, AmzContentFormat.TIPS_KEY, !StringUtils.isEmpty(tips) ? tips : "", false);
                descriptionForm = replacePattern(descriptionForm, AmzContentFormat.REASON_KEY, !StringUtils.isEmpty(reasons) ? reasons : "", false);

//                descriptionForm = descriptionForm.replace(AmzContentFormat.DESCRIPTION_KEY, descriptionContent);
//                descriptionForm = descriptionForm.replace(AmzContentFormat.TIPS_KEY, !StringUtils.isEmpty(tips) ? tips : "");
//                descriptionForm = descriptionForm.replace(AmzContentFormat.REASON_KEY, !StringUtils.isEmpty(reasons) ? reasons : "");
            } else {
                descriptionForm = replacePattern(descriptionForm, AmzContentFormat.REASON_KEY, "", false);
//                descriptionForm = descriptionForm.replace(AmzContentFormat.REASON_KEY, "");
                if (currentLengh + tipsLength + deslength <= 2000) {
                    descriptionForm = replacePattern(descriptionForm, AmzContentFormat.DESCRIPTION_KEY, descriptionContent, false);
                    descriptionForm = replacePattern(descriptionForm, AmzContentFormat.TIPS_KEY, !StringUtils.isEmpty(tips) ? tips : "", false);

//                    descriptionForm = descriptionForm.replace(AmzContentFormat.DESCRIPTION_KEY, descriptionContent);
//                    descriptionForm = descriptionForm.replace(AmzContentFormat.TIPS_KEY, !StringUtils.isEmpty(tips) ? tips : "");
                } else {
                    descriptionForm = replacePattern(descriptionForm, AmzContentFormat.TIPS_KEY, "", false);
//                    descriptionForm = descriptionForm.replace(AmzContentFormat.TIPS_KEY, "");
                    if (currentLengh + deslength <= 2000) {
                        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.DESCRIPTION_KEY, !StringUtils.isEmpty(descriptionContent) ? descriptionContent : "", false);
//                        descriptionForm = descriptionForm.replace(AmzContentFormat.DESCRIPTION_KEY, descriptionContent != null ? descriptionContent : "");
                    } else {
                        int remain = 2000 - currentLengh;
                        descriptionContent = getItemDescriptionHtml(listDesParams, remain, true);

                        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.DESCRIPTION_KEY, descriptionContent != null ? descriptionContent : "", false);
//                        descriptionForm = descriptionForm.replace(AmzContentFormat.DESCRIPTION_KEY, descriptionContent != null ? descriptionContent : "");
                    }
                }
            }
        }

        descriptionForm = descriptionForm.replaceAll("\\<\\/div\\>", "\\<\\/p\\>");
        descriptionForm = descriptionForm.replaceAll("\\<div\\>", "\\<p\\>");
        aliexProductFull.description = CheckingDataUtil.removeHiddenKeys(descriptionForm);
        updateAliexProductFullBulletPoints(aliexProductFull, storePageInfo, listDesParams);
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
    
    public String getItemDescriptionHtml(ArrayList<Element> list, int maxLength, boolean isForm) {
        String header = null;
        String tail = null;
        String breakLine = "\n";
        if (isForm) {
            header = "<p><b>Product description:</b></p>\n<ul>";
            tail = "</ul>";
            maxLength -= (header.length() + tail.length());
        }

        StringBuilder resultBuilder = new StringBuilder();

        if (list != null && !list.isEmpty()) {

            StringBuilder sb = new StringBuilder();

            int length = 0;

            Element preElement = null;

            for (Element elementLevel1 : list) {
                if (length + elementLevel1.outerHtml().length() + breakLine.length() <= maxLength) {
                    String text = elementLevel1.text().trim();
                    String textLower = text.toLowerCase().trim();
                    if (textLower.contains("shipment") || textLower.contains("payment")
                            || textLower.contains("china") || textLower.contains("aliexpress")) {
                        return sb.toString();
                    }

                    if (!StringUtils.isTextVisible(text)) {
                        if (elementLevel1.tagName().equals("br")) {
                            if (preElement != null && preElement.tagName().equals("br")) {
                                preElement = elementLevel1;
                                continue;
                            } else {
                                if (sb.length() > 0) {
                                    sb.append("</br>").append(breakLine);
                                    length = sb.length();
                                }
                                preElement = elementLevel1;
                                continue;
                            }
                        } else {
                            preElement = elementLevel1;
                            continue;
                        }
                    }

                    if (text.startsWith("[xlmodel]")) {
                        continue;
                    }

                    sb.append(elementLevel1.outerHtml()).append(breakLine);
                    preElement = elementLevel1;
                    length = sb.length();
//                    System.out.println("=======");
//                    System.out.println("" + element.outerHtml());
                } else {

//                    System.out.println("======= Limit");
//                    System.out.println("" + element.outerHtml());
                    Elements listElementLevel1Childs = elementLevel1.children();

//                    for(Element element1 : elements) {
//                        System.out.println("======= child limit");
//                        System.out.println("" + element1.outerHtml());
//                    }
                    Element headerElement = null;
                    String headerHtml = "";
                    ArrayList<String> listHeader = null;
                    int headerIndex = 0;

                    if (listElementLevel1Childs != null && !listElementLevel1Childs.isEmpty()) {

                        for (int i = 0, size = listElementLevel1Childs.size(); i < size; i++) {
                            Element elementLevel2 = listElementLevel1Childs.get(i);
                            String tagName = elementLevel2.tagName();

                            String txt = elementLevel2.text().trim();

                            String textLower = txt.toLowerCase().trim();
                            if (textLower.contains("shipment") || textLower.contains("payment")
                                    || textLower.contains("china") || textLower.contains("aliexpress")) {
                                return sb.toString();
                            }

                            if (txt.startsWith("[xlmodel]")) {
                                continue;
                            }

                            if (!StringUtils.isTextVisible(txt)) {
                                if (elementLevel2.tagName().equals("br")) {
                                    if (preElement != null && preElement.tagName().equals("br")) {
                                        preElement = elementLevel2;
                                        continue;
                                    } else {
                                        if (sb.length() > 0) {
                                            sb.append("</br>").append(breakLine);
                                            length = sb.length();
                                        }
                                        preElement = elementLevel2;
                                        continue;
                                    }
                                } else {
                                    preElement = elementLevel2;
                                    continue;
                                }
                            }

                            if (tagName.equals("strong") || tagName.equals("b")) {
                                headerIndex = i;

                                headerElement = elementLevel2;
                                headerHtml += headerElement.outerHtml() + breakLine.length();

                                if (listHeader == null) {
                                    listHeader = new ArrayList<>();
                                } else {
                                    listHeader.clear();
                                }
                                listHeader.add(headerHtml);

//                                System.out.println("======= Header");
//                                System.out.println("" + element.outerHtml());
                            } else {
                                if (headerElement != null && headerHtml != null) {
                                    if (length + headerHtml.length() + elementLevel2.outerHtml().length() + breakLine.length() <= maxLength) {
                                        if (i == headerIndex + 1) {
                                            String text = elementLevel2.text();
                                            if (text.startsWith("[xlmodel]")) {
                                                continue;
                                            }

                                            if (!text.trim().isEmpty()) {
                                                if (StringUtils.isTextVisible(text)) {
                                                    for (String s : listHeader) {
                                                        sb.append(s).append(breakLine);
                                                        length = sb.length();
                                                    }

                                                    listHeader.clear();

                                                    headerElement = null;
                                                    headerHtml = "";
                                                } else {
                                                    headerIndex++;
                                                }
                                            } else {
                                                listHeader.add(elementLevel2.outerHtml());
                                                headerHtml += elementLevel2.outerHtml();
                                                headerIndex++;
                                            }
                                        }
                                        String elementHtml = elementLevel2.outerHtml();
                                        sb.append(elementHtml).append(breakLine);
                                        preElement = elementLevel2;
                                        length = sb.length();
                                    } else {
                                        break;
                                    }
                                } else {
                                    if (length + elementLevel2.outerHtml().length() + breakLine.length() <= maxLength) {
                                        sb.append(elementLevel2.outerHtml()).append(breakLine);
                                        length = sb.length();
                                    } else {
                                        break;
                                    }

                                }
                            }
                        }
                    }
                    break;
                }

            }
            if (sb.length() > 0) {
                if (isForm) {
                    resultBuilder.append(header);
                }

                resultBuilder.append(sb.toString());
                if (isForm) {
                    resultBuilder.append(tail);
                }
                return resultBuilder.toString();
            } else {
                return null;
            }

        } else {
            return null;
        }
    }
    
    public String getItemSpecificsHtml(AliexProductFull aliexProductFull) {
        StringBuilder sb = null;
        if (aliexProductFull.listItemSpecificses != null && !aliexProductFull.listItemSpecificses.isEmpty()) {

            sb = new StringBuilder();
            sb.append("<p><strong>Product Specifics:</strong></p>\n").append("<ul>");

            for (ItemSpecifics itemSpecifics : aliexProductFull.listItemSpecificses) {
                if (itemSpecifics.isAvailable()) {
                    addContentWithTag(sb, "li", StringUtils.getPrefixCapitalWord(itemSpecifics.getName()), " ", itemSpecifics.getValue(), "\n");
                }
            }
        }

        if (sb != null && sb.length() > 0) {
            sb.append("</ul></br>");
            return sb.toString();
        } else {
            return null;
        }

    }
    
    public void addContentWithTag(StringBuilder sb, String tag, String... content) {

        if (content == null || content.length == 0) {
            return;
        }

        if (sb == null) {
            sb = new StringBuilder();
        }
        sb.append("<").append(tag).append(">");
        for (String s : content) {
            sb.append(s);
        }
        sb.append("</").append(tag).append(">");
    }
    
    public String replacePattern(String form, String key, String content, boolean isNeedUpperPrefix) {
        if (content == null || content.isEmpty()) {
            return form.replace(key, "");
        }

        return form.replace(key, isNeedUpperPrefix ? StringUtils.getPrefixCapitalWord(content) : content);
    }

    public String replaceAllPattern(String form, String key, String content, boolean isNeedUpperPrefix) {
        if (content == null || content.isEmpty()) {
            return form.replaceAll(Pattern.quote(key), "");
        }

        return form.replaceAll(Pattern.quote(key), isNeedUpperPrefix ? StringUtils.getPrefixCapitalWord(content) : content);
    }
    
    private void updateAliexProductFullBulletPoints(AliexProductFull aliexProductFull, AliexStoreInfo aliexStoreInfo, ArrayList<Element> listDesParams) {
        aliexProductFull.setBulletPoints(aliexStoreInfo.getListBulletPoints());
        if (Configs.usingFeatureFromDes == 1) {
            genBulletPointsFromDescriptions(aliexProductFull, listDesParams);
        }

        String[] keyParts = aliexProductFull.generic_keywords.split(Pattern.quote(" "));
        int size = keyParts.length;
        if (size < 5) {
            changeBulletPoint(aliexProductFull, 1, aliexProductFull.generic_keywords);
            changeBulletPoint(aliexProductFull, 2, aliexProductFull.generic_keywords);
            changeBulletPoint(aliexProductFull, 3, aliexProductFull.generic_keywords);
            changeBulletPoint(aliexProductFull, 4, aliexProductFull.generic_keywords);
            changeBulletPoint(aliexProductFull, 5, aliexProductFull.generic_keywords);
            return;
        }
        int one = size / 5;

        ArrayList<String> listWords = new ArrayList<>();
        for (int i = 0; i <= size; i++) {
            if (i == one) {
                changeBulletPoint(aliexProductFull, 1, listWords);
                listWords = new ArrayList<>();
            } else if (i == one * 2) {
                changeBulletPoint(aliexProductFull, 2, listWords);
                listWords = new ArrayList<>();
            } else if (i == one * 3) {
                changeBulletPoint(aliexProductFull, 3, listWords);
                listWords = new ArrayList<>();
            } else if (i == one * 4) {
                changeBulletPoint(aliexProductFull, 4, listWords);
                listWords = new ArrayList<>();
            } else if (i == size) {
                changeBulletPoint(aliexProductFull, 5, listWords);
            }
            if (i < size) {
                listWords.add(keyParts[i]);
            }
        }

        changeBulletPoint(aliexProductFull, AmzContentFormat.BRANDNAME_KEY, aliexStoreInfo.getBrandName() != null ? aliexStoreInfo.getBrandName() : "");
        changeBulletPoint(aliexProductFull, AmzContentFormat.MAIN_KEYWORD_KEY, aliexStoreInfo.getMain_key() != null ? aliexStoreInfo.getMain_key() : "");
    }
    
    public void genBulletPointsFromDescriptions(AliexProductFull aliexProductFull, ArrayList<Element> listDesParams) {
        ArrayList<String> listBullets = null;
        for (Element element: listDesParams) {
            String text = element.text().trim();
            if (isFeatureTextInDes(text.toLowerCase())) {
                listBullets = new ArrayList<>();
                continue;
            } 
            
            if (listBullets != null) {
                if (!StringUtils.isEmpty(text)) {
                    listBullets.add(text);
                    if (listBullets.size() == 5) {
                        break;
                    }
                }
            }
        }
        
        if (listBullets != null && listBullets.size() > 3) {
            aliexProductFull.setBulletPoints(listBullets);
        }
    }
    
    private boolean isFeatureTextInDes(String text) {
        boolean sign1 = text.contains("feature") || text.contains("function") || text.contains("advantage");
        if (!sign1) return false;
        String[] parts = text.split(Pattern.quote(" "));
        return parts.length <= 2;
    }
    
    public void changeBulletPoint(AliexProductFull aliexProductFull, String key, String value) {
        if (aliexProductFull.bullet_point1 != null) {
            aliexProductFull.bullet_point1 = aliexProductFull.bullet_point1.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point1", aliexProductFull.bullet_point1);
        }

        if (aliexProductFull.bullet_point2 != null) {
            aliexProductFull.bullet_point2 = aliexProductFull.bullet_point2.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point2", aliexProductFull.bullet_point2);
        }

        if (aliexProductFull.bullet_point3 != null) {
            aliexProductFull.bullet_point3 = aliexProductFull.bullet_point3.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point3", aliexProductFull.bullet_point3);
        }

        if (aliexProductFull.bullet_point4 != null) {
            aliexProductFull.bullet_point4 = aliexProductFull.bullet_point4.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point4", aliexProductFull.bullet_point4);
        }

        if (aliexProductFull.bullet_point5 != null) {
            aliexProductFull.bullet_point5 = aliexProductFull.bullet_point5.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point5", aliexProductFull.bullet_point5);
        }
    }
    
    public void changeBulletPoint(AliexProductFull aliexProductFull, int number, String words) {
        String pattern = null;
        switch (number) {
            case 1:
                pattern = AmzContentFormat.SEARCH_TERM_1;
                if (aliexProductFull.bullet_point1 != null) {
                    aliexProductFull.bullet_point1 = aliexProductFull.bullet_point1.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point1", aliexProductFull.bullet_point1);
                }

                break;
            case 2:
                pattern = AmzContentFormat.SEARCH_TERM_2;
                if (aliexProductFull.bullet_point2 != null) {
                    aliexProductFull.bullet_point2 = aliexProductFull.bullet_point2.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point2", aliexProductFull.bullet_point2);
                }
                break;
            case 3:
                pattern = AmzContentFormat.SEARCH_TERM_3;
                if (aliexProductFull.bullet_point3 != null) {
                    aliexProductFull.bullet_point3 = aliexProductFull.bullet_point3.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point3", aliexProductFull.bullet_point3);
                }
                break;
            case 4:
                pattern = AmzContentFormat.SEARCH_TERM_4;
                if (aliexProductFull.bullet_point4 != null) {
                    aliexProductFull.bullet_point4 = aliexProductFull.bullet_point4.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point4", aliexProductFull.bullet_point4);
                }
                break;
            case 5:
                pattern = AmzContentFormat.SEARCH_TERM_5;
                if (aliexProductFull.bullet_point5 != null) {
                    aliexProductFull.bullet_point5 = aliexProductFull.bullet_point5.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point5", aliexProductFull.bullet_point5);
                }
                break;
        }
    }
    
    public void changeBulletPoint(AliexProductFull aliexProductFull, int number, ArrayList<String> keywords) {

        String pattern = null;
        switch (number) {
            case 1:
                pattern = AmzContentFormat.SEARCH_TERM_1;
                if (aliexProductFull.bullet_point1 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point1.length(), keywords);
                    aliexProductFull.bullet_point1 = aliexProductFull.bullet_point1.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point1", aliexProductFull.bullet_point1);
                }

                break;
            case 2:
                pattern = AmzContentFormat.SEARCH_TERM_2;
                if (aliexProductFull.bullet_point2 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point2.length(), keywords);
                    aliexProductFull.bullet_point2 = aliexProductFull.bullet_point2.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point2", aliexProductFull.bullet_point2);
                }
                break;
            case 3:
                pattern = AmzContentFormat.SEARCH_TERM_3;
                if (aliexProductFull.bullet_point3 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point3.length(), keywords);
                    aliexProductFull.bullet_point3 = aliexProductFull.bullet_point3.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point3", aliexProductFull.bullet_point3);
                }
                break;
            case 4:
                pattern = AmzContentFormat.SEARCH_TERM_4;
                if (aliexProductFull.bullet_point4 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point4.length(), keywords);
                    aliexProductFull.bullet_point4 = aliexProductFull.bullet_point4.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point4", aliexProductFull.bullet_point4);
                }
                break;
            case 5:
                pattern = AmzContentFormat.SEARCH_TERM_5;
                if (aliexProductFull.bullet_point5 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point5.length(), keywords);
                    aliexProductFull.bullet_point5 = aliexProductFull.bullet_point5.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point5", aliexProductFull.bullet_point5);
                }
                break;
        }
    }
    
    public String genListKeyForBullet(int currentLength, ArrayList<String> listKeys) {

        if (listKeys == null || listKeys.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        HashMap<String, String> hashMap = new HashMap<>();

        for (String key : listKeys) {

            if (hashMap.containsKey(key.trim().toLowerCase())) {
                continue;
            }

            hashMap.put(key.trim().toLowerCase(), "");

            if (currentLength + sb.length() + key.length() > 400) {
                break;
            }

            if (sb.length() == 0) {
                sb.append(key);
            } else {
                sb.append(", ").append(key);
            }

        }

        String result = sb.toString();
        return CheckingDataUtil.processTrademarkAndBrandname(result);
    }
    
    public void removeBrandNameInfo(AliexProductFull aliexProductFull) {
        String brandName = aliexProductFull.getBranName();
        aliexProductFull.title = StringUtils.removeWord(aliexProductFull.title, brandName);
        aliexProductFull.generic_keywords = StringUtils.removeWord(aliexProductFull.generic_keywords, brandName);
        aliexProductFull.description = StringUtils.removeWord(aliexProductFull.description, brandName);
        aliexProductFull.bullet_point1 = StringUtils.removeWord(aliexProductFull.bullet_point1, brandName);
        aliexProductFull.bullet_point2 = StringUtils.removeWord(aliexProductFull.bullet_point2, brandName);
        aliexProductFull.bullet_point3 = StringUtils.removeWord(aliexProductFull.bullet_point3, brandName);
        aliexProductFull.bullet_point4 = StringUtils.removeWord(aliexProductFull.bullet_point4, brandName);
        aliexProductFull.bullet_point5 = StringUtils.removeWord(aliexProductFull.bullet_point5, brandName);
        aliexProductFull.material_type = StringUtils.removeWord(aliexProductFull.material_type, brandName);
        
        DataStore.putProductData(aliexProductFull.item_sku, "item_name", aliexProductFull.title);
        DataStore.putProductData(aliexProductFull.item_sku, "generic_keywords", aliexProductFull.generic_keywords);
        DataStore.putProductData(aliexProductFull.item_sku, "product_description", aliexProductFull.description);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point1", aliexProductFull.bullet_point1);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point2", aliexProductFull.bullet_point2);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point3", aliexProductFull.bullet_point3);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point4", aliexProductFull.bullet_point4);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point5", aliexProductFull.bullet_point5);
        DataStore.putProductData(aliexProductFull.item_sku, "material_type", aliexProductFull.material_type);
    }
    
    public void removeBrandNameInfo(String brandName, ProductAmz productAmz) {
        productAmz.item_name = StringUtils.removeWord(productAmz.item_name, brandName);
        productAmz.generic_keywords = StringUtils.removeWord(productAmz.generic_keywords, brandName);
        productAmz.product_description = StringUtils.removeWord(productAmz.product_description, brandName);
        productAmz.bullet_point1 = StringUtils.removeWord(productAmz.bullet_point1, brandName);
        productAmz.bullet_point2 = StringUtils.removeWord(productAmz.bullet_point2, brandName);
        productAmz.bullet_point3 = StringUtils.removeWord(productAmz.bullet_point3, brandName);
        productAmz.bullet_point4 = StringUtils.removeWord(productAmz.bullet_point4, brandName);
        productAmz.bullet_point5 = StringUtils.removeWord(productAmz.bullet_point5, brandName);
        productAmz.material_type = StringUtils.removeWord(productAmz.material_type, brandName);
        
        DataStore.putProductData(productAmz.item_sku, "item_name", productAmz.item_name);
        DataStore.putProductData(productAmz.item_sku, "generic_keywords", productAmz.generic_keywords);
        DataStore.putProductData(productAmz.item_sku, "product_description", productAmz.product_description);
        DataStore.putProductData(productAmz.item_sku, "bullet_point1", productAmz.bullet_point1);
        DataStore.putProductData(productAmz.item_sku, "bullet_point2", productAmz.bullet_point2);
        DataStore.putProductData(productAmz.item_sku, "bullet_point3", productAmz.bullet_point3);
        DataStore.putProductData(productAmz.item_sku, "bullet_point4", productAmz.bullet_point4);
        DataStore.putProductData(productAmz.item_sku, "bullet_point5", productAmz.bullet_point5);
        DataStore.putProductData(productAmz.item_sku, "material_type", productAmz.material_type);
    }
    
    public void removeBrandInfo(ArrayList<String> listBrandName, ProductAmz productAmz) {
        if (listBrandName == null || listBrandName.isEmpty()) {
            return;
        }

        for (String brandName : listBrandName) {
            if (!StringUtils.isEmpty(brandName)) {
                removeBrandNameInfo(brandName.trim(), productAmz);
            }
        }
    }

    public void processProduct(AliexProductFull aliexProductFull, AliexStoreInfo store) {
        String keyStore = genKey(ComputerIdentifier.diskSerial, store.getStoreSign());
        if (aliexProductFull.getBranName() != null) {
            ArrayList<String> listBranchName = null;
            if (mapBrandName.containsKey(keyStore)) {
                listBranchName = mapBrandName.get(keyStore);
            } else {
                listBranchName = new ArrayList<>();
                mapBrandName.put(keyStore, listBranchName);
            }
            if (!listBranchName.contains(aliexProductFull.getBranName())) {
                listBranchName.add(aliexProductFull.getBranName());
            }
        }
        
        if (setBannedProduct.containsKey(aliexProductFull.getId())) {
            System.out.println("Remove " + aliexProductFull.getId() + " because of banned words: " + setBannedProduct.get(aliexProductFull.getId()));
            return;
        }

        if (aliexProductFull.getShippingPrice() < 0) {
            System.out.println("Remove " + aliexProductFull.getId() + " because of no shipping method");
            return;
        }
        AliexStoreInfo aliexStoreInfo = mapStoreInfo.get(keyStore);
        float origiPrice = aliexProductFull.getProductPrice(aliexProductFull.getFirstPrice(), aliexStoreInfo.getPriceRate());
        if (!aliexProductFull.isHasVarition()) {
            if (origiPrice > aliexStoreInfo.getPriceLimit()) {
                System.out.println("Remove " + aliexProductFull.getId() + " because of over the price limit");
                return;
            }
        }

        if (aliexStoreInfo.isIsOnlyUS()) {
            if (!aliexProductFull.isHasShipFromUS()) {
                System.out.println("Remove " + aliexProductFull.getId() + " because of no ship from US variation");
                return;
            }
        } 
        
        if (StringUtils.isEmpty(aliexProductFull.title)) {
            System.out.println("Remove " + aliexProductFull.getId() + " because of no title");
            return;
        }
        
        if (StringUtils.isEmpty(aliexProductFull.description)) {
            System.out.println("Remove " + aliexProductFull.getId() + " because of no description");
            return;
        }

        ArrayList<ProductAmz> listProductAmz = ProcessTransformAliexToAmz.transform(aliexProductFull, aliexStoreInfo, origiPrice);
//        TransformAliexToAmzReq req = new TransformAliexToAmzReq();
//        req.aliexProductFull = aliexProductFull;
//        req.aliexStoreInfo = aliexStoreInfo;
//        req.fetchingImageFromDes = Configs.fetchingImageFromDes;
//        TransformAliexToAmzResponse res = DropApiCall.doTransformAliexToAmz(req, null);
//        ArrayList<ProductAmz> listProductAmz = res.data;

        if (listProductAmz != null) {
            String key = genKey(ComputerIdentifier.diskSerial, aliexProductFull.getStoreSign(), aliexProductFull.getPageIndex());
            if (!mapProducts.containsKey(key)) {
                mapProducts.put(key, listProductAmz);
            } else {
                ArrayList<ProductAmz> list = mapProducts.get(key);
                list.addAll(listProductAmz);
            }
        } else {
            System.out.println("Can not get any product from id: " + aliexProductFull.getId());
        }
    }
    
    public void processProduct(String id, TransformCrawlResponse res, AliexStoreInfo aliexStoreInfo, int pageIndex, String storeSign, ArrayList<ImagePathModel> imagePathModels) {
        ArrayList<ProductAmz> listProductAmz = ProcessTransformAliexToAmz.transform(
                res,
                aliexStoreInfo,
                imagePathModels
        );
        if (listProductAmz != null) {
            String key = genKey(ComputerIdentifier.diskSerial, storeSign, pageIndex);
            if (!mapProducts.containsKey(key)) {
                mapProducts.put(key, listProductAmz);
            } else {
                ArrayList<ProductAmz> list = mapProducts.get(key);
                list.addAll(listProductAmz);
            }
        } else {
            System.out.println("Can not get any product from id: " + id);
        }
    }
    
    public void processErrorProducts(TransformResponse res, String storeSign, int pageIndex) {
        String key = genKey(ComputerIdentifier.diskSerial, storeSign, pageIndex);
        if (!mapErrorsProducts.containsKey(key)) {
                ArrayList<TransformResponse> list = new ArrayList<>();
                list.add(res);
                mapErrorsProducts.put(key, list);
            } else {
                ArrayList<TransformResponse> list = mapErrorsProducts.get(key);
                list.add(res);
            }
    }

    public void processPageInfo(AliexPageInfo aliexPageInfo) {
        String keyProduct = genKey(ComputerIdentifier.diskSerial, aliexPageInfo.getStoreSign(), aliexPageInfo.getPageIndex());
        ArrayList<ProductAmz> list = mapProducts.get(keyProduct);

        String keyStore1 = genKey(ComputerIdentifier.diskSerial, aliexPageInfo.getStoreSign());
        AliexStoreInfo aliexStoreInfo2 = mapStoreInfo.get(keyStore1);
        boolean isNeedRemoveBrandInfo = mapBrandName.get(keyStore1) != null && !mapBrandName.get(keyStore1).isEmpty();

        if (list != null) {
            ArrayList<ProductAmz> listTemp = new ArrayList<>();
            int productCount = 0;
            boolean isChildProcessing = false;
            int size = list.size();
            System.out.println("Page " + aliexPageInfo.getPageIndex() + ": Total Row => " + size);
            
            int partCount = 0;
            int rowCount = 0;
            int parentCount = 0;
            for (ProductAmz productAmz : list) {
                if (isNeedRemoveBrandInfo) {
                    removeBrandInfo(mapBrandName.get(keyStore1), productAmz);
                }
                productCount ++;
                rowCount ++;
                isChildProcessing = !productAmz.isParent();
                if (!isChildProcessing) {
                    parentCount ++;
                }
                if (productCount == size - 1 || (!isChildProcessing)) {
                    String fileName = null;
                    System.out.println("processPageInfo " + productCount + "/" + size + " " + partCount);
                    if (productCount == size - 1 && partCount == 0) {
                        listTemp.add(productAmz);
                        fileName = genExcelFileNameWithPage(aliexStoreInfo2, aliexPageInfo.getPageIndex());
                        ProcessPageDataSvs.processPageData(listTemp, aliexStoreInfo2, fileName, false);
                    } else {
                        fileName = genExcelFileNameWithPage(aliexStoreInfo2, aliexPageInfo.getPageIndex(), partCount + 1);
                        ProcessPageDataSvs.processPageData(listTemp, aliexStoreInfo2, fileName, false);
                        listTemp.clear();
                        listTemp.add(productAmz);
                        partCount ++;
                        rowCount = 1;
                    }
                } else {
                    listTemp.add(productAmz);
                }
            }
            
            System.out.println("Page " + aliexPageInfo.getPageIndex() + ": Total parent => " + parentCount);
        }
        
        ArrayList<TransformResponse> listErrors = mapErrorsProducts.get(keyProduct);
        if (listErrors != null && !listErrors.isEmpty()) {
            ProcessPageDataSvs.processPageErrorData(listErrors, aliexStoreInfo2, aliexPageInfo.getPageIndex());
        }
    }

    public static String genKey(String diskSerialNumber, String storeSign) {
        return diskSerialNumber + storeSign;
    }

    public static String genKey(String diskSerialNumber, String storeSign, int page) {
        return diskSerialNumber + storeSign + "page" + page;
    }

    public String genExcelFileNameWithPage(AliexStoreInfo aliexStoreInfo, int pageIndex, int indexPart) {
        return "";
    }
    
    public String genExcelFileNameWithPage(AliexStoreInfo aliexStoreInfo, int pageIndex) {
        return "";
    }
    
    public String genExcelFileNameForStore(AliexStoreInfo aliexStoreInfo) {
        return "";
    }
}
