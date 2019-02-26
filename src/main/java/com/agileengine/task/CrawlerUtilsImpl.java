package com.agileengine.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class CrawlerUtilsImpl implements CrawlerUtils{

    @Override
    public Document loadFromUrl(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
            System.out.println(String.format("Loaded page from url: %s", url));
        } catch (IOException e) {
            System.out.println(String.format("Failed to load from url: %s", url));
        }
        return document;
    }

    @Override
    public String constructPath(Element element) {
        Element currentNode = element;
        StringBuilder path = new StringBuilder();
        while (currentNode != null) {
            if (currentNode.siblingNodes().size()+1 > 1 ) {
                int index = 0;
                Element previousSibling = currentNode.previousElementSibling();
                while(previousSibling != null) {
                    if(currentNode.tagName().equals(previousSibling.tagName())){
                        index++;
                    }
                    previousSibling = previousSibling.previousElementSibling();
                }
                if (index > 0) {
                    path.insert(0, "]")
                            .insert(0, index)
                            .insert(0, '[');
                }
            }
            path.insert(0, currentNode.tagName());
            path.insert(0, "->");
            currentNode = currentNode.parent();
        }
        path.delete(0, 9);
        return path.toString();
    }
}
