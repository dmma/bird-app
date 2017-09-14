package ua.dp.dmma.bird.client.operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
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
            BirdSightingSearchData birdSightingSearchData = getBirdSightingSearchData();
            List<BirdSightingData> list = ClientBuilder.newClient().register(JacksonFeature.class).target(getServerURL()).path("sighting")
                            .queryParam("name", birdSightingSearchData.birdName).queryParam("startDate", birdSightingSearchData.startDate)
                            .queryParam("endDate", birdSightingSearchData.endDate).request().get(new GenericType<List<BirdSightingData>>()
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

    private BirdSightingSearchData getBirdSightingSearchData() throws IOException
    {
        BirdSightingSearchData data = new BirdSightingSearchData();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("Please enter bird name");
            data.birdName = bufferedReader.readLine();
            data.startDate = getDateTimeValueFromConsole("start date", DATE_SDF, bufferedReader);
            data.endDate = getDateTimeValueFromConsole("end date", DATE_SDF, bufferedReader);
        }
        return data;
    }

    private String getTableHeader()
    {
        return "|name    |date                       |";
    }

    private String getTableContent(List<BirdSightingData> list)
    {
        StringBuilder sb = new StringBuilder();
        for (BirdSightingData birdSightingData : list)
        {
            sb.append("|");
            sb.append(birdSightingData.getName());
            sb.append("    |");
            sb.append(DATE_TIME_SDF.format(new Date(birdSightingData.getDateTime())));
            sb.append("    |");
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    private class BirdSightingSearchData
    {
        String birdName;
        Long startDate;
        Long endDate;
    }
}
