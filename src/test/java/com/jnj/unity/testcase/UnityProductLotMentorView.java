package com.jnj.unity.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.gemstone.gemfire.internal.cache.FilterProfile.interestType;
import com.jnj.adf.client.api.JsonObject;
import com.jnj.adf.client.api.PageResults;

import domain.ProductLot;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnityProductLotMentorView extends UnityViewTestCaseBase {
	private static final String REGION_PATH_F4108 = "/MENTOR/mentor_f4108";
	private static final String REGION_PATH_F4102 = "/MENTOR/mentor_f4102";
	private static final String REGION_PATH_F4801 = "/MENTOR/mentor_f4801";
	private static final String VIEW_PAHT = "/UNITY/mentor_productlot";
	// key field
	private static final String VIEW_F4108_COL_IOITM = "ioitm";
	private static final String VIEW_F4108_COL_IOLOTN = "iolotn";
	private static final String VIEW_F4108_COL_IOMCU = "iomcu";
	// field
	private static final String VIEW_F4108_COL_IOLITM = "iolitm";
	private static final String VIEW_F4108_COL_IOUPMJ = "ioupmj";
	private static final String VIEW_F4108_COL_IODOCO = "iodoco";
	private static final String VIEW_F4108_COL_IOMMEJ= "iommej";
	// key field
	private static final String VIEW_F4102_COL_IBITM = "ibitm";
	private static final String VIEW_F4102_COL_IBMCU = "ibmcu";
	private static final String VIEW_F4102_COL_IBGLPT = "ibglpt";
	// field
	private static final String VIEW_F4102_COL_IBUPMJ = "ibupmj";
	// key field
	private static final String VIEW_F4801_COL_WAMCU = "wamcu";
	private static final String VIEW_F4801_COL_WALOTN = "walotn";
	private static final String VIEW_F4801_COL_WADOCO = "wadoco";
	// field
	private static final String VIEW_F4801_COL_wastrx = "wastrx";
	private static final String VIEW_F4801_COL_wasoqs = "wasoqs";
	private static final String VIEW_F4801_COL_wauom = "wauom";
	private static final String VIEW_F4801_COL_WAUPMJ = "waupmj";

	/*
	private static final String[] keys = {VIEW_F4108_COL_IOITM,VIEW_F4108_COL_IOLOTN,VIEW_F4108_COL_IOMCU};
	
	private Map<String,Object> regionMap4108 = new HashMap<String,Object>();
	private Map<String,Object> regionMap4102 = new HashMap<String,Object>();
	private Map<String,Object> regionMap4801 = new HashMap<String,Object>();

	@Override
	protected void clearData() {
		adfService.onPath(VIEW_PAHT).removeAll();
		adfService.onPath(REGION_PATH_F4108).removeAll();
		adfService.onPath(REGION_PATH_F4102).removeAll();
		adfService.onPath(REGION_PATH_F4801).removeAll();
	}

	@Override
	protected void initData() {
		prepareAccountInBoundForCase1();
	}

	
	private void prepareAccountInBoundForCase1() {
		
		String key1 = JsonObject.create()
				.append(VIEW_F4108_COL_IOITM,"129261.0")
				.append(VIEW_F4108_COL_IOLOTN,"5531946")
				.append(VIEW_F4108_COL_IOMCU,"LN01").toJson();
		String value1 = JsonObject.append(key1)
				.append(VIEW_F4108_COL_IOLITM,"354-6001")
				.append(VIEW_F4108_COL_IOUPMJ,"108102")
				.append(VIEW_F4108_COL_IODOCO, "12")//"" NULL  
				.append(VIEW_F4108_COL_IOMMEJ,"108102")
				.toJson();
		// 4108 and 4102 data,4801 has no relation data
		String key2 = JsonObject.create()
				.append(VIEW_F4108_COL_IOITM,"129262.0")
				.append(VIEW_F4108_COL_IOLOTN,"5531946")
				.append(VIEW_F4108_COL_IOMCU,"TX01").toJson();
		String value2 = JsonObject.append(key2)
				.append(VIEW_F4108_COL_IOLITM,"354-6001")
				.append(VIEW_F4108_COL_IOUPMJ,"108102")
				.append(VIEW_F4108_COL_IODOCO, "12")//"" NULL  
				.append(VIEW_F4108_COL_IOMMEJ,"108102")
				.toJson();
		// 4108 data, 4102 and 4801 have no relation data
		String key3 = JsonObject.create()
				.append(VIEW_F4108_COL_IOITM,"129261.0")
				.append(VIEW_F4108_COL_IOLOTN,"5531946")
				.append(VIEW_F4108_COL_IOMCU,"TX01").toJson();
		String value3 = JsonObject.append(key3)
				.append(VIEW_F4108_COL_IOLITM,"354-6001")
				.append(VIEW_F4108_COL_IOUPMJ,"108102")
				.append(VIEW_F4108_COL_IODOCO, "12")//"" NULL  
				.append(VIEW_F4108_COL_IOMMEJ,"108102")
				.toJson();
		String key4 = JsonObject.create()
				.append(VIEW_F4108_COL_IOITM,"129261.0")
				.append(VIEW_F4108_COL_IOLOTN,"5531946")
				.append(VIEW_F4108_COL_IOMCU,"LN01TX01").toJson();
		String value4 = JsonObject.append(key4)
				.append(VIEW_F4108_COL_IOLITM,"354-6001")
				.append(VIEW_F4108_COL_IOUPMJ,"108102")
				.append(VIEW_F4108_COL_IODOCO, "12")//"" NULL  
				.append(VIEW_F4108_COL_IOMMEJ,"108102")
				.toJson();
		regionMap4108.put(key1, value1);
		regionMap4108.put(key2, value2);
		regionMap4108.put(key3, value3);
		regionMap4108.put(key4, value4);
		
		// 4102 data
		String key101 = JsonObject.create()
				.append(VIEW_F4102_COL_IBITM,"129261.0")
				.append(VIEW_F4102_COL_IBMCU,"LN01")
				.append(VIEW_F4102_COL_IBGLPT,"FG01").toJson();
		String value101 = JsonObject.append(key101)
				.append(VIEW_F4102_COL_IBUPMJ,"108103")
				.toJson();
		// 4801 have no relation data
		String key102 = JsonObject.create()
				.append(VIEW_F4102_COL_IBITM,"129262.0")
				.append(VIEW_F4102_COL_IBMCU,"LN01")
				.append(VIEW_F4102_COL_IBGLPT,"FG01").toJson();
		String value102 = JsonObject.append(key102)
				.append(VIEW_F4102_COL_IBUPMJ,"108103")
				.toJson();
		
		regionMap4102.put(key101, value101);
		regionMap4102.put(key102, value102);
		
		String key201 = JsonObject.create()
				.append(VIEW_F4801_COL_WADOCO,"12")
				.append(VIEW_F4801_COL_WALOTN,"5531946")
				.append(VIEW_F4801_COL_WAMCU,"LN01").toJson();
		String value201 = JsonObject.append(key201)
				.append(VIEW_F4801_COL_wastrx,"104035")
				.append(VIEW_F4801_COL_wasoqs,"470000")
				.append(VIEW_F4801_COL_wauom,"EA")
				.append(VIEW_F4801_COL_WAUPMJ,"108104")
				.toJson();	
		
		regionMap4801.put(key201, value201);
		
		adfService.onPath(REGION_PATH_F4801).putAll(regionMap4801);	
		adfService.onPath(REGION_PATH_F4102).putAll(regionMap4102);	
		adfService.onPath(REGION_PATH_F4108).putAll(regionMap4108);	
	}
	*/
	/*
	@Test
	
	public void test1PutAll() {
		System.out.println("start");
		List<String> viewResult = adfService.onPath(VIEW_PAHT).findAll();
		assertNotNull(viewResult);
		// compare count
		assertEquals(1, viewResult.size());
		System.out.print(viewResult.get(0));
		// compare value
		JsonObject viewJsObj = JsonObject.append(viewResult.get(0));
		String key = viewJsObj.getValue(REGION_PATH_F4108).toString();
		JsonObject f4108JsObj = JsonObject.append(regionMap4108.get(key).toString());
		// externalID = f4801.walotn.trim() +"_"＋f4108.ioitm＋"_"＋"Mentor_JDE Mentor"
		String externalID = "5531946" + "_" + f4108JsObj.getValue(VIEW_F4108_COL_IOITM)+"_"+"Mentor_JDE Mentor";
		//assertEquals(externalID, viewJsObj.getValue("ExternalID"));
		System.out.println("Unity_DMI_External_Id_c_JDE_Mentor:"+viewJsObj.getValue("Unity_DMI_External_Id_c_JDE_Mentor"));
		// changeDate=Max(f4108.ioupmj,f4102.ibupmj,f4801.waupmj) = 108104		
		String date = "2008-04-13";
		// dev server TimeZone:America/New_York
		TimeZone timeZoneNY = TimeZone.getTimeZone("America/New_York");		
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		outputFormat.setTimeZone(timeZoneNY);
		
		Calendar cal  = new GregorianCalendar();
		cal.setTimeInMillis(Long.parseLong(viewJsObj.getValue("changeDate")));
		String targetDate = outputFormat.format(cal.getTime());
		assertEquals(date, targetDate);
		// MedConnect__Quantity_Attached_UOM__c=f4801.wauom
		assertEquals("EA", viewJsObj.getValue("MedConnect__Quantity_Attached_UOM__c"));
		// MedConnect__Lot_Quantity_Affected__c=f4801.wasoqs
		assertEquals("470000", viewJsObj.getValue("MedConnect__Lot_Quantity_Affected__c"));
		// Unity_Manufactured_Date__c=f4801.wastrx(*2) 104035
		assertEquals("20040204", viewJsObj.getValue("Unity_Manufactured_Date__c"));		
		// MedConnect__Lot_Number__c=f4801.walotn
		assertEquals("5531946", viewJsObj.getValue("MedConnect__Lot_Number__c"));
		// MedConnect__Manufacturing_Site_Name__c=f4102.ibmcu
		assertEquals("LN01", viewJsObj.getValue("MedConnect__Manufacturing_Site_Name__c"));
		// MedConnect__Type__c=f4102.ibglpt
		assertEquals("FG01", viewJsObj.getValue("MedConnect__Type__c"));
		// sourceSystem="JDE Mentor"
		assertEquals("JDE Mentor", viewJsObj.getValue("sourceSystem"));
		// opco="Mentor"
		assertEquals("Mentor", viewJsObj.getValue("opco"));
		// LastModifiedDate=System.Date
		cal  = new GregorianCalendar();
		assertEquals(outputFormat.format(cal.getTime()), viewJsObj.getValue("LastModifiedDate"));
		// Unity_Value__c=nulls
		assertEquals("null",viewJsObj.getValue("Unity_Value__c"));
		// Unity_Attribute__c=null
		assertEquals("null", viewJsObj.getValue("Unity_Attribute__c"));
		// Unity_Product_Mod_Code__c=null
		assertEquals("null", viewJsObj.getValue("Unity_Product_Mod_Code__c"));
		// Unity_Product_SKU_Number__c=f4108.iolitm + "_MTR"
		assertEquals(f4108JsObj.getValue(VIEW_F4108_COL_IOLITM)+"_MTR", viewJsObj.getValue("Unity_Product_SKU_Number__c"));
		// Unity_Expiration__c=f4108.iommej(*2) 108102=20080411
		assertEquals("20080411", viewJsObj.getValue("Unity_Expiration__c"));
		
		System.out.println("end");
	}
	*/
	
	@Test
	public void testCheckMD(){
		checkDate("20040201", "20040210");
	}
	public void checkDate(String minDate,String maxDate){
		PageResults<String> viewResult = adfService.onPath(VIEW_PAHT).queryPage("", 1, 1000);
		List<String>list =viewResult.getResults();
		int i=0;
		System.out.println("VIEW总数据数:"+viewResult.getTotalCount());
		//System.out.println("ViewProduct***************"+ProductView);
		//System.out.print("viewResult:"+viewResult.get(0));
		System.out.println("*****************************************************************");
		System.out.println("Unity_Manufactured_Date__c"+"       from     "+minDate+"   to    "+maxDate+"\n");

		for(String view:list ){
			JsonObject viewJsObj = JsonObject.append(view);
			String manufacturedDate=viewJsObj.getValue("Unity_Manufactured_Date__c");
			int MD=Integer.parseInt(manufacturedDate);
			int minMD=Integer.parseInt(minDate);
			int maxMD=Integer.parseInt(maxDate);
			if(MD>=minMD&&MD<=maxMD){
			    i++;
			    System.out.println("#KEY:       "+viewJsObj.getValue("Unity_DMI_External_Id_c_JDE_Mentor")+"           Unity_Manufactured_Date__c:"+viewJsObj.getValue("Unity_Manufactured_Date__c"));
			
			}
		}
	    System.out.println("检索到总条数："+i);
		System.out.println("*****************************************************************");

		/*
		JsonObject viewJsObj = JsonObject.append(viewResult.get(0));
		String external=viewJsObj.getValue("LastModifiedDate");
	    System.out.println("***********************LastModifiedDate:"+external);
		*/
		
		
	}
	
	
	
}