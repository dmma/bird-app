package ua.dp.dmma.bird.server.application;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.beust.jcommander.ParameterException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.beust.jcommander.JCommander;

import ua.dp.dmma.bird.server.config.ServerApplicationConfig;
import ua.dp.dmma.bird.server.config.SpringAnnotationConfig;
import ua.dp.dmma.bird.server.config.console.ServerConsoleArguments;
import ua.dp.dmma.bird.server.service.storage.StorageService;

import static ua.dp.dmma.bird.server.service.storage.StorageService.BIRD_SIGHTING_STORAGE_FILE_NAME;
import static ua.dp.dmma.bird.server.service.storage.StorageService.BIRD_STORAGE_FILE_NAME;

public class ServerApplication
{
    private static final int DEFAULT_PORT = 3000;
    private static final int DEFAULT_PROC_COUNT = 2;
    private static final String DEFAULT_STORAGE_LOCATION = System.getProperty("user.home");

    public static void main(String[] args)
    {
        try
        {
            ServerConsoleArguments serverArguments = getServerStartUpOptions(args);

            URI baseURI = getBaseURI(serverArguments.getPort());
            setStorageLocationFolder(serverArguments.getFolder());

            final ServerApplicationConfig resourceConfig = new ServerApplicationConfig();
            resourceConfig.property("contextConfig", new AnnotationConfigApplicationContext(SpringAnnotationConfig.class));

            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseURI, resourceConfig, false);

            setProcCount(server, serverArguments.getProcCount());

            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
            server.start();
            Logger.getLogger(ServerApplication.class.getName())
                            .log(Level.INFO, String.format("Application started.%nTry out %s%nStop the application using CTRL+C", baseURI));

            Thread.currentThread().join();
        }
        catch (IOException | InterruptedException ex)
        {
            Logger.getLogger(ServerApplication.class.getName()).log(Level.WARNING, ex.getMessage());
        }
    }

    /**
     * Parses and validates start up input parameters
     *
     * @param args console arguments
     * @return parsed arguments
     */
    private static ServerConsoleArguments getServerStartUpOptions(String[] args)
    {
        ServerConsoleArguments arguments = new ServerConsoleArguments();
        try
        {
            JCommander.newBuilder().addObject(arguments).build().parse(args);
        }
        catch (ParameterException e)
        {
            Logger.getLogger(ServerApplication.class.getName()).log(Level.WARNING, e.getMessage());
            System.exit(0);
        }
        return arguments;
    }

    /**
     * Constructs base URI for server
     *
     * @param port TCP port on which server will listen for incoming requests
     * @return server's URI
     */
    private static URI getBaseURI(Integer port) throws IOException
    {
        checkPortAvailability(getValueOrDefault(port, DEFAULT_PORT));
        return URI.create("http://localhost:" + getValueOrDefault(port, DEFAULT_PORT));
    }

    /**
     * Checks whether the port is available
     *
     * @param port port number
     */
    private static void checkPortAvailability(Integer port)
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            serverSocket.setReuseAddress(true);
        }
        catch (IOException e)
        {
            Logger.getLogger(ServerApplication.class.getName()).log(Level.WARNING, String.format("Port %s is used by another application", port));
            System.exit(0);
        }
    }

    /**
     * Sets storage location for storage service. If folder doesn't exist, method
     * will create them.<br/>
     *
     * @param storageLocation location for creating data storage directory
     * @throws IOException       if an I/O during directory creation
     * @throws SecurityException if {@link SecurityManager#checkWrite(String) checkWrite} or {@link SecurityManager#checkRead(String) checkRead} are failed
     * @see StorageService
     */
    private static void setStorageLocationFolder(String storageLocation) throws IOException
    {
        String location = getValueOrDefault(storageLocation, DEFAULT_STORAGE_LOCATION);
        Path storagePath = Files.createDirectories(Paths.get(location + File.separator + "serverdata"));
        validateAccessPermissions(storagePath);
        validateStorageFilesAccessPermissions(storagePath);
        StorageService.storageLocationFolder = storagePath;
    }

    /**
     * Validates if user has permissions for read and write operation for file
     * @param directoryPath storage directory
     */
    private static void validateStorageFilesAccessPermissions(Path directoryPath)
    {
        validateAccessPermissions(Paths.get(directoryPath + File.separator + BIRD_STORAGE_FILE_NAME));
        validateAccessPermissions(Paths.get(directoryPath + File.separator + BIRD_SIGHTING_STORAGE_FILE_NAME));
    }

    /**
     * Validates if user has permissions for read and write operation for file
     * @param filePath target object for permissions checking
     */
    private static void validateAccessPermissions(Path filePath){
        if (Files.exists(filePath))
        {
            File file = filePath.toFile();
            if (!file.canWrite())
            {
                Logger.getLogger(ServerApplication.class.getName())
                                .log(Level.WARNING, String.format("You do not have write access permission for path %s", file.getAbsolutePath()));
                System.exit(0);
            }
            if (!file.canRead())
            {
                Logger.getLogger(ServerApplication.class.getName())
                                .log(Level.WARNING, String.format("You do not have read access permission for path %s", file.getAbsolutePath()));
                System.exit(0);
            }
        }
    }



    /**
     * Configures thread pool for server's TCP transport
     *
     * @param httpServer        server for configuration
     * @param argumentProcCount number of threads to process requests
     */
    private static void setProcCount(HttpServer httpServer, Integer argumentProcCount)
    {
        Integer procCount = getValueOrDefault(argumentProcCount, DEFAULT_PROC_COUNT);

        NetworkListener listener = httpServer.getListener("grizzly");
        final TCPNIOTransportBuilder builder = TCPNIOTransportBuilder.newInstance();

        final ThreadPoolConfig config = ThreadPoolConfig.defaultConfig();
        config.setCorePoolSize(procCount).setMaxPoolSize(procCount).setQueueLimit(-1);
        builder.setWorkerThreadPoolConfig(config);
        final TCPNIOTransport transport = builder.build();
        listener.setTransport(transport);
    }

    /**
     * Returns value if is not null, otherwise default value will be returned
     *
     * @param value        value to be checked for null
     * @param defaultValue some default value
     * @return value or default if value == null
     */
    private static <T> T getValueOrDefault(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }
}
