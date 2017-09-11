package ua.dp.dmma.bird.server.service.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.dp.dmma.bird.server.service.storage.StorageService;

import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/server")
@Service
public class ServerResourceService
{
    @Autowired
    private StorageService storageService;

    @POST
    public Response shutDownServer()
    {
        storageService.storeDataToTheDisk();
        new Thread(() ->
        {
            try
            {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e)
            {
                Logger.getLogger(ServerResourceService.class.getName()).log(Level.INFO, "Error waiting data storing before shut down", e);
            }
            System.exit(0);
        }).start();
        return Response.ok().build();
    }
}
