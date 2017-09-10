package ua.dp.dmma.bird.server.application;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.beust.jcommander.JCommander;

import ua.dp.dmma.bird.server.config.ServerApplicationConfig;
import ua.dp.dmma.bird.server.config.SpringAnnotationConfig;
import ua.dp.dmma.bird.server.config.console.ServerConsoleArguments;
import ua.dp.dmma.bird.server.service.storage.StorageService;

/**
 * 
 * @author dmma
 *
 */
public class ServerApplication {
	private static final int DEFAULT_PORT = 3000;
	private static final int DEFAULT_PROC_COUNT = 2;
	private static final String DEFAULT_STORAGE_LOCATION = System.getProperty("user.home");

	public static void main(String[] args) {
		try {
			ServerConsoleArguments serverArguments = getServerStartUpOptions(args);
			URI baseURI = getBaseURI(serverArguments.getPort());
			setStorageLocationFolder(serverArguments.getFolder());

			final ServerApplicationConfig resourceConfig = new ServerApplicationConfig();
			resourceConfig.property("contextConfig",
					new AnnotationConfigApplicationContext(SpringAnnotationConfig.class));

			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseURI, resourceConfig, false);
			
			Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
			server.start();
			Logger.getLogger(ServerApplication.class.getName()).log(Level.INFO,
					String.format("Application started.%nTry out %s%nStop the application using CTRL+C", baseURI));

			Thread.currentThread().join();
		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static ServerConsoleArguments getServerStartUpOptions(String[] args) {
		ServerConsoleArguments arguments = new ServerConsoleArguments();
		JCommander.newBuilder().addObject(arguments).build().parse(args);
		return arguments;
	}

	private static URI getBaseURI(Integer port) {
		return URI.create("http://localhost:" + getValueOrDefault(port, DEFAULT_PORT));
	}

	private static void setStorageLocationFolder(String storageLocation) throws IOException {
		String locaion = getValueOrDefault(storageLocation, DEFAULT_STORAGE_LOCATION);
		StorageService.storageLocationFolder = Files.createDirectories(Paths.get(locaion + File.separator + "serverdata"));
	}

	private static <T> T getValueOrDefault(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}
}
