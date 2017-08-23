package com.jnj.unity.unity_views.command;

import org.apache.commons.lang3.BooleanUtils;



import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.Option;

import com.jnj.adf.client.api.IRemoteService;
import com.jnj.adf.grid.manager.GridManager;
import com.jnj.adf.grid.manager.RemoteServiceManager;
import com.jnj.adf.grid.view.builder.BuildViewService;
import com.jnj.unity.unity_views.UnityBaseViewDeploy;
import com.jnj.unity.unity_views.ViewConstants;
/*import com.jnj.unity.unity_views.deploy.AccountViewDeploy;
import com.jnj.unity.unity_views.deploy.BtBJapanProductLotViewDeploy;
import com.jnj.unity.unity_views.deploy.BtBLatProductLotViewDeploy;
import com.jnj.unity.unity_views.deploy.BtBNaProductLotViewDeploy;
import com.jnj.unity.unity_views.deploy.BwiProductLotViewDeploy;
import com.jnj.unity.unity_views.deploy.CrossroadsProductLotViewDeploy;
import com.jnj.unity.unity_views.deploy.MarsCustomerViewDeploy;
import com.jnj.unity.unity_views.deploy.MarsMaterialViewDeploy;
import com.jnj.unity.unity_views.deploy.MarsProductLotViewDeploy;
import com.jnj.unity.unity_views.deploy.MentorAssetViewDeploy;
import com.jnj.unity.unity_views.deploy.MentorMaterialViewDeploy;

import com.jnj.unity.unity_views.deploy.ProductViewDeploy; 
*/
import com.jnj.unity.unity_views.deploy.MentorProductLotViewDeploy;

/**
 * Deploy view to grid and do full create at first time.
 * 
 * @author sli172
 *
 */
public class ViewDeployCreate extends CmdBase {
	@Option(name = "-toEms", usage = "Specify one source to deploy rule and full create.")
	private String toEmsData;

	@Option(name = "-all", usage = "For all source, depoly all view or clean all view joins.")
	private boolean isAll = false;
	
	@Option(name = "-cleanJoins", usage = "For all source systems, clean view joins.")
	private String cleanJoins;
	
	@Option(name = "-fullCreate", usage = "For all source systems, full create or not default is true.")
	private String fullCreate;

	protected void doProcess() {
		if (BooleanUtils.isTrue(isAll)) {
			System.out.println("LoadAll is true, load all source sytem rule file and full create.");
			if(StringUtils.equals(cleanJoins, "Y")){
				System.out.println("cleanJoins is true, clean all view joins.");
				cleanAllViewJoins();
				System.out.println("clean all view joins success.");
			}else{
				deployAll();
			}
		}else{
			System.out.println("Deploy file for Ems Data:" + toEmsData);
			if(StringUtils.equals(cleanJoins, "Y")){
				System.out.println("cleanJoins is true, clean" +toEmsData +"view joins.");
				cleanOneViewJoins(toEmsData);
				System.out.println("clean "+toEmsData+" view joins success.");
			}else{
				deployOne(toEmsData);
			}
		}
	}

	private void cleanOneViewJoins(String toEmsData) {
		IRemoteService<BuildViewService> rs;
		try {
			rs = RemoteServiceManager.getInstance().getRemoteService(BuildViewService.class.newInstance());
//			switch (toEmsData) {
//			case "MarsCustomerAndEmailView":
//				rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_CUSTOMER_REGION);
//				rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_CUSTOMER_EMAIL_REGION);
//				break;
//			case "MentorMaterialView":
//				rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_MATERIAL_REGION);
//				break;
//			case "MarsMaterialView":
//				rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_MARS_MATERIAL_REGION);
//				break;
//			case "ProductView":
//				rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_PRODUCT_REGION);
//				break;
//			case "AccountView":
//				rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_ACCOUNT_REGION);
//				break;
//			case "CrossroadsProductLotView":
//			case "MentorProductLotView":
//			case "MarsProductLotView":
//			case "BwiProductlotView":
//			case "ProductlotNaView":
//			case "ProductlotJapanView":
//			case "ProductlotLatamView":
//				rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_PRODUCT_LOT_REGION);
//				break;
//			case "MentorAssetView":
//				rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_ASSET_ALL_REGION);
//				break;
//			default:
//				break;
//			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void deployAll() {
		deployOne("MarsCustomerAndEmailView");
		deployOne("MentorMaterialView");
		deployOne("MarsMaterialView");
		deployOne("ProductView");
		deployOne("AccountView");
		deployOne("CrossroadsProductLotView");
		deployOne("MentorProductLotView");
		deployOne("MentorAssetView");
		deployOne("MarsProductLotView");
		deployOne("ProductlotNaView");
		deployOne("ProductlotJapanView");
		deployOne("ProductlotLatamView");
		deployOne("BwiProductlotView");
	}
	
	private void deployOne(String toEadsData) {
//		if("Y".equals(toEadsData)){
//			MarsCustomerViewDeploy view4 = new MarsCustomerViewDeploy();
//			call(view4);
//			MentorMaterialViewDeploy view7 = new MentorMaterialViewDeploy();
//			call(view7);
//		} else {
//			ProductViewDeploy view5 = new ProductViewDeploy();
//			call(view5);
//			AccountViewDeploy view6 = new AccountViewDeploy();
//			call(view6);
//			CrossroadsProductLotViewDeploy view1 = new CrossroadsProductLotViewDeploy();
//			call(view1);
//			MentorProductLotViewDeploy view2 = new MentorProductLotViewDeploy();
//			call(view2);
//			MentorAssetViewDeploy view3 = new MentorAssetViewDeploy();
//			call(view3);
//		}
		switch (toEadsData) {
		/*
		case "MarsCustomerAndEmailView":
			MarsCustomerViewDeploy view4 = new MarsCustomerViewDeploy();
			call(view4);
			break;
		case "MentorMaterialView":
			MentorMaterialViewDeploy view7 = new MentorMaterialViewDeploy();
			call(view7);
			break;
		case "MarsMaterialView":
			MarsMaterialViewDeploy view8 = new MarsMaterialViewDeploy();
			call(view8);
			break;
		case "ProductView":
			ProductViewDeploy view5 = new ProductViewDeploy();
			call(view5);
			break;
		case "AccountView":
			AccountViewDeploy view6 = new AccountViewDeploy();
			call(view6);
			break;
		case "CrossroadsProductLotView":
			CrossroadsProductLotViewDeploy view1 = new CrossroadsProductLotViewDeploy();
			call(view1);
			break;
			*/
		case "MentorProductLotView":
			MentorProductLotViewDeploy view2 = new MentorProductLotViewDeploy();
			call(view2);
			break;
			/*
		case "MentorAssetView":
			MentorAssetViewDeploy view3 = new MentorAssetViewDeploy();
			call(view3);
			break;
		case "MarsProductLotView":
			MarsProductLotViewDeploy view9 = new MarsProductLotViewDeploy();
			call(view9);
			break;
		case "ProductlotNaView":
			BtBNaProductLotViewDeploy view10 = new BtBNaProductLotViewDeploy();
			call(view10);
			break;
		case "ProductlotJapanView":
			BtBJapanProductLotViewDeploy view11 = new BtBJapanProductLotViewDeploy();
			call(view11);
			break;
		case "ProductlotLatamView":
			BtBLatProductLotViewDeploy view12 = new BtBLatProductLotViewDeploy();
			call(view12);
			break;
		case "BwiProductlotView":
			BwiProductLotViewDeploy view13 = new BwiProductLotViewDeploy();
			call(view13);
			break;
			*/
		default:
			break;
		}
	}

	private void call(UnityBaseViewDeploy inst) {
		inst.setAdfClient(client);
		if(StringUtils.equals(fullCreate, "N")){
			inst.setFullCreate(false);
		}
		inst.process();
	}
	
	private void cleanAllViewJoins() {
		IRemoteService<BuildViewService> rs;
		try {
			rs = RemoteServiceManager.getInstance().getRemoteService(BuildViewService.class.newInstance());
//			rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_ASSET_ALL_REGION);
//			rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_PRODUCT_REGION);
//			rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_ACCOUNT_REGION);
//			rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_PRODUCT_LOT_REGION);
//			rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_CUSTOMER_REGION);
//			rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_CUSTOMER_EMAIL_REGION);
//			rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_MATERIAL_REGION);
//			rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins(ViewConstants.VIEW_MARS_MATERIAL_REGION);
//			rs.onServer(GridManager.getInstance().getGemFirePool(grid)).clearJoins("/unity/drltest");

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		ViewDeployCreate inst = new ViewDeployCreate();
		inst.process(args);
	}
}
