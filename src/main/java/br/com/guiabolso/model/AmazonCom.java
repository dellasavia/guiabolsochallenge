package br.com.guiabolso.model;

/**
 *
 * @author daniel.savia
 */
public class AmazonCom extends ExtractISBN {

    @Override
    public String getValue(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }

}
