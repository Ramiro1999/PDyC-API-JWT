package unnoba.edu.tp2.resources;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import unnoba.edu.tp2.DTO.SongDTO;
import unnoba.edu.tp2.Model.Genre;
import unnoba.edu.tp2.Model.Song;
import unnoba.edu.tp2.service.SongService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/songs")
public class SongResource {
    @Autowired
    private SongService songService;
    private ModelMapper modelMapper = new ModelMapper();

    /*
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getSongs() {
        List<SongDTO> songsDTO = songService.getSongs().stream()
                .map(song -> modelMapper.map(song, SongDTO.class))
                .collect(Collectors.toList());
        return Response.ok(songsDTO).build();
    }


     */
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getSongsByAuthorGenre(@QueryParam("author") String author,@QueryParam("genre") Genre genre ) {
        List<SongDTO> songsByAuthorGenreDTO = songService.getSongsByAuthorGenre(author,genre).stream()
                .map(song -> modelMapper.map(song, SongDTO.class))
                .collect(Collectors.toList());
        return Response.ok(songsByAuthorGenreDTO).build();
    }




}







/*
    @Autowired
    private BookService service;

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getBooks() {
        List<BookDTO> booksDTO = service.getBooks().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        return Response.ok(booksDTO).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response create(newBookDTO newBook) {
        Book book = modelMapper.map(newBook, Book.class);
        try {
            service.saveBook(book);
            return Response.status(Response.Status.CREATED).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, newBookDTO updateBook) {
        Book book = service.findBook(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            book.setTitle(updateBook.getTitle());
            service.saveBook(book);
            return Response.ok().build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") Long id){
        Book book = service.findBook(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(book).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        Book book = service.findBook(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try{
            service.deleteBook(book);
            return Response.ok().build();
        }catch (DataAccessException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

}

 */





