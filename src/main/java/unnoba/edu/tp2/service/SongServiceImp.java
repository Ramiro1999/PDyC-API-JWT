package unnoba.edu.tp2.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unnoba.edu.tp2.Model.Genre;
import unnoba.edu.tp2.Model.Song;
import unnoba.edu.tp2.repository.SongRepository;

import java.util.List;

@Service
public class SongServiceImp implements SongService {

    @Autowired
    public SongRepository songRepository;
    @Override
    public List<Song> getSongs() {
        return songRepository.findAll();
    }

    @Override
    public List<Song> getSongsByAuthorGenre(String author, Genre genre) {
        return songRepository.findByAuthorAndGenre(author,genre);
    }
}
