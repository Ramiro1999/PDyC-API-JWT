package unnoba.edu.tp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unnoba.edu.tp2.Model.Book;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


}
