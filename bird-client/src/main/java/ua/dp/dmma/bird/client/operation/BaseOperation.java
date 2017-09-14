package ua.dp.dmma.bird.client.operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dmma
 */
public abstract class BaseOperation
{
    private String serverURL;
    protected static final SimpleDateFormat DATE_TIME_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected static final SimpleDateFormat DATE_SDF = new SimpleDateFormat("yyyy-MM-dd");

    public abstract void execute();

    /**
     * Performs execute method for selected operation and than shut down the client application
     *
     * @param portNumber target server port number
     */
    public void executeOperation(int portNumber)
    {
        serverURL = "http://localhost:" + portNumber;
        execute();
        quit();
    }

    private void quit()
    {
        System.exit(0);
    }

    String getServerURL()
    {
        return serverURL;
    }

    Long getDateTimeValueFromConsole(String fieldName, SimpleDateFormat sdf, BufferedReader bufferedReader) throws IOException
    {
        boolean isFilled = false;
        Date dateTime = null;
        while (!isFilled)
        {
            System.out.println(String.format("Please enter %s value in %s format", fieldName, sdf.toPattern()));
            String value = bufferedReader.readLine();
            try
            {
                dateTime = sdf.parse(value);
                isFilled = true;
            }
            catch (ParseException e)
            {
                System.out.println(String.format("Wrong datetime value %s for %s, please try again", value, fieldName));
            }
        }
        return dateTime.getTime();
    }

    Double getDoubleValueFromConsole(String fieldName, BufferedReader bufferedReader) throws IOException
    {
        boolean isFilled = false;
        Double doubleValue = null;
        while (!isFilled)
        {
            System.out.println("Please enter " + fieldName + " value");
            String value = bufferedReader.readLine();
            try
            {
                doubleValue = Double.parseDouble(value);
                isFilled = true;
            }
            catch (NumberFormatException e)
            {
                System.out.println(String.format("Wrong value %s for field %s, please try again", value, fieldName));
            }
        }
        return doubleValue;
    }
}
