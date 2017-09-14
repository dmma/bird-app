package ua.dp.dmma.bird.client.console;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * Port number validator
 *
 * @author dmma
 */
public class PortNumberValidator implements IParameterValidator
{
    @Override
    public void validate(String name, String value) throws ParameterException
    {
        if (value.chars().anyMatch(v -> !Character.isDigit(v)))
        {
            throw new ParameterException("Invalid port number value " + value + " Port number must be digits between 1 and 65535");
        }
        Integer portNumber = Integer.valueOf(value);
        if (portNumber < 1 || portNumber > 65535)
        {
            throw new ParameterException("Invalid port number " + value + " Port number must be between 1 and 65535");
        }
    }
}

