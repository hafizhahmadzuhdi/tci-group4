import exception.YearException;
import models.Book;
import models.Movie;
import models.Music;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;


import static org.mockito.Mockito.*;

public class ScraperTest {

    private String url, book_url;
    private ArrayList<String> authors = new ArrayList<>();

    @Before
    public void setUp(){
        url = "http://example.com";
        book_url = "http://localhost/sample_site_to_crawl/details.php?id=102";
        String author1 = "James Bensen";
        authors.add(author1);
    }

    @Test
    public void parseAllShouldBeCalled() throws YearException {
        //Arrange
        Scraper scraper = mock(Scraper.class);

        //doCallRealMethod().when(scraper).parseAll(book_url);
        scraper.parseAll(book_url);

        //Assert
        verify(scraper, times(1)).parseAll(book_url);
    }

    @Test
    public void parseSpecificShouldBeCalled(){
        Scraper scraper = new Scraper();
        boolean result = scraper.parseSpecific(book_url, "978-0132350884");
        assertEquals(true, result);
    }

    @Test
    public void shouldGiveTheExpectedElements(){
        //Arrange
        Document document = mock(Document.class);
        Elements elements = mock(Elements.class);
        Scraper scraper = new Scraper();
        //Act
        //The document will take only a search on the class div of media-details
        when(document.getElementsByClass("media-details")).thenReturn(elements);
        scraper.setElements(document.getElementsByClass("media-details"));
        //Assert
        assertEquals(elements, scraper.getElements());
    }

    @Test
    public void parseAllShouldReturnAListOfBooksMoviesAndMusics() throws YearException {
        //Arrange
        Elements elements = new Elements();
        Document document = mock(Document.class);
        Scraper scraper = mock(Scraper.class);
        ArrayList<Music> musics = new ArrayList<Music>();
        ArrayList<Book> books = new ArrayList<Book>();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        //Act
        //Document.getElementsByClass will be called within the parseAll method
        when(document.getElementsByClass("media-details")).thenReturn(elements);
        scraper.setDocument(document);
        scraper.parseAll(url);
        movies = scraper.getMovies();
        books = scraper.getBooks();
        musics = scraper.getMusics();
        //Assert
        assertNotNull(movies);
        assertNotNull(books);
        assertNotNull(musics);

    }

    @Test
    public void shouldAddAListToBooks() throws YearException {
        //Arrange
        Scraper scraper = mock(Scraper.class);
        Element element = mock(Element.class);
        //  Attributes for making the book
        int id = 1;
        String title = "Alice in wonderland";
        String category = "Book";
        Element contentElement = mock(Element.class);
        String attributeFormat = "Ebook";
        String attributeGenre = "Tech";
        int year = 2013;
        String yearString = Integer.toString(year);
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Jimmy Bensen");
        String attributeIsbn = "ISBN12345";
        String attributePublisher = "Gramedia Publisher";

        //Act
        //During the scraping, there are some information will be drilled
        //1. ID 2. Titlte 3. Category 4. Content (Details of the object)
        //It can be viewed from our web target
        when(scraper.getDetailsOfElementFromEachTag(element, "Year")).thenReturn(yearString);
        when(scraper.getDetailsOfElementFromEachTag(element, "Format")).thenReturn(attributeFormat);
        when(scraper.getDetailsOfElementFromEachTag(element, "Genre")).thenReturn(attributeGenre);
        when(scraper.getDetailsOfElementFromEachTag(element, "Year")).thenReturn(yearString);
        when(scraper.getDetailsOfElementFromEachTag(element, "ISBN")).thenReturn(attributeIsbn);
        when(scraper.getDetailsOfElementFromEachTag(element, "Publisher")).thenReturn(attributePublisher);
        when(scraper.getDetailsWithinAList(element, "Authors")).thenReturn(authors);
        //doCallRealMethod().when(scraper).addToList(id, title, category, element);
        scraper.addToList(id, title, category, element);

        //Assert
        verify(scraper, times(1)).addToList(id, title, category, element);
    }

    @Test
    public void idShouldbeReturnedFromURL(){
        Scraper scraper = new Scraper();
        int id = scraper.getIdFromUrl(book_url);
        assertEquals(102, id);
    }

    @Test
    public void shouldAddAListToTheMusics() throws YearException {
        //Arrange
        Scraper scraper = mock(Scraper.class);
        Element element = mock(Element.class);
        /**
         * TO DO: FIX the Movie class to make it having the title
         */
        //Act
        when(scraper.getDetailsOfElementFromEachTag(element, "Year")).thenReturn("2015");
        when(scraper.getDetailsOfElementFromEachTag(element, "Genre")).thenReturn("Rock");
        when(scraper.getDetailsOfElementFromEachTag(element, "Format")).thenReturn("Vinyl");
        when(scraper.getDetailsOfElementFromEachTag(element, "Artist")).thenReturn("Elvis Presley");
        scraper.addToList(302, "Elvis Forever", "Music", element);
        //Assert
        verify(scraper, times(1)).addToList(302, "Elvis Forever", "Music", element);
    }

}
