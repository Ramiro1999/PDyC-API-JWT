package unnoba.edu.tp2.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import unnoba.edu.tp2.DTO.UserDTO;
import unnoba.edu.tp2.service.UserService;

@Path("/perfil")
public class UserResource {

    @Autowired
    public UserService userService;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response userData(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = (String)auth.getPrincipal();
        if(userEmail!=null){
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(userEmail);
            return Response.ok(userDTO).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }


}
