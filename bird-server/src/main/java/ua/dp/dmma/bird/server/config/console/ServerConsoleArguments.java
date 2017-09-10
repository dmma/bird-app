package ua.dp.dmma.bird.server.config.console;

import com.beust.jcommander.Parameter;

/**
 * 
 * @author dmma
 *
 */
public class ServerConsoleArguments {

	@Parameter(names = { "-port", "-p" }, validateWith = PortNumberValidator.class)
	private Integer port;
	@Parameter(names = { "-proc_count", "-pc" }, validateWith = PositiveIntegerValidator.class)
	private Integer procCount;
	@Parameter(names = { "-data", "-d" })
	private String folder;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	@Override
	public String toString() {
		return "ServerConsoleArguments [port=" + port + ", procCount=" + procCount + ", folder=" + folder + "]";
	}

}
