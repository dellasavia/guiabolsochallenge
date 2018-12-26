package br.com.guiabolso.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author daniel.savia
 */
public class FundamentalKotlin extends ExtractISBN {

    @Override
    public String getValue(String url) {
        String result = DEFAULT_VALUE;

        String html = getContentPage(url);

        Document doc = Jsoup.parse(html);
        Elements description = doc.getElementsByClass("scondary_content");
        String temp = description.get(0).getElementsContainingOwnText("ISBN").html();

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(temp);
        if (m.find()) {
            result = m.group();
        }

        return result;
    }

}
