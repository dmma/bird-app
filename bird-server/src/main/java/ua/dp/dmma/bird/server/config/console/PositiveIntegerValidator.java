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
        if (Integer.valueOf(value) < 0)
        {
            throw new ParameterException("Parameter " + name + " must be positive. But your passed " + value);
        }
    }
}
