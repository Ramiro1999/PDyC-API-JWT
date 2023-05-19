package unnoba.edu.tp2.service;

import jakarta.ws.rs.ForbiddenException;
import unnoba.edu.tp2.DTO.PlaylistDTO;
import unnoba.edu.tp2.Model.Playlist;
import unnoba.edu.tp2.Model.Song;
import unnoba.edu.tp2.exceptions.PlaylistNotFoundException;

import java.util.List;

public interface PlaylistService {

    public List<Playlist> getPlaylists();

    public Playlist findById(Long id);

    public List<Song> getSongsByPlaylistId(Long id);

    public void save(Playlist playlist,String userEmail);

    public void update(Long id, PlaylistDTO playlistDTO, String userEmail) throws ForbiddenException, PlaylistNotFoundException;


}
