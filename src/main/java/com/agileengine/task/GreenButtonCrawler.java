package com.agileengine.task;

import com.google.common.collect.ImmutableMap;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class GreenButtonCrawler implements Crawler{

    private String targetElementId;
    private CrawlerUtils crawlerUtils;
    private static final String TAG = "a";
    private static final String CLASS = "class";
    private static final String REL = "rel";
    private static final String TITLE = "title";
    private static final String TEXT = "text";
    private static final String[] PRIORITY_SEQUENCE = {CLASS, REL, TITLE, TEXT};

    public GreenButtonCrawler(String targetElementId, CrawlerUtils crawlerUtils) {
        this.targetElementId = targetElementId;
        this.crawlerUtils = crawlerUtils;
    }

    private Map<String, String> extractElementData(Element targetElement) {
        if (targetElement != null) {
            Map<String, String> elementSearchData = ImmutableMap.<String, String>builder()
                    .put(CLASS, targetElement.attr("class"))
                    .put(REL, targetElement.attr("rel"))
                    .put(TITLE, targetElement.attr("title"))
                    .put(TEXT, targetElement.text())
                    .build();
            return elementSearchData;
        }
        return emptyMap();
    }


    @Override
    public void crawl(String originUrl, String... searchUrls) {
        Document doc = crawlerUtils.loadFromUrl(originUrl);
        Element targetElement = doc.getElementById(targetElementId);
        Map<String, String> searchData = extractElementData(targetElement);
        for (String searchUrl : searchUrls) {
            crawlOnPage(searchUrl,searchData);
        }
    }

    @Override
    public Element crawl(String originUrl, String searchUrl) {
        Document doc = crawlerUtils.loadFromUrl(originUrl);
        Element targetElement = doc.getElementById(targetElementId);
        Map<String, String> searchData = extractElementData(targetElement);
        return crawlOnPage(searchUrl,searchData);
    }

    private Element crawlOnPage(String url, Map<String, String> searchData) {
        Document pageDoc = crawlerUtils.loadFromUrl(url);
        Element okButton = findElementByTagAndData(pageDoc, TAG, searchData);
        if (okButton != null) {
            System.out.println(String.format("Found button on page %s with path: %s.",url, crawlerUtils.constructPath(okButton)));
            return okButton;
        }
            System.out.println(String.format("Failed to find on page %s.", url));
        return null;
    }



    private Element findElementByTagAndData(Document document,String tag, Map<String, String> searchData) {
        Elements elementsByTag = document.getElementsByTag(tag);
        return recursiveFilter(elementsByTag, emptyList(), 0, searchData);
    }

    private Element recursiveFilter(List<Element> initialElements, List<Element> elements, int priority, Map<String, String> searchData) {
        if (elements.size() == 1) {
            return elements.get(0);
        } else if (priority < PRIORITY_SEQUENCE.length) {
            List<Element> filteredElements = elements.size() == 0 ?
                    filterElementsByDataEntry(initialElements, PRIORITY_SEQUENCE[priority], searchData) :
                    filterElementsByDataEntry(elements, PRIORITY_SEQUENCE[priority], searchData);
            return recursiveFilter(initialElements, filteredElements, ++priority, searchData);
        }
        return null;
    }

    private List<Element> filterElementsByDataEntry(List<Element> elements, String entryName, Map<String, String> searchData) {
        return TEXT.equals(entryName) ? filterElementsByText(elements, searchData.get(TEXT)) :
                filterElementsByAttribute(elements, entryName, searchData.get(entryName));
    }

    private List<Element> filterElementsByAttribute(List<Element> elements, String attrName, String targetAttrValue) {
        return elements.stream().filter(element -> Objects.equals(element.attr(attrName), targetAttrValue)).collect(Collectors.toList());
    }

    private List<Element> filterElementsByText(List<Element> elements, String targetTextValue) {
        return elements.stream().filter(element -> Objects.equals(element.text(), targetTextValue)).collect(Collectors.toList());
    }


}
