package ua.dp.dmma.bird.server.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.dp.dmma.bird.server.model.BirdData;
import ua.dp.dmma.bird.server.service.storage.StorageService;

/**
 * 
 * @author dmma
 *
 */
@Path("/bird")
@Service
public class BirdResourceService {

	@Autowired
	private StorageService storageService;

	@POST
	@Consumes("application/json")
	public Response saveBird(BirdData birdData) {
		Status status = storageService.addBird(birdData) ? Status.CREATED : Status.CONFLICT;
		return Response.status(status).build();
	}

	@DELETE
	public Response removeBird(String birdName) {
		return Response.ok().build();
	}

	@GET
	@Produces("application/json")
	public Response getBirdList() {
		return Response.ok(storageService.getBirdList()).build();
	}
}
