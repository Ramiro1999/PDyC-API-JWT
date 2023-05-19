package unnoba.edu.tp2;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;
import unnoba.edu.tp2.resources.PlaylistResource;
import unnoba.edu.tp2.resources.SongResource;


@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){
        register(SongResource.class);
        register(PlaylistResource.class);
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR,true);
    }



}


