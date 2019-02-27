package com.agileengine.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {

    private static final String targetElementId = "make-everything-ok-button";

    public static void main(String[] args) {
        System.out.println("Hello AgileEngine!");
        List<String> urls = new ArrayList<>(Arrays.asList(args));
        if (urls.size() == 0) {
            urls.add("https://agileengine.bitbucket.io/beKIvpUlPMtzhfAy/samples/sample-0-origin.html");
        }
        new GreenButtonCrawler(targetElementId, new CrawlerUtilsImpl()).crawl(urls.get(0), urls.toArray(new String[0]));
    }
}
