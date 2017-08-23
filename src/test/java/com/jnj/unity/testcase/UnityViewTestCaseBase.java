package com.jnj.unity.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jnj.adf.client.api.ADFService;
import com.jnj.adf.client.api.IRemoteService;
import com.jnj.adf.client.api.JsonObject;
import com.jnj.adf.config.annotations.EnableADF;
import com.jnj.adf.grid.manager.RemoteServiceManager;
import com.jnj.adf.grid.support.context.ADFContext;
import com.jnj.adf.grid.support.system.ADFConfigHelper;
import com.jnj.adf.grid.utils.LogUtil;
import com.jnj.adf.grid.utils.SpringBeanUtils;
import com.jnj.adf.grid.utils.Util;
import com.jnj.adf.grid.view.builder.BuildViewService;
import com.jnj.unity.testbase.FileTools;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = UnityViewTestCaseBase.class)
@ComponentScan(basePackages = "com.jnj.unity.testcase")
public class UnityViewTestCaseBase extends FileTools {

	protected static ADFService adfService;
	private static AtomicBoolean connectionInitialized = new AtomicBoolean();
	private static AtomicBoolean dataInitialized = new AtomicBoolean();

	@EnableADF
	static class UnityTestConfig {

	}

	@BeforeClass
	public static void connectAndLogin() {
		if (connectionInitialized.get()) {
			return;
		}
		connectionInitialized.set(true);
		try {
			SpringBeanUtils.initContext(UnityTestConfig.class);
			adfService = ADFContext.getBean(ADFService.class);
			adfService.connect("", getGroupId());
			loginAfterConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void initStatic() {
		dataInitialized = new AtomicBoolean();
	}

	public static void loginAfterConnect() {
		Boolean withSecurity = ADFConfigHelper.getBoolean("test.withSecurity");
		if (withSecurity) {
			String loginUserId = ADFConfigHelper.getProperty("test.user");
			adfService.login(loginUserId, ADFConfigHelper.getProperty("test.passwd"));
		}
	}

	public static String getGroupId() {
		return ADFConfigHelper.getProperty("test.group");
	}

	@Before
	public void before() {
		if (dataInitialized.get()) {
			return;
		}
		dataInitialized.set(true);
		clearData();
		sleep();
		initData();
		sleep();
	}

	protected String getKeyValue(String[] fieldNames, String jsonString) {
		JsonObject jsobj1 = JsonObject.append(jsonString);
		JsonObject jsobj2 = JsonObject.create();
		for(String filed:fieldNames) {
			jsobj2.append(filed, jsobj1.getValue(filed));
		}
		
		return jsobj2.toJson();
	}
	protected String getFieldValue(String fieldName, String jsonString) {
		JsonObject jo2 = JsonObject.append(jsonString);
		String value = jo2.getValue(fieldName);
		if ("null".equals(value)) {
			value = null;
		}
		return value;
	}

	protected void compareJsonValue(String expectedJson, String fieldName, String targetJson) {
		assertEquals(getFieldValue(fieldName, expectedJson), getFieldValue(fieldName, targetJson));
	}
	protected void compareValue(String expected, String fieldName, String jsonString) {
		assertEquals(expected, getFieldValue(fieldName, jsonString));
	}

	protected void compareEmptyValue(String fieldName, String jsonString) {
		String value = getFieldValue(fieldName, jsonString);
		assertTrue(StringUtils.isEmpty(value));
	}

	protected static void sleep() {
		Util.sleep(10000);//ADFConfigHelper.getInteger("test.waitTime")
	}

	protected void fullCreate(String viewFullPath, String regionFullPth) {
		LogUtil.getCoreLog().info("Calling remote fullCreate on view region:{} with master region:{} ...", viewFullPath,
				regionFullPth);
		IRemoteService<BuildViewService> srv = RemoteServiceManager.getInstance()
				.getRemoteService(BuildViewService.class);
		srv.onServers(regionFullPth).fullCreate(regionFullPth, viewFullPath, "");
		LogUtil.getCoreLog().info("Finished executing fullCreate on servers.");
	}

	protected String getStringFromMap(Map<String, Object> m, String field) {
		if (m != null) {
			Object o = m.get(field);
			return o != null ? o.toString() : null;
		}
		return null;
	}
	
	// need to override this mesthod
	protected void clearData(){
		
	};

	// need to override this method
	protected void initData(){
		
	};
}
