package br.com.guiabolso.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author daniel.savia
 */
public class Manning extends ExtractISBN {

    @Override
    public String getValue(String url) {
        String result = DEFAULT_VALUE;

        String html = getContentPage(url);

        Document doc = Jsoup.parse(html);
        Elements description = doc.getElementsByClass("product-info");

        if (description != null && !description.isEmpty()) {
            String temp = description.get(0).getElementsContainingOwnText("ISBN").html().replaceAll("ISBN", "").replaceAll(" ", "");
            if (temp != null && !"".equals(temp)) {
                result = temp;
            }
        }

        return result;

    }
}
