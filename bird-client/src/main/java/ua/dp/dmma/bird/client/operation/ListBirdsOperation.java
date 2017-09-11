package ua.dp.dmma.bird.client.operation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import ua.dp.dmma.bird.client.dto.BirdData;

public class ListBirdsOperation extends BaseOperation {

	@Override
	public void execute() {
		List<BirdData> list = ClientBuilder.newClient().target(getServerURL()).path("bird").request()
				.get(new GenericType<List<BirdData>>() {
				});
		Logger.getLogger(ListBirdsOperation.class.getName()).log(Level.INFO, String
				.format("Birds table%n %s%n %s %s", getTableHeader(), getTableContent(list), getTableCount(list)));
	}

	private String getTableHeader() {
		return new String("|name    |color    |weight    |height    |");
	}

	private String getTableContent(List<BirdData> list) {
		StringBuilder sb = new StringBuilder();
		
		for (BirdData birdData : list) {
			sb.append("|");
			sb.append(birdData.getName());
			sb.append("    |");
			sb.append(birdData.getColor());
			sb.append("    |");
			sb.append(birdData.getWeight());
			sb.append("    |");
			sb.append(birdData.getHeight());
			sb.append("    |");
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	private String getTableCount(List<BirdData> list) {
		return new String("Total number of sirds present on the server is " + list.size());
	}
}
