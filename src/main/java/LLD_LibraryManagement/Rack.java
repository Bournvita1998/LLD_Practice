package LLD_LibraryManagement;

public class Rack {
    private int rackNo;
    private BookCopy bookCopy;

    public Rack(int rackNo) {
        this.rackNo = rackNo;
        this.bookCopy = null;
    }

    public int getRackNo() {
        return rackNo;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public boolean isEmpty() {
        return bookCopy == null;
    }

    public void placeBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
        bookCopy.setRackNo(this.rackNo);
    }

    public void removeBookCopy() {
        if (bookCopy != null) {
            bookCopy.setRackNo(null);
            bookCopy = null;
        }
    }
}

