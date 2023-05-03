package unnoba.edu.tp2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unnoba.edu.tp2.Model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song,Long> {


}
