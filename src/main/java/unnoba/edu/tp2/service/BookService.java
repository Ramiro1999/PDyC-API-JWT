package unnoba.edu.tp2.service;

import unnoba.edu.tp2.Model.Book;

import java.util.List;

public interface BookService {

    public List<Book> getBooks();

    public Book saveBook(Book book);

    public Book findBook(Long id);

    public void deleteBook(Book book);

}
