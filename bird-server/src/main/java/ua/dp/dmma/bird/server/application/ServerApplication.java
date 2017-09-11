package ua.dp.dmma.bird.server.application;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
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
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        return arguments;
    }

    /**
     * Constructs base URI for server
     *
     * @param port TCP port on which server will listen for incoming requests
     * @return server's URI
     * @throws IOException if port is already used by another application
     */
    private static URI getBaseURI(Integer port) throws IOException
    {
        checkPortAvailability(port);
        return URI.create("http://localhost:" + getValueOrDefault(port, DEFAULT_PORT));
    }

    /**
     * Checks whether the port is available
     *
     * @param port port number
     * @throws IOException if port is not available
     */
    private static void checkPortAvailability(Integer port) throws IOException
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            serverSocket.setReuseAddress(true);
        }
    }

    /**
     * Sets storage location for storage service. If folder doesn't exist, method
     * will create them.<br/>
     *
     * @param storageLocation location for creating data storage directory
     * @throws IOException       if parent directory is not exists
     * @throws SecurityException if {@link SecurityManager#checkWrite(String) checkWrite} or {@link SecurityManager#checkRead(String) checkRead} are failed
     * @see StorageService
     */
    private static void setStorageLocationFolder(String storageLocation) throws IOException
    {
        String location = getValueOrDefault(storageLocation, DEFAULT_STORAGE_LOCATION);
        StorageService.storageLocationFolder = Files.createDirectories(Paths.get(location + File.separator + "serverdata"));
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
