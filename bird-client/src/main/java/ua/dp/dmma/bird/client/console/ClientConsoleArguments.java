package ua.dp.dmma.bird.client.console;

import com.beust.jcommander.Parameter;

/**
 * @author dmma
 */
public class ClientConsoleArguments
{
    @Parameter(names = { "-serverPort", "-p" })
    private Integer port = 3000;

    public Integer getPort()
    {
        return port;
    }

    public void setPort(Integer port)
    {
        this.port = port;
    }

}
