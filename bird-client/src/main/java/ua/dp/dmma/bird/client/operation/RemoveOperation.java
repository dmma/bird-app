package ua.dp.dmma.bird.client.operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class RemoveOperation extends BaseOperation
{

    @Override
    public void execute()
    {
        try
        {
            String name = getBirdName();
            Response response = ClientBuilder.newClient().target(getServerURL()).path("bird").path(name).request().delete();
            if (Status.ACCEPTED.getStatusCode() == response.getStatus())
            {
                System.out.println(String.format("Bird %s successfully removed from the database", name));
            }
            else if (Status.CONFLICT.getStatusCode() == response.getStatus())
            {
                System.out.println(String.format("Bird %s was removed from the database earlier", name));
            }
            else
            {
                System.out.println("Error removing bird from the database");
            }
        }
        catch (IOException e)
        {
            Logger.getLogger(AddBirdOperation.class.getName()).log(Level.INFO, "Error filling bird name", e);
        }

    }

    private String getBirdName() throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("Please enter bird name for removing");
            return bufferedReader.readLine();
        }
    }

}
