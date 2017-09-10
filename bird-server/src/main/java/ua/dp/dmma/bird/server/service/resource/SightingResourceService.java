package ua.dp.dmma.bird.server.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.dp.dmma.bird.server.model.BirdSightingData;
import ua.dp.dmma.bird.server.service.storage.StorageService;

/**
 * 
 * @author dmma
 *
 */
@Path("/sighting")
@Service
public class SightingResourceService {
	@Autowired
	private StorageService storageService;

	@POST
	@Consumes("application/json")
	public Response saveSighting(BirdSightingData birdSightingData) {
		storageService.addBirdSighting(birdSightingData);
		return Response.status(Status.CREATED).build();
	}

	@GET
	@Produces("application/json")
	public Response getSightingList() {
		return Response.ok(storageService.getBirdSightingList()).build();
	}
}
