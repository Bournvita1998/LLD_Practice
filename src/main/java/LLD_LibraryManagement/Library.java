package LLD_LibraryManagement;

import java.util.*;

public class Library {
    private String libraryId;
    private List<Rack> racks;
    private Map<String, Book> books;
    private Map<String, BookCopy> bookCopies;
    private Map<String, User> users;

    public Library(String libraryId, int noOfRacks) {
        this.libraryId = libraryId;
        this.racks = new ArrayList<>();
        for (int i = 1; i <= noOfRacks; i++) {
            racks.add(new Rack(i));
        }
        this.books = new HashMap<>();
        this.bookCopies = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void addBook(String bookId, String title, List<String> authors, List<String> publishers, List<String> bookCopyIds) {
        Book book = new Book(bookId, title, authors, publishers);
        books.put(bookId, book);
        List<Integer> addedRacks = new ArrayList<>();

        for (String copyId : bookCopyIds) {
            BookCopy bookCopy = new BookCopy(copyId, book);
            Rack rack = getFirstEmptyRack();
            if (rack != null) {
                rack.placeBookCopy(bookCopy);
                bookCopies.put(copyId, bookCopy);
                addedRacks.add(rack.getRackNo());
            } else {
                System.out.println("Rack not available");
                return;
            }
        }
        System.out.println("Added Book to racks: " + String.join(",", addedRacks.toString()));
    }

    public void removeBookCopy(String bookCopyId) {
        if (bookCopies.containsKey(bookCopyId)) {
            BookCopy bookCopy = bookCopies.get(bookCopyId);
            if (bookCopy.getRackNo() != null) {
                int rackNo = bookCopy.getRackNo();
                Rack rack = racks.get(rackNo - 1);
                rack.removeBookCopy();
                bookCopies.remove(bookCopyId);
                System.out.println("Removed book copy: " + bookCopyId + " from rack: " + rackNo);
            } else {
                System.out.println("Invalid Book Copy ID");
            }
        } else {
            System.out.println("Invalid Book Copy ID");
        }
    }

    public Rack getFirstEmptyRack() {
        for (Rack rack : racks) {
            if (rack.isEmpty()) {
                return rack;
            }
        }
        return null;
    }

    public void borrowBook(String bookId, String userId, String dueDate) {
        if (!books.containsKey(bookId)) {
            System.out.println("Invalid Book ID");
            return;
        }

        User user = getOrCreateUser(userId);
        if (!user.canBorrowMoreBooks()) {
            System.out.println("Overlimit");
            return;
        }

        for (BookCopy bookCopy : bookCopies.values()) {
            if (bookCopy.getBook().getBookId().equals(bookId) && bookCopy.getRackNo() != null) {
                int rackNo = bookCopy.getRackNo();
                Rack rack = racks.get(rackNo - 1);
                rack.removeBookCopy();
                user.borrowBook(bookCopy, dueDate);
                System.out.println("Borrowed Book from rack: " + rackNo);
                return;
            }
        }

        System.out.println("Not available");
    }

    public void borrowBookCopy(String bookCopyId, String userId, String dueDate) {
        if (!bookCopies.containsKey(bookCopyId)) {
            System.out.println("Invalid Book Copy ID");
            return;
        }

        BookCopy bookCopy = bookCopies.get(bookCopyId);
        if (bookCopy.getRackNo() == null) {
            System.out.println("Invalid Book Copy ID");
            return;
        }

        User user = getOrCreateUser(userId);
        if (!user.canBorrowMoreBooks()) {
            System.out.println("Overlimit");
            return;
        }

        int rackNo = bookCopy.getRackNo();
        Rack rack = racks.get(rackNo - 1);
        rack.removeBookCopy();
        user.borrowBook(bookCopy, dueDate);
        System.out.println("Borrowed Book from rack: " + rackNo);
    }

    public void returnBookCopy(String bookCopyId) {
        for (User user : users.values()) {
            BookCopy bookCopy = user.returnBook(bookCopyId);
            if (bookCopy != null) {
                Rack rack = getFirstEmptyRack();
                if (rack != null) {
                    rack.placeBookCopy(bookCopy);
                    System.out.println("Returned book copy " + bookCopyId + " and added to rack: " + rack.getRackNo());
                    return;
                }
            }
        }
        System.out.println("Invalid Book Copy ID");
    }

    private User getOrCreateUser(String userId) {
        if (!users.containsKey(userId)) {
            User user = new User(userId, "User" + userId);
            users.put(userId, user);
        }
        return users.get(userId);
    }
}

