package ua.dp.dmma.bird.client.operation;

/**
 * @author dmma
 */
public abstract class BaseOperation
{
    private String serverURL;

    public abstract void execute();

    /**
     * Performs execute method for selected operation and than shut down the client application
     *
     * @param portNumber target server port number
     */
    public void executeOperation(int portNumber)
    {
        serverURL = "http://localhost:" + portNumber;
        execute();
        quit();
    }

    private void quit()
    {
        System.exit(0);
    }

    String getServerURL()
    {
        return serverURL;
    }
}
