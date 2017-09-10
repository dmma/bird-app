package ua.dp.dmma.bird.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dmma
 */
@Configuration
@ComponentScan(basePackages = { "ua.dp.dmma.bird.server.service.resource", "ua.dp.dmma.bird.server.service.storage" })
public class SpringAnnotationConfig {

}
