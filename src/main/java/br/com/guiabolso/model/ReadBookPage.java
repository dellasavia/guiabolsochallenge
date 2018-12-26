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

        Elements elements = doc.getAllElements();
        Book book = new Book();
        StringBuilder description = new StringBuilder();
        boolean newItem = true;
        for (Element item : elements) {

            // tag <h2> indica um novo livro
            if ("h2".equals(item.tagName())) {
                book = new Book();
                description = new StringBuilder();
                book.setTitle(item.text());
                newItem = true;
            }

            if ("p".equals(item.tagName())) {
                description.append(item.text());
                book.setDescription(description.toString());

                Document subDoc = Jsoup.parse(item.html());
                Element link = subDoc.select("a").first();
                if (link != null && newItem) {
                    String url = link.attr("href");
                    ExtractISBN isbn = ExtractISBN.getInstance(url);
                    book.setISBN(isbn.getValue(url));
                    newItem = false;
                }
            }

            if ("book-lang".equals(item.className())) {
                book.setLanguage(item.text());
                books.add(book);
            }
        }
    }

    public List<Book> getBooks() {
        return books;
    }
}
