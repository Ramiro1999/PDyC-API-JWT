package unnoba.edu.tp2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unnoba.edu.tp2.Model.Book;
import unnoba.edu.tp2.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    private BookRepository repository;

    @Override
    public List<Book> getBooks() {
            return repository.findAll();

    }

    @Override
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Override
    public Book findBook(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteBook(Book book) {
        repository.delete(book);
    }
}
