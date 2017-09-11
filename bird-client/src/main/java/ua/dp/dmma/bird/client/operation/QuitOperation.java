package ua.dp.dmma.bird.client.operation;

import javax.ws.rs.client.ClientBuilder;

public class QuitOperation extends BaseOperation
{

    @Override
    public void execute()
    {
        ClientBuilder.newClient().target(getServerURL()).path("server").request().post(null);
    }

}
