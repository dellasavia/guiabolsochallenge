package br.com.guiabolso.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel.savia
 */
public abstract class ExtractISBN {

    public final String DEFAULT_VALUE = "Unavailable";

    public final int MIN_LENGTH = 10;

    public abstract String getValue(String url);

    public static ExtractISBN getInstance(String url) {
        if (url.contains("manning")) {
            return new Manning();
        } else if (url.contains("leanpub")) {
            return new LeanPub();
        } else if (url.contains("packtpub")) {
            return new PackPub();
        } else if (url.contains("fundamental")) {
            return new FundamentalKotlin();
        } else if (url.contains("amazon.com")) {
            return new AmazonCom();
        } else if (url.contains("amazon.de")) {
            return new AmazonDe();
        } else if (url.contains("kuramkitap")) {
            return new KuramKitap();
        } else if (url.contains("raywenderlich")) {
            return new RayWenderLich();
        } else if (url.contains("editions")) {
            return new EditionsEni();
        }
        return null;
    }

    public String getContentPage(String url) {
        String html = null;
        try {
            html = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (MalformedURLException ex) {
            Logger.getLogger(PackPub.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PackPub.class.getName()).log(Level.SEVERE, null, ex);
        }

        return html;
    }

}
