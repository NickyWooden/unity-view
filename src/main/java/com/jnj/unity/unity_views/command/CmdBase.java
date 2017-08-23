package com.jnj.unity.unity_views.command;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import com.jnj.unity.unity_views.common.AdfClient;

public abstract class CmdBase {
	@Option(name = "-group", usage = "specify the group name you want to operation.")
	protected String group;

	@Option(name = "-grid", usage = "specify the grid name you want to on/off view processor.")
	protected String grid;

	@Option(name = "-user", usage = "specify the user name to login.")
	protected String userName;

	@Option(name = "-password", usage = "specify the password to login.")
	protected String password;

	protected AdfClient client;
	
	public void connectAndLogin(String group, String userName, String password) {
		if (StringUtils.isBlank(group)) {
			System.err.println(
					"No group option is set, please set option -group to specify which group you want to operate!");
			System.exit(-1);
		}
		client = new AdfClient();
		client.connect(group);
		if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
			client.getAdfService().login(userName, password);
		}
	}

	public void parseCmd(String[] args) {
		CmdLineParser.registerHandler(String[].class, StringArrayOptionHandler.class);
		CmdLineParser cp = new CmdLineParser(this);
		try {
			cp.parseArgument(args);
		} catch (CmdLineException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void process(String[] args) {
		parseCmd(args);
		connectAndLogin(group, userName, password);
		doProcess();
	}
	abstract protected void doProcess();
}
