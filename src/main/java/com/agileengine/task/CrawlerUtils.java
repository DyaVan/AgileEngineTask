package com.agileengine.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface CrawlerUtils {

    Document loadFromUrl(String url);

    String constructPath(Element element);

}
