package ua.dp.dmma.bird.client.application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.beust.jcommander.JCommander;

import com.beust.jcommander.ParameterException;
import ua.dp.dmma.bird.client.console.ClientConsoleArguments;
import ua.dp.dmma.bird.client.console.command.AddBirdCommand;
import ua.dp.dmma.bird.client.console.command.AddSightingCommand;
import ua.dp.dmma.bird.client.console.command.ListBirdsCommand;
import ua.dp.dmma.bird.client.console.command.ListSightingsCommand;
import ua.dp.dmma.bird.client.console.command.QuitCommand;
import ua.dp.dmma.bird.client.console.command.RemoveCommand;
import ua.dp.dmma.bird.client.operation.AddBirdOperation;
import ua.dp.dmma.bird.client.operation.AddSightingOperation;
import ua.dp.dmma.bird.client.operation.BaseOperation;
import ua.dp.dmma.bird.client.operation.ListBirdsOperation;
import ua.dp.dmma.bird.client.operation.ListSightingsOperation;
import ua.dp.dmma.bird.client.operation.QuitOperation;
import ua.dp.dmma.bird.client.operation.RemoveOperation;

public class ClientApplication
{

    private static final Map<String, Class<? extends BaseOperation>> OPERATIONS = new HashMap<>();

    static
    {
        OPERATIONS.put("-quit", QuitOperation.class);
        OPERATIONS.put("-addbird", AddBirdOperation.class);
        OPERATIONS.put("-listbirds", ListBirdsOperation.class);
        OPERATIONS.put("-remove", RemoveOperation.class);
        OPERATIONS.put("-addsighting", AddSightingOperation.class);
        OPERATIONS.put("-listsightings", ListSightingsOperation.class);
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException
    {
        ClientConsoleArguments arguments = new ClientConsoleArguments();

        try
        {
            JCommander jCommander = JCommander.newBuilder().addObject(arguments).addCommand(new QuitCommand()).addCommand(new AddBirdCommand())
                            .addCommand(new AddSightingCommand()).addCommand(new ListBirdsCommand()).addCommand(new ListSightingsCommand())
                            .addCommand(new RemoveCommand()).build();
            jCommander.parse(args);

            String parsedCommand = jCommander.getParsedCommand();
            validateParsedCommand(parsedCommand);

            checkServerAvailability(arguments.getPort());

            Class<? extends BaseOperation> clazz = OPERATIONS.get(parsedCommand);
            clazz.newInstance().executeOperation(arguments.getPort());
        }
        catch (ParameterException e)
        {
            Logger.getLogger(ClientApplication.class.getName()).log(Level.WARNING, e.getMessage());
            System.exit(0);
        }

    }

    private static void validateParsedCommand(String parsedCommand)
    {
        if (parsedCommand == null)
        {
            Logger.getLogger(ClientApplication.class.getName()).log(Level.WARNING, String.format("Expected one of commands %s", OPERATIONS.keySet()));
            System.exit(0);
        }
    }

    private static void checkServerAvailability(Integer port)
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            serverSocket.setReuseAddress(true);
            Logger.getLogger(ClientApplication.class.getName()).log(Level.WARNING, String.format("Can not connect to server application on port %s", port));
            System.exit(0);
        }
        catch (IOException e)
        {
            Logger.getLogger(ClientApplication.class.getName()).log(Level.INFO, String.format("Connecting to server application on port %s", port));
        }
    }
}
