package ua.dp.dmma.bird.client.application;

import java.util.HashMap;
import java.util.Map;

import com.beust.jcommander.JCommander;

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

public class ClientApplication {

	private static Map<String, Class<? extends BaseOperation>> operations = new HashMap<>();

	static {
		operations.put("-quit", QuitOperation.class);
		operations.put("-addbird", AddBirdOperation.class);
		operations.put("-listbirds", ListBirdsOperation.class);
		operations.put("-remove", RemoveOperation.class);
		operations.put("-addsighting", AddSightingOperation.class);
		operations.put("-listsightings", ListSightingsOperation.class);
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		ClientConsoleArguments arguments = new ClientConsoleArguments();
		JCommander jCommander = JCommander.newBuilder().addObject(arguments).addCommand(new QuitCommand())
				.addCommand(new AddBirdCommand()).addCommand(new AddSightingCommand())
				.addCommand(new ListBirdsCommand()).addCommand(new ListSightingsCommand())
				.addCommand(new RemoveCommand()).build();
		jCommander.parse(args);

		Class<? extends BaseOperation> clazz = operations.get(jCommander.getParsedCommand());
		clazz.newInstance().executeOperation(arguments.getPort());
	}
}
