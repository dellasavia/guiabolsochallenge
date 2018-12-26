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
        //String html = new Scanner(new URL(mainURL).openStream(), "UTF-8").useDelimiter("\\A").next();
        Document doc = Jsoup.parse(html);

        Elements elements = doc.getAllElements();
        Book book = new Book();
        StringBuilder description = new StringBuilder();
        boolean newItem = true;
        for (Element item : elements) {

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
                    System.out.println(url);
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

    public static void main(String[] args) throws MalformedURLException, IOException {
        //String html = new Scanner(new URL("https://kotlinlang.org/docs/books.html").openStream(), "UTF-8").useDelimiter("\\A").next();

        ReadBookPage rbp = new ReadBookPage("https://kotlinlang.org/docs/books.html");

        List<Book> books = rbp.getBooks();

        for (Book book : books) {
            System.out.println(book.getTitle());
            System.out.println(book.getDescription());
            System.out.println(book.getLanguage());
            System.out.println(book.getISBN());
            System.out.println("");
        }

    }

}
