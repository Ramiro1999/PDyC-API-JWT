package unnoba.edu.tp2.service;

import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unnoba.edu.tp2.DTO.AddSongPlaylistDTO;
import unnoba.edu.tp2.DTO.PlaylistDTO;
import unnoba.edu.tp2.Model.Playlist;
import unnoba.edu.tp2.Model.Song;
import unnoba.edu.tp2.Model.User;
import unnoba.edu.tp2.exceptions.PlaylistNotFoundException;
import unnoba.edu.tp2.exceptions.SongNotFoundException;
import unnoba.edu.tp2.repository.PlaylistRepository;
import unnoba.edu.tp2.repository.SongRepository;
import unnoba.edu.tp2.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImp implements PlaylistService {

    @Autowired
    public PlaylistRepository playlistRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public SongRepository songRepository;
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

    @Override
    public void addSong(Long id, Long idSong, String userEmail) throws ForbiddenException, PlaylistNotFoundException, SongNotFoundException {
        Playlist playlistBD = playlistRepository.findById(id).orElse(null);
        User userLogged = userRepository.findFirstByEmail(userEmail);
        Song song = songRepository.findById(idSong).orElse(null);
        if(playlistBD==null){
            throw new PlaylistNotFoundException("Playlist no encontrada en la BD");
        }
        if(song==null){
            throw new SongNotFoundException("Cancion no encontrada en la BD");
        }
        if(Objects.equals(userLogged.getEmail(), playlistBD.getUser().getEmail())){
            playlistBD.getSongs().add(song);
            playlistRepository.save(playlistBD);
        }else{
            throw new ForbiddenException("no se puede modificar la playlist, no eres el dueño");

        }

    }



    @Override
    public void deleteSong(Long id_playlist, Long id_song,String userEmail) throws ForbiddenException, PlaylistNotFoundException, SongNotFoundException {
        Playlist playlistBD = playlistRepository.findById(id_playlist).orElse(null);
        User userLogged = userRepository.findFirstByEmail(userEmail);
        Song song = songRepository.findById(id_song).orElse(null);
        boolean existSongInPlaylist = playlistRepository.existSongInPlaylist(id_playlist,song);
        if(playlistBD==null){
            throw new PlaylistNotFoundException("Playlist no encontrada en la BD");
        }
        if(!existSongInPlaylist){
            throw new SongNotFoundException("Cancion no encontrada en la playlist");
        }
        if(Objects.equals(userLogged.getEmail(), playlistBD.getUser().getEmail())){
            playlistBD.setSongs(deleteSong(playlistBD.getSongs(),song));
            playlistRepository.save(playlistBD);
        }else{
            throw new ForbiddenException("no se puede modificar la playlist, no eres el dueño");
        }

    }

    public ArrayList<Song> deleteSong(List<Song>songs,Song song){
        songs = songs.stream()
                .filter(s -> !Objects.equals(s.getId(), song.getId()))
                .collect(Collectors.toList());
        return (ArrayList<Song>) songs;
    }


    @Override
    public void deletePlaylist(Long id, String userEmail) throws ForbiddenException, PlaylistNotFoundException {
        Playlist playlistBD = playlistRepository.findById(id).orElse(null);
        User userLogged = userRepository.findFirstByEmail(userEmail);
        if(playlistBD==null){
            throw new PlaylistNotFoundException("Playlist no encontrada en la BD");
        }
        if(Objects.equals(userLogged.getEmail(), playlistBD.getUser().getEmail())){
            playlistRepository.delete(playlistBD);
        }else{
            throw new ForbiddenException("no se puede modificar la playlist, no eres el dueño");
        }
    }


}
