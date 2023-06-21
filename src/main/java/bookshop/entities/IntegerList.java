package bookshop.entities;

public class IntegerList {
    private Integer bookDetailId;
    private Integer bookId;
    private Integer formatId;

    public IntegerList() {
    }

    public IntegerList(Integer bookDetailId, Integer bookId, Integer formatId) {
        this.bookDetailId = bookDetailId;
        this.bookId = bookId;
        this.formatId = formatId;
    }

    public Integer getBookDetailId() {
        return bookDetailId;
    }

    public void setBookDetailId(Integer bookDetailId) {
        this.bookDetailId = bookDetailId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getFormatId() {
        return formatId;
    }

    public void setFormatId(Integer formatId) {
        this.formatId = formatId;
    }

    @Override
    public String toString() {
        return "IntegerList{" +
                "bookId=" + bookId +
                ", formatId=" + formatId +
                '}';
    }
}
