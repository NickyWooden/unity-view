package com.jnj.unity.unity_views;

import java.io.File;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.gemstone.gemfire.cache.RegionService;
import com.gemstone.gemfire.cache.client.Pool;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.management.internal.cli.functions.DeployFunction;
import com.jnj.adf.grid.manager.GridManager;
import com.jnj.adf.grid.manager.MultipleUserSupporter;
import com.jnj.adf.grid.utils.LogUtil;
import com.jnj.unity.unity_views.common.AdfClient;


public class FunctionDeploy {
	
	public static void main(String[] args) {
		FunctionDeploy functionDeploy = new FunctionDeploy();
		try {
			functionDeploy.deploy(new File("C:\\gtang\\sts-bundle\\workspace\\leapfrog\\lpfg-function\\target\\lpfg-function-0.0.5.jar"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private AdfClient client;

	public FunctionDeploy() {
		client = new AdfClient();
		client.connect(ViewConstants.GROUP_ID);
		client.getAdfService().login("", "");
	}

	public void deploy(File jarFile) throws IOException {
		Object[] deployArgs = new Object[2];
		deployArgs[0] = new String[] { jarFile.getName() };
		byte[] bytes = FileUtils.readFileToByteArray(jarFile);
		deployArgs[1] = new byte[][] { bytes };
		Pool pool = GridManager.getInstance().getGemFirePool(ViewConstants.GRID_ID);
		Execution ex = null;
		if (pool.getMultiuserAuthentication()) {
			RegionService rs = MultipleUserSupporter.getRegionServiceForMultiUser(pool);
			ex = FunctionService.onServers(rs);
		} else
			ex = FunctionService.onServers(pool);
		ex = ex.withArgs(deployArgs);
		Object restuls = ex.execute(new DeployFunction()).getResult();
		LogUtil.getCoreLog().info("Jar file:{} deployed,results:{} ", jarFile, restuls);
	}
}
