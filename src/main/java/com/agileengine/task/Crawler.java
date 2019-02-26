package com.agileengine.task;

import org.jsoup.nodes.Element;

interface Crawler {

    void crawl(String originUrl, String... searchUrls);

    Element crawl(String originUrl, String searchUrl);

}
