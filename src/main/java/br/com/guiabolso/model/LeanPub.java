package br.com.guiabolso.model;

/**
 *
 * @author daniel.savia
 */
public class LeanPub extends ExtractISBN {

    @Override
    public String getValue(String html) {
        return DEFAULT_VALUE;
    }

}
