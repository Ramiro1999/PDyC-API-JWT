package unnoba.edu.tp2.DTO;

import unnoba.edu.tp2.Model.Song;

import java.util.List;

public class PlaylistWithSongsDTO {

    private String name;

    private List<SongDTO> songs;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDTO> songs) {
        this.songs = songs;
    }
}
