package unnoba.edu.tp2.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unnoba.edu.tp2.Model.Genre;
import unnoba.edu.tp2.Model.Song;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song,Long> {

    List<Song> findByAuthorAndGenre(String author, Genre genre);


}
