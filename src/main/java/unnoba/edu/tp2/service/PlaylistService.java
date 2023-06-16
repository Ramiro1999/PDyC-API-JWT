package unnoba.edu.tp2.service;

import jakarta.ws.rs.ForbiddenException;
import unnoba.edu.tp2.DTO.AddSongPlaylistDTO;
import unnoba.edu.tp2.DTO.PlaylistDTO;
import unnoba.edu.tp2.Model.Playlist;
import unnoba.edu.tp2.Model.Song;
import unnoba.edu.tp2.exceptions.PlaylistNotFoundException;
import unnoba.edu.tp2.exceptions.SongNotFoundException;

import java.util.List;

public interface PlaylistService {

    public List<Playlist> getPlaylists(String userEmail)  throws ForbiddenException;

    public Playlist findById(Long id);

    public List<Song> getSongsByPlaylistId(Long id);

    public void save(Playlist playlist,String userEmail);

    public void update(Long id, PlaylistDTO playlistDTO, String userEmail) throws ForbiddenException, PlaylistNotFoundException;

    public void deleteSong(Long id, Long id_song,String userEmail) throws ForbiddenException, PlaylistNotFoundException, SongNotFoundException;
    public void addSong(Long id, Long idSong, String userEmail) throws ForbiddenException, PlaylistNotFoundException, SongNotFoundException;

    public void deletePlaylist(Long id,String userEmail) throws ForbiddenException, PlaylistNotFoundException;
}
