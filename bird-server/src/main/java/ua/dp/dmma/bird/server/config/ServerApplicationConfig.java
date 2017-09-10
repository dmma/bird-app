package ua.dp.dmma.bird.server.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * @author dmma
 */
@ApplicationPath("/")
public class ServerApplicationConfig extends ResourceConfig {
	public ServerApplicationConfig() {
		register(RequestContextFilter.class);
		packages("ua.dp.dmma.bird.server.service.resource");
		register(LoggingFeature.class);
	}
}
