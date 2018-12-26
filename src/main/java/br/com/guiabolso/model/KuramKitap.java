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
public class KuramKitap extends ExtractISBN {

    @Override
    public String getValue(String url) {
        String result = DEFAULT_VALUE;

        String html = getContentPage(url);

        Document doc = Jsoup.parse(html);
        Elements description = doc.getElementsByClass("prd_tags");
        String temp = description.get(0).html();

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(temp);
        if (m.find()) {
            if (m.group().length() >= 10) {
                result = m.group();
            }
        }

        return result;
    }

}
