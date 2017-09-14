package ua.dp.dmma.bird.server.config.console;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * Positive int parameter validator
 *
 * @author dmma
 */
public class PositiveIntegerValidator implements IParameterValidator
{

    @Override
    public void validate(String name, String value) throws ParameterException
    {
        if (value.chars().anyMatch(v -> !Character.isDigit(v)))
        {
            throw new ParameterException("Invalid number value " + value + " Parameter must be digit between 1 and 65535");
        }

        if (Integer.valueOf(value) < 1)
        {
            throw new ParameterException("Parameter " + name + " must be positive. But your passed " + value);
        }
    }
}
