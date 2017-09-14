package ua.dp.dmma.bird.client.operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import ua.dp.dmma.bird.client.dto.BirdData;

public class AddBirdOperation extends BaseOperation
{

    @Override
    public void execute()
    {
        try
        {
            BirdData request = createBirdData();
            Response response = ClientBuilder.newClient().register(JacksonFeature.class).target(getServerURL()).path("bird").request()
                            .post(Entity.entity(request, MediaType.APPLICATION_JSON));

            if (Status.CREATED.getStatusCode() == response.getStatus())
            {
                System.out.println(String.format("Bird %s successfully added to the database", request.getName()));
            }
            else if (Status.CONFLICT.getStatusCode() == response.getStatus())
            {
                System.out.println(String.format("Bird %s was added to the database earlier", request.getName()));
            }
            else
            {
                System.out.println("Error adding bird to the database");
            }
        }
        catch (IOException | IllegalArgumentException | IllegalAccessException e)
        {
            Logger.getLogger(AddBirdOperation.class.getName()).log(Level.INFO, "Error filling bird data", e);
        }
    }

    private BirdData createBirdData() throws IOException, IllegalArgumentException, IllegalAccessException
    {
        BirdData birdData = new BirdData();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {
            for (Field field : BirdData.class.getDeclaredFields())
            {
                System.out.println("Please enter " + field.getName() + " value");
                String value = bufferedReader.readLine();
                field.setAccessible(true);
                field.set(birdData, value);
            }
        }
        return birdData;
    }
}
