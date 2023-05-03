package unnoba.edu.tp2;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import unnoba.edu.tp2.resources.SongResource;


@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){
        register(SongResource.class);
    }



}


