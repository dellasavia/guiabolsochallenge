package br.com.guiabolso.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author daniel.savia
 */
public class EditionsEni extends ExtractISBN {

    @Override
    public String getValue(String url) {
        String result = DEFAULT_VALUE;

        String html = getContentPage(url);

        Document doc = Jsoup.parse(html);
        Elements description = doc.getElementsByClass("Characteristics");

        if (description != null && !description.isEmpty()) {
            Elements firstContent = description.get(0).getElementsByClass("TContent");
            if (firstContent != null && !firstContent.isEmpty()) {
                String temp = firstContent.get(0).getElementsContainingOwnText("ISBN").html();
                if (temp != null && !"".equals(temp)) {
                    result = temp.replaceAll("ISBN :", "").replaceAll("-", "");
                }
            }
        }

        return result;
    }

}
