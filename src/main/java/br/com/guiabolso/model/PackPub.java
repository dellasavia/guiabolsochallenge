package br.com.guiabolso.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author daniel.savia
 */
public class PackPub extends ExtractISBN {

    @Override
    public String getValue(String url) {
        String result = DEFAULT_VALUE;

        String html = getContentPage(url);

        Document doc = Jsoup.parse(html);

        Elements description = doc.getElementsByAttribute("isbn");

        if (description != null) {
            return description.attr("isbn");
        }
        return result;
    }

}
