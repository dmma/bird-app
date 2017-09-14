package ua.dp.dmma.bird.server.service.resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.dp.dmma.bird.server.model.BirdSightingData;
import ua.dp.dmma.bird.server.service.storage.StorageService;

/**
 * @author dmma
 */
@Path("/sighting")
@Service
public class SightingResourceService
{
    @Autowired
    private StorageService storageService;

    @POST
    @Consumes("application/json")
    public Response saveSighting(BirdSightingData birdSightingData)
    {
        storageService.addBirdSighting(birdSightingData);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Produces("application/json")
    public Response getSightingList(@QueryParam("name") String birdName, @QueryParam("startDate") Long startDate, @QueryParam("endDate") Long endDate)
    {
        Pattern pattern = Pattern.compile(birdName);
        List<BirdSightingData> filteredList = storageService.getBirdSightingList().stream().filter(bs ->
        {
            Matcher matcher = pattern.matcher(bs.getName());
            return matcher.matches();
        }).filter(bs -> bs.getDateTime() >= startDate && bs.getDateTime() <= endDate)
                        .sorted(Comparator.comparing(BirdSightingData::getName).thenComparing(BirdSightingData::getDateTime)).collect(Collectors.toList());
        return Response.ok(filteredList).build();
    }
}
