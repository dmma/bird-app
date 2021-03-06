package ua.dp.dmma.bird.client.operation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import org.glassfish.jersey.jackson.JacksonFeature;
import ua.dp.dmma.bird.client.dto.BirdData;

public class ListBirdsOperation extends BaseOperation
{

    @Override
    public void execute()
    {
        List<BirdData> list = ClientBuilder.newClient().register(JacksonFeature.class).target(getServerURL()).path("bird").request()
                        .get(new GenericType<List<BirdData>>()
        {
        });
        list.sort((b1, b2) -> b1.getName().compareTo(b2.getName()));
        Logger.getLogger(ListBirdsOperation.class.getName())
                        .log(Level.INFO, String.format("Birds table%n%s%n%s%s", getTableHeader(), getTableContent(list), getTableCount(list)));
    }

    private String getTableHeader()
    {
        return "|name    |color    |weight    |height    |" + System.getProperty("line.separator") + "------------------------------------------";
    }

    private String getTableContent(List<BirdData> list)
    {
        StringBuilder sb = new StringBuilder();

        for (BirdData birdData : list)
        {
            sb.append("|");
            sb.append(birdData.getName());
            sb.append("    |");
            sb.append(birdData.getColor());
            sb.append("    |");
            sb.append(birdData.getWeight());
            sb.append("    |");
            sb.append(birdData.getHeight());
            sb.append("    |");
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    private String getTableCount(List<BirdData> list)
    {
        return "Total number of birds present on the server is " + list.size();
    }
}
