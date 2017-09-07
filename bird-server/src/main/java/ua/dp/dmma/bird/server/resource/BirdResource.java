package ua.dp.dmma.bird.server.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * 
 * @author dmma
 *
 */
@Path("/bird")
public class BirdResource {

	@POST
	public Response saveBird() {
		return Response.ok().build();
	}

	@DELETE
	public Response removeBird(String birdName) {
		return Response.ok().build();
	}

	@GET
	public Response getBirdList() {
		return Response.ok().build();
	}
}
