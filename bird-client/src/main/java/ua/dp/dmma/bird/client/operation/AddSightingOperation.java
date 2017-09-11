package ua.dp.dmma.bird.client.operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ua.dp.dmma.bird.client.dto.BirdSightingData;

public class AddSightingOperation extends BaseOperation {

	@Override
	public void execute() {
		try {
			BirdSightingData request = createBirdSightingData();
			Response response = ClientBuilder.newClient().target(getServerURL()).path("sighting").request()
					.post(Entity.entity(request, MediaType.APPLICATION_JSON));

			if (Status.CREATED.getStatusCode() == response.getStatus()) {
				System.out.println("Bird sighting successfully added to the database");
			} else {
				System.out.println("Error adding sighting to the database");
			}
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
			Logger.getLogger(AddBirdOperation.class.getName()).log(Level.INFO, "Error filling sighting data", e);
		}
	}

	private BirdSightingData createBirdSightingData()
			throws IOException, IllegalArgumentException, IllegalAccessException {
		BirdSightingData birdSightingData = new BirdSightingData();
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
			for (Field field : BirdSightingData.class.getDeclaredFields()) {
				System.out.println("Please enter " + field.getName() + " value");
				String value = bufferedReader.readLine();
				field.setAccessible(true);
				field.set(birdSightingData, value);
			}
		}
		return birdSightingData;
	}

}
