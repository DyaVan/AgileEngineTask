package com.agileengine.task;

public class Program {

    private static final String targetElementId = "make-everything-ok-button";

    public static void main(String[] args) {
        System.out.println("Hello AgileEngine!");
        new GreenButtonCrawler(targetElementId, new CrawlerUtilsImpl()).crawl(args[0], args);
    }
}
