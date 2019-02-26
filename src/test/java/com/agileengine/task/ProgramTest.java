package com.agileengine.task;

import org.junit.Before;
import org.junit.Test;

import static com.agileengine.task.PageLinks.*;
import static org.junit.Assert.*;

public class ProgramTest {

    GreenButtonCrawler greenButtonCrawler;
    CrawlerUtilsImpl crawlerUtils;

    @Before
    public void setUp() {
        crawlerUtils = new CrawlerUtilsImpl();
        greenButtonCrawler = new GreenButtonCrawler("make-everything-ok-button", crawlerUtils);
    }

    @Test
    public void testUrlConnection() {
        assertNotNull(crawlerUtils.loadFromUrl(ORIGIN));
        assertNotNull(crawlerUtils.loadFromUrl(SAMPLE_1));
        assertNotNull(crawlerUtils.loadFromUrl(SAMPLE_2));
        assertNotNull(crawlerUtils.loadFromUrl(SAMPLE_3));
        assertNotNull(crawlerUtils.loadFromUrl(SAMPLE_4));
    }


    @Test
    public void test() {
        assertNotNull(greenButtonCrawler.crawl(ORIGIN, SAMPLE_1));
        assertNotNull(greenButtonCrawler.crawl(ORIGIN, SAMPLE_2));
        assertNotNull(greenButtonCrawler.crawl(ORIGIN, SAMPLE_3));
        assertNotNull(greenButtonCrawler.crawl(ORIGIN, SAMPLE_4));
    }

}