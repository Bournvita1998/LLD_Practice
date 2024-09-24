package LLD_LibraryManagement;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userId;
    private String name;
    private Map<String, BookCopy> borrowedBooks;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.borrowedBooks = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean canBorrowMoreBooks() {
        return borrowedBooks.size() < 5;
    }

    public void borrowBook(BookCopy bookCopy, String dueDate) {
        borrowedBooks.put(bookCopy.getBookCopyId(), bookCopy);
        bookCopy.setBorrowedBy(this.userId);
        bookCopy.setDueDate(dueDate);
    }

    public BookCopy returnBook(String bookCopyId) {
        if (borrowedBooks.containsKey(bookCopyId)) {
            BookCopy bookCopy = borrowedBooks.remove(bookCopyId);
            bookCopy.setBorrowedBy(null);
            bookCopy.setDueDate(null);
            return bookCopy;
        }
        return null;
    }

    public Map<String, BookCopy> getBorrowedBooks() {
        return borrowedBooks;
    }
}

