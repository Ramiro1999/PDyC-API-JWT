package unnoba.edu.tp2.resources;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import unnoba.edu.tp2.DTO.AddSongPlaylistDTO;
import unnoba.edu.tp2.DTO.PlaylistDTO;
import unnoba.edu.tp2.DTO.PlaylistWithSongsDTO;
import unnoba.edu.tp2.DTO.SongDTO;
import unnoba.edu.tp2.Model.Playlist;
import unnoba.edu.tp2.exceptions.PlaylistNotFoundException;
import unnoba.edu.tp2.exceptions.SongNotFoundException;
import unnoba.edu.tp2.service.PlaylistService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/playlists")
public class PlaylistResource {

    @Autowired
    private PlaylistService playlistService;

    private ModelMapper modelMapper = new ModelMapper();

    //obtener todas las playlists
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getPlaylists(){
        List<PlaylistDTO> playlistDTO = playlistService.getPlaylists().stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistDTO.class))
                .collect(Collectors.toList());
        return Response.ok(playlistDTO).build();
    }


    //obtener info de una playlist + sus canciones
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/{id}")
    public Response getPlaylistWithSongs(@PathParam("id") Long id){
        PlaylistWithSongsDTO playlistWithSongsDTO = new PlaylistWithSongsDTO();
        Playlist playlist = playlistService.findById(id);
        if(playlist==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        playlistWithSongsDTO.setName(playlist.getName());
        List<SongDTO> songsDTO = playlistService.getSongsByPlaylistId(id).stream()
                .map(song -> modelMapper.map(song, SongDTO.class))
                .collect(Collectors.toList());
        playlistWithSongsDTO.setSongs(songsDTO);
        return Response.ok(playlistWithSongsDTO).build();


    }

    //Crear playlist
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response create(PlaylistDTO playlistDTO) {
          Playlist playlist = modelMapper.map(playlistDTO,Playlist.class);
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          String userEmail = (String)auth.getPrincipal();
        try {
            playlistService.save(playlist,userEmail);
            return Response.status(Response.Status.CREATED).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    //actualizar nombre de la playlist (preguntar como mostrar mensaje de excepcion)
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    @Path("/{id}")
    public Response updatePlaylist (@PathParam("id")long id,PlaylistDTO playlistDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedEmail = (String) auth.getPrincipal();
        try{
            playlistService.update(id,playlistDTO,loggedEmail);
            return Response.ok().build();
        } catch (ForbiddenException e){
            return Response.status(Response.Status.FORBIDDEN).build();
        }catch (PlaylistNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    //Agregar cancion a playlist
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/{id}/songs")
    public Response addSongToPlaylist(@PathParam("id")long id, AddSongPlaylistDTO addSongPlaylistDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedEmail = (String) auth.getPrincipal();
        try{
            Long idSong = Long.valueOf(addSongPlaylistDTO.getId());
            playlistService.addSong(id,idSong,loggedEmail);
            return Response.ok().build();
        } catch (ForbiddenException e){
            return Response.status(Response.Status.FORBIDDEN).build();
        }catch (PlaylistNotFoundException | SongNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    //Borrar cancion de una playlist
    @DELETE
    @Path("/{id}/songs/{song_id}")
    public Response deleteSongFromPlaylist(@PathParam("id")long id,@PathParam("song_id") long song_id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedEmail = (String) auth.getPrincipal();
        try{
            playlistService.deleteSong(id,song_id,loggedEmail);
            return Response.ok().build();
        } catch (ForbiddenException e){
            return Response.status(Response.Status.FORBIDDEN).build();
        }catch (PlaylistNotFoundException | SongNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    //Borrar playlist
    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id")long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedEmail = (String) auth.getPrincipal();
        try{
            playlistService.deletePlaylist(id,loggedEmail);
            return Response.ok().build();
        } catch (ForbiddenException e){
            return Response.status(Response.Status.FORBIDDEN).build();
        }catch (PlaylistNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
