package ua.dp.dmma.bird.server.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import ua.dp.dmma.bird.server.resource.BirdResource;

/**
 * @author dmma
 */
@ApplicationPath("/")
public class ServerApplicationConfig extends ResourceConfig {
	public ServerApplicationConfig() {
		register(BirdResource.class);
	}
}
