/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Whitelist;

/**
 *
 * @author PhanDuy
 */
public class DescriptionUtils {

    public static String processDescription(String descriptionHtmlOrigin) {
        StringBuilder sb = new StringBuilder();
        Pattern pt = Pattern.compile("[^!-~ ]");
        Matcher match = pt.matcher(descriptionHtmlOrigin);
        while (match.find()) {
            String s = match.group();
            descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\" + s, "");
        }

        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\<a href=.*?>", "");
        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\<img.*?>", "");
        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\<span.*?>", "");
        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("\\</span>", "");
        descriptionHtmlOrigin = descriptionHtmlOrigin.replaceAll("&nbsp;", "");
        
        Whitelist myCustomWhitelist = new Whitelist();
        myCustomWhitelist.addTags("b", "br", "p", "ul", "div", "li", "strong", "border", "span", "stype");
        String clean = Jsoup.clean(descriptionHtmlOrigin, myCustomWhitelist);
        clean = clean.replaceAll("\\<\\/h2", "\\<\\/h3");
        clean = clean.replaceAll("\\<h2\\>", "\\<h3\\>");

        clean = clean.replaceAll("\\<\\/h1", "\\<\\/h3");
        clean = clean.replaceAll("\\<h1\\>", "\\<h3\\>");

        Document doc = Jsoup.parse(clean);
        List<Node> childs = doc.body().childNodes();
        ArrayList<Element> results = new ArrayList<>();
        Element preElement = null;
        for (int i = 0, size = childs.size(); i < size; i++) {

            Node childNodeLv1 = childs.get(i);

            if (childNodeLv1 instanceof Element) {
                Element elementLv1 = (Element) childNodeLv1;
                if (elementLv1.tagName().equals("div")) {
                    List<Node> listNode = elementLv1.childNodes();
                    if (listNode != null) {
                        for (Node node : listNode) {
                            if (!(node instanceof Element)) {
                                String nodeText = node.toString().trim();
                                if (nodeText.isEmpty()) {
                                    continue;
                                }
                                Element element = new Element(Tag.valueOf("p"), nodeText);
                                element.appendText(nodeText);
                                results.add(element);
                                preElement = element;
                            } else {
                                Element element = (Element) node;
                                if (element.tagName().equals("br")) {
                                    if (preElement != null) {
                                        String preTag = preElement.tagName();
                                        if (!preTag.equals("p") && !preTag.equals("br")) {
                                            results.add(element);
                                            preElement = element;
                                        }
                                    } else {
                                        results.add(element);
                                        preElement = element;
                                    }
                                } else {
                                    String txt = element.text();
                                    if (StringUtils.isTextVisible(txt)) {
                                        results.add(element);
                                        preElement = element;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (elementLv1.tagName().equals("br")) {
                        if (preElement != null) {
                            String preTag = preElement.tagName();
                            if (!preTag.equals("p") && !preTag.equals("br")) {
                                results.add(elementLv1);
                                preElement = elementLv1;
                            }
                        } else {
                            results.add(elementLv1);
                            preElement = elementLv1;
                        }
                    } else {
                        String text = elementLv1.text();
                        if (StringUtils.isTextVisible(text)) {
                            results.add(elementLv1);
                            preElement = elementLv1;
                        }

                    }
                }
            } else {
                String text = childNodeLv1.toString().trim();
                if (StringUtils.isTextVisible(text)) {
                    Element element = new Element(Tag.valueOf("p"), text);
                    element.appendText(text);
                    results.add(element);
                    preElement = element;
                }
            }

        }

        sb.append(clean);
        return sb.toString();
    }
}
