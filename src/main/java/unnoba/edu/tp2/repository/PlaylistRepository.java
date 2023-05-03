package unnoba.edu.tp2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unnoba.edu.tp2.Model.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Long> {

}
