package com.jnj.unity.unity_views;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.jnj.adf.client.api.IRemoteService;
import com.jnj.adf.grid.manager.GridManager;
import com.jnj.adf.grid.manager.RemoteServiceManager;
import com.jnj.adf.grid.support.system.ADFConfigHelper;
import com.jnj.adf.grid.view.builder.BuildViewService;
import com.jnj.adf.grid.view.domain.ViewJoin;
import com.jnj.unity.unity_views.common.AdfClient;
import com.jnj.unity.unity_views.common.AdfClient.RemoteServiceType;


public abstract class UnityBaseViewDeploy {

	private AdfClient client;
	public String rulePath = "classpath:";

	private String group;
	private String grid;
	private boolean fullCreate = true;

	public void setAdfClient(AdfClient client) {
		this.client = client;
	}

	public void setFullCreate(boolean fullCreate) {
		this.fullCreate = fullCreate;
	}

	protected static String getTestGroupId() {
		return ADFConfigHelper.getProperty("test.group");
	}

	protected static String getTestGrid() {
		return ADFConfigHelper.getProperty("test.grid");
	}

	public void connect(String group, String grid) {
		this.group = group;
		this.grid = grid;
		client = new AdfClient();
		client.connect(group);
	}

	public void connect() {
		try{
		ADFConfigHelper.initConfig();
		connect(getTestGroupId(), getTestGrid());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRulePath() {
		return rulePath;
	}

	public void setRulePath(String rulePath) {
		this.rulePath = rulePath;
	}
	
	// asset rule
	public void createAssetAllRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_ASSET_ALL_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
		
	// Product rule
	public void createProductUnitsRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_PRODUCT_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	
	// Account rule
	public void createAccountUnitsRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_ACCOUNT_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}

	// productLot rule
	public void createProductLotUnitsRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_PRODUCT_LOT_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	
	// productLot rule
	public void createProductLotUnitsRuleSap(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_PRODUCT_LOT_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	
	// mars customer rule
	public void createMarsCustomerRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_CUSTOMER_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	// mars customer Email rule
	public void createMarsCustomerEmailRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_CUSTOMER_EMAIL_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	
	// project one customer rule
	public void createPjOneCustomerRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_PJONE_CUSTOMER_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	// project one customer Email rule
	public void createPjOneCustomerEmailRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_PJONE_CUSTOMER_EMAIL_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	
	// fusion customer rule
	public void createFusionCustomerRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_FUSION_CUSTOMER_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	// fusion customer Email rule
	public void createFusionCustomerEmailRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_FUSION_CUSTOMER_EMAIL_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	// mentor material rule
	public void createMentorMaterialRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
		String viewPath = ViewConstants.VIEW_MATERIAL_REGION;

		addJoins(viewPath, joins);

		createRule(viewPath, mainPath, otherPaths, fileName);

	}
	
	// mars material rule
	public void createMarsMaterialRule(String mainPath, String[] otherPaths, String fileName, List<ViewJoin> joins) {
			String viewPath = ViewConstants.VIEW_MARS_MATERIAL_REGION;

			addJoins(viewPath, joins);

			createRule(viewPath, mainPath, otherPaths, fileName);

	}
	
	private void addJoins(String viewPath, List<ViewJoin> joins) {
		client.invokeRemoteService(grid, RemoteServiceType.SERVER, BuildViewService.class, "addJoins", viewPath, joins);
	}

	private void createRule(String viewPath, String mainPath, String[] otherPaths, String fileName) {
		try {
			uploadRule(fileName);
			addMetaData(fileName, viewPath, mainPath, otherPaths);
			if(BooleanUtils.isTrue(fullCreate)){
				fullCreate(viewPath, mainPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void uploadRule(String fileName) throws IOException {
		System.out.println("class :" + this.getClass().getClassLoader().getResource(getRulePath()+fileName));
		System.out.println("file name :" + getRulePath()+fileName);

		Object result = client.invokeRemoteService(grid, RemoteServiceType.SERVERS, BuildViewService.class,
				"uploadDrlFileContent", fileName,
				IOUtils.toByteArray(this.getClass().getClassLoader().getResource(getRulePath()+fileName).openStream()));
//				FileUtils.readFileToByteArray(ResourceUtils.getFile(getRulePath() + fileName)));
		List<String> l = (List<String>) result;
		int sum = l.size();
		for (String o : l) {
			System.out.println(o);
		}
		System.out.println("finished executing uploadDrlFileContent on " + sum + " servers.");
	}

	@SuppressWarnings("unchecked")
	private void addMetaData(String fileName, String view_path, String main_path, String[] otherPaths)
			throws IOException {
		String[] mainFullPath = new String[] { main_path };
		String[] regionFullPaths = (String[]) ArrayUtils.addAll(new String[] { main_path }, otherPaths);
		String[] drlFiles = new String[] { fileName };
		Object result = client.invokeRemoteService(grid, RemoteServiceType.SERVERS, BuildViewService.class,
				"addViewMetaWithDrlFile", view_path, mainFullPath, regionFullPaths, drlFiles);
		List<String> l = (List<String>) result;
		int sum = l.size();
		for (String o : l) {
			System.out.println(o);
		}
		System.out.println("finished executing addViewMeta on " + sum + " servers.");
	}

	@SuppressWarnings("rawtypes")
	private void fullCreate(String viewFullPath, String regionFullPth) {
		System.out.println("calling remote fullCreate ...");
		Object result = client.invokeRemoteService(grid, RemoteServiceType.SERVERS, BuildViewService.class,
				"fullCreate", regionFullPth, viewFullPath, "");
		List l = (List) result;
		int sum = l.size();
		for (Object o : l) {
			if (o instanceof String) {
				String s = (String) o;
				System.out.println(s);
			} else if (o instanceof Exception) {
				((Exception) o).printStackTrace();
			} else {
				System.out.println(o);
			}
		}
		System.out.println("finished executing fullCreate on " + sum + " servers.");
	}

	public void rebuildViewMethod(String viewFullRegion, String condition, String mainRegionPath) {
		IRemoteService<BuildViewService> rs;
		try {
			rs = RemoteServiceManager.getInstance().getRemoteService(BuildViewService.class.newInstance());
			rs.onServers(GridManager.getInstance().getGemFirePool(grid)).rebuildView(viewFullRegion, condition,
					mainRegionPath);

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		System.out.println("finished executing rebuildView on " + mainRegionPath + " region.");
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	public abstract void process();

	// public String getGrid() {
	// return grid;
	// }
	//
	// public void setGrid(String grid) {
	// this.grid = grid;
	// }
}