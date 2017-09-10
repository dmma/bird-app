package ua.dp.dmma.bird.server.config.console;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
/**
 * 
 * @author dmma
 *
 */
public class PortNumberValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		Integer portNumber = Integer.valueOf(value);
		if (portNumber < 1 || portNumber > 65535) {
			throw new ParameterException("Invalid port number " + value + " Port number must be between 1 and 65535");
		}
	}
}
