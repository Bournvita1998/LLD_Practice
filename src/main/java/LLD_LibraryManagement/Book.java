package LLD_LibraryManagement;

import java.util.List;

public class Book {
    private String bookId;
    private String title;
    private List<String> authors;
    private List<String> publishers;

    public Book(String bookId, String title, List<String> authors, List<String> publishers) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.publishers = publishers;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getPublishers() {
        return publishers;
    }
}

