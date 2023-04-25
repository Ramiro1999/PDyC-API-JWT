package unnoba.edu.tp2.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import unnoba.edu.tp2.DTO.BookDTO;
import unnoba.edu.tp2.DTO.newBookDTO;
import unnoba.edu.tp2.Model.Book;
import unnoba.edu.tp2.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/books")
public class BookResource {

    private ModelMapper modelMapper = new ModelMapper();

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





