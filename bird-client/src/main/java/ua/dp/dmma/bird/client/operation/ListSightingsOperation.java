package ua.dp.dmma.bird.client.operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import org.glassfish.jersey.jackson.JacksonFeature;
import ua.dp.dmma.bird.client.dto.BirdSightingData;

public class ListSightingsOperation extends BaseOperation
{

    @Override
    public void execute()
    {
        try
        {
            String name = getBirdName();
            List<BirdSightingData> list = ClientBuilder.newClient().register(JacksonFeature.class).target(getServerURL()).path("sighting").path(name).request()
                            .get(new GenericType<List<BirdSightingData>>()
                            {
                            });
            Logger.getLogger(ListBirdsOperation.class.getName())
                            .log(Level.INFO, String.format("BirdSightings table%n%s%n%s", getTableHeader(), getTableContent(list)));
        }
        catch (IOException e)
        {
            Logger.getLogger(AddBirdOperation.class.getName()).log(Level.INFO, "Error filling bird name", e);
        }

    }

    private String getTableHeader()
    {
        return "|name    |date    |";
    }

    private String getTableContent(List<BirdSightingData> list)
    {
        StringBuilder sb = new StringBuilder();
        for (BirdSightingData birdSightingData : list)
        {
            sb.append("|");
            sb.append(birdSightingData.getName());
            sb.append("    |");
            sb.append(birdSightingData.getDate());
            sb.append("    |");
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    private String getBirdName() throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("Please enter bird name");
            return bufferedReader.readLine();
        }
    }
}
