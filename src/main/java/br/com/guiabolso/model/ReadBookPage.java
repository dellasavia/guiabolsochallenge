package br.com.guiabolso.model;

import br.com.guiabolso.domain.Book;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author daniel.savia
 */
public class ReadBookPage {

    private final List<Book> books = new ArrayList<>();

    private final String BOOK_INDICATOR = "h2";

    private final String DESCRIPTION_INDICATOR = "p";

    private final String LANGUAGE_INDICATOR = "book-lang";

    public ReadBookPage(String mainURL) throws MalformedURLException, IOException {
        URL ur1l = new URL(mainURL);
        InputStream is = (InputStream) ur1l.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String html = sb.toString();
        Document doc = Jsoup.parse(html);

        Elements allTagsOfPage = doc.getAllElements();
        Book book = new Book();
        StringBuilder description = new StringBuilder();
        boolean newItem = true;

        for (Element currentTag : allTagsOfPage) {

            if (BOOK_INDICATOR.equals(currentTag.tagName())) {
                book = new Book();
                description = new StringBuilder();
                book.setTitle(currentTag.text());
                newItem = true;
            }

            if (DESCRIPTION_INDICATOR.equals(currentTag.tagName())) {
                description.append(currentTag.text());
                book.setDescription(description.toString());

                Document subDoc = Jsoup.parse(currentTag.html());
                Element linkToGetISBN = subDoc.select("a").first();

                if (linkToGetISBN != null && newItem) {
                    String url = linkToGetISBN.attr("href");
                    ExtractISBN isbn = ExtractISBN.getInstance(url);
                    book.setISBN(isbn.getValue(url));
                    newItem = false;
                }
            }

            if (LANGUAGE_INDICATOR.equals(currentTag.className())) {
                book.setLanguage(currentTag.text());
                books.add(book);
            }
        }
    }

    public List<Book> getBooks() {
        return books;
    }
}
