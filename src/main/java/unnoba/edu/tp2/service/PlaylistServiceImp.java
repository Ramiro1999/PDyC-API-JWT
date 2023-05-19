package unnoba.edu.tp2.service;

import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unnoba.edu.tp2.DTO.PlaylistDTO;
import unnoba.edu.tp2.Model.Playlist;
import unnoba.edu.tp2.Model.Song;
import unnoba.edu.tp2.Model.User;
import unnoba.edu.tp2.exceptions.PlaylistNotFoundException;
import unnoba.edu.tp2.repository.PlaylistRepository;
import unnoba.edu.tp2.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
public class PlaylistServiceImp implements PlaylistService {

    @Autowired
    public PlaylistRepository playlistRepository;

    @Autowired
    public UserRepository userRepository;
    @Override
    public List<Playlist> getPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist findById(Long id) {
        return playlistRepository.findById(id).orElse(null);
    }

    @Override
    public List<Song> getSongsByPlaylistId(Long id) {
        return playlistRepository.getSongsByPlaylistId(id);
    }

    @Override
    public void save(Playlist playlist,String userEmail) {
        playlist.setUser(userRepository.findFirstByEmail(userEmail));
        playlistRepository.save(playlist);
    }

    @Override
    public void update(Long id, PlaylistDTO playlistDTO, String userEmail) throws ForbiddenException,PlaylistNotFoundException{
        Playlist playlistBD = playlistRepository.findById(id).orElse(null);
        User userLogged = userRepository.findFirstByEmail(userEmail);
        if(playlistBD==null){
            throw new PlaylistNotFoundException("Playlist no encontrada en la BD");
        }else{
            if(Objects.equals(userLogged.getEmail(), playlistBD.getUser().getEmail())){
                playlistBD.setName(playlistDTO.getName());
                playlistRepository.save(playlistBD);
            }else{
                throw new ForbiddenException("no se puede modificar la playlist, no eres el dueño");

            }
        }

    }


}
