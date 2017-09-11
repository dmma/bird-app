package ua.dp.dmma.bird.client.operation;

/**
 * 
 * @author dmma
 *
 */
public abstract class BaseOperation {

	private String serverURL;

	public abstract void execute();

	public void executeOperation(int portNumber) {
		serverURL = "http://localhost:" + portNumber;
		execute();
		quit();
	}

	private void quit() {
		System.exit(0);
	}

	protected String getServerURL() {
		return serverURL;
	}
}
