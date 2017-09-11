package ua.dp.dmma.bird.client.operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ua.dp.dmma.bird.client.dto.BirdData;

public class AddBirdOperation extends BaseOperation {

	@Override
	public void execute() {
		try {
			BirdData request = createBirdData();
			Response response = ClientBuilder.newClient().target(getServerURL()).path("bird").request()
					.post(Entity.entity(request, MediaType.APPLICATION_JSON));
			System.out.println(response.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BirdData createBirdData() throws IOException, IllegalArgumentException, IllegalAccessException {
		BirdData birdData = new BirdData();
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
			for (Field field : BirdData.class.getDeclaredFields()) {
				System.out.println("Please enter " + field.getName() + " value");
				String value = bufferedReader.readLine();
				field.setAccessible(true);
				field.set(birdData, value);
			}
		}
		return birdData;
	}
}
