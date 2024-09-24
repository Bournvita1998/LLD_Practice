package LLD_LibraryManagement;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Library library = new Library("Library1", 10);

        library.addBook("B1", "Book Title 1", Arrays.asList("Author1", "Author2"), Arrays.asList("Publisher1"), Arrays.asList("BC1", "BC2"));
        library.borrowBook("B1", "U1", "2024-08-01");
        library.returnBookCopy("BC1");
    }
}

