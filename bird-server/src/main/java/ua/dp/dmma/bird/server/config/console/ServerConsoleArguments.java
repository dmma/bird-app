package ua.dp.dmma.bird.server.config.console;

import com.beust.jcommander.Parameter;

/**
 * Object wrapper for holding, parsing and validating of server's console
 * arguments.
 * 
 * @see com.beust.jcommander.JCommander
 * @see com.beust.jcommander.JCommander.Builder
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

	public Integer getProcCount() {
		return procCount;
	}

	public void setProcCount(Integer procCount) {
		this.procCount = procCount;
	}

	@Override
	public String toString() {
		return "ServerConsoleArguments [port=" + port + ", procCount=" + procCount + ", folder=" + folder + "]";
	}
}
