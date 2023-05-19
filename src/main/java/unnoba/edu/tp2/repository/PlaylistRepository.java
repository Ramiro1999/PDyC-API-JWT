package unnoba.edu.tp2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unnoba.edu.tp2.Model.Playlist;
import unnoba.edu.tp2.Model.Song;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Long> {


    @Query("select p.songs FROM Playlist p where (p.id=:id)")
    List<Song> getSongsByPlaylistId (@Param("id") long id);
}
