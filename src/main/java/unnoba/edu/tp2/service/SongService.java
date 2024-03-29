package unnoba.edu.tp2.service;

import unnoba.edu.tp2.Model.Genre;
import unnoba.edu.tp2.Model.Song;

import java.util.List;

public interface SongService {

    public List<Song> getSongs();


    public List<Song> getSongsByAuthorGenre(String author, Genre genre);
}
