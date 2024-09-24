package LLD_LibraryManagement;

public class BookCopy {
    private String bookCopyId;
    private Book book;
    private Integer rackNo;
    private String borrowedBy;
    private String dueDate;

    public BookCopy(String bookCopyId, Book book) {
        this.bookCopyId = bookCopyId;
        this.book = book;
        this.rackNo = null;
        this.borrowedBy = null;
        this.dueDate = null;
    }

    public String getBookCopyId() {
        return bookCopyId;
    }

    public Book getBook() {
        return book;
    }

    public Integer getRackNo() {
        return rackNo;
    }

    public void setRackNo(Integer rackNo) {
        this.rackNo = rackNo;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}

