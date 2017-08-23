/*                                                                         
 * Copyright 2010-2013 the original author or authors.                     
 *                                                                         
 * Licensed under the Apache License, Version 2.0 (the "License");         
 * you may not use this file except in compliance with the License.        
 * You may obtain a copy of the License at                                 
 *                                                                         
 *      http://www.apache.org/licenses/LICENSE-2.0                         
 *                                                                         
 * Unless required by applicable law or agreed to in writing, software     
 * distributed under the License is distributed on an "AS IS" BASIS,       
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and     
 * limitations under the License.                                          
 */
package com.jnj.unity.unity_views.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.gemstone.gemfire.cache.client.Pool;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.TypeMismatchException;
import com.gemstone.gemfire.cache.query.internal.ResultsBag;
import com.gemstone.gemfire.pdx.PdxInstance;
import com.jnj.adf.client.api.ADFService;
import com.jnj.adf.client.api.IBiz;
import com.jnj.adf.client.api.IRemoteService;
import com.jnj.adf.client.api.JsonObject;
import com.jnj.adf.client.api.PageResults;
import com.jnj.adf.config.annotations.EnableADF;
import com.jnj.adf.grid.common.PdxUtils;
import com.jnj.adf.grid.manager.GridManager;
import com.jnj.adf.grid.manager.MultipleUserSupporter;
import com.jnj.adf.grid.manager.RegionHelper;
import com.jnj.adf.grid.support.context.ADFContext;
import com.jnj.adf.grid.utils.LogUtil;
import com.jnj.adf.grid.utils.SpringBeanUtils;

/**
 * @author gtang
 *
 */
public class AdfClient {

	public enum RemoteServiceType {
		SERVER, SERVERS, REGION, MEMBER
	}

	private ADFService adfService;

	@EnableADF
	static class UniytViewConfig {

	}
	
	@SuppressWarnings("resource")
	public AdfClient() {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("adf.xml");
//		this.adfService = context.getBean(ADFService.class);
		SpringBeanUtils.initContext(UniytViewConfig.class);
		this.adfService = ADFContext.getBean(ADFService.class);
	}

	public AdfClient(ADFService adfService) {
		this.adfService = adfService;
	}

	public void connect(String groupName) {
		adfService.connect(null, groupName);
//		adfService.login("admin", "123456");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void query(String path) {
		PageResults<HashMap> pr = adfService.onPath(path).queryPage("", 1, 10, HashMap.class);
		System.out.println(pr.getTotalCount());
		List<Entry<String, HashMap>> entries = pr.getEntries();
		if (entries != null && !entries.isEmpty()) {
			HashMap<String, Object> m = null;
			String key = null;
			for (Entry<String, HashMap> entry : entries) {
				key = entry.getKey();
				m = (HashMap<String, Object>) entry.getValue();
				System.out.println(key);
				System.out.println(m);
			}
		}
	}
	
	public static Object oqlQueryForObject(String path,String sql,Object[] params){
		List<Object> l = oqlQueryForList(path, sql, params);
		return l != null && l.size() > 0 ? l.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> oqlQueryForList(String path,String sql,Object[] params){
		ResultsBag rb = oqlQuery(path, sql, params);
		return rb.asList();
	}
	
	public static ResultsBag oqlQuery(String path,String sql,Object[] params){
		String gridId = RegionHelper.getGridIdByRegionPath(path);
		QueryService queryService = MultipleUserSupporter.getRegionService(gridId).getQueryService();
		Query query = queryService.newQuery(sql);
		if(params == null || params.length == 0)
			try {
				return (ResultsBag)query.execute();
			} catch (FunctionDomainException | TypeMismatchException | NameResolutionException
					| QueryInvocationTargetException e) {
				LogUtil.getCoreLog().error("region query error:",e);
				return null;
			}
		try {
			return (ResultsBag)query.execute(params);
		} catch (FunctionDomainException | TypeMismatchException | NameResolutionException
				| QueryInvocationTargetException e) {
			LogUtil.getCoreLog().error("region query error:",e);
			return null;
		}
	}

	public void put(String path, String key, Object value) {
		adfService.onPath(path).put(key, value);
	}

	public String get(String path, String key) {
		return adfService.onPath(path).get(key);
	}

	public void delete(String path, String key) {
		adfService.onPath(path).remove(key);
	}
	
	public void removeAll(String path) {
		adfService.onPath(path).removeAll();
	}
	
	public void temporalPut(String path, String key,Object value){
		adfService.onPath(path).biTemporal().put(key, value);
	}
	
	@SuppressWarnings("rawtypes")
	public <T> Object invokeRemoteService(String gridId,RemoteServiceType type, Class<T> cls,
	String method,ResultCollector<?, ?> collector,
			Object... params) {
		if (type == null || cls == null ||  StringUtils.isEmpty(method))
			return null;
		IRemoteService<T> irs = IBiz.remote(cls);
		if(collector != null){
			irs.withResultCollector(collector);
		}
		T t = null;
//		Pool pool = GridManager.getInstance().getGemFirePool();
		Pool pool = GridManager.getInstance().getGemFirePool(gridId);
		switch (type) {
		case REGION:
			// irs.onRegion(path, filters)
		case SERVER:
			t = irs.onServer(pool);
		case SERVERS:
			t = irs.onServers(pool);
		default:
			t = irs.onServers(pool);
		}

		Class[] types = new Class[params.length];
		int i = 0;
		for (Object param : params) {
//			System.out.println(param.getClass());
			if(param.getClass().equals(ArrayList.class)){
				types[i++] = List.class;
			}else{
				types[i++] = param.getClass();
			}
		}
		try {
			cls.getMethod(method, types).invoke(t, params);
//			for (Object o : irs.iterableResults()) {
//				System.out.println(o);
//			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if(collector != null)
			return collector.getResult();
		return irs.iterableResults();
	}


	public <T> Object invokeRemoteService(String gridId,RemoteServiceType type, Class<T> cls, 
			String method,
			Object... params) {
		return invokeRemoteService(gridId,type, cls, method,null, params);
	}

	public void excuteFunction(RemoteServiceType type, String funcName) {
		Pool pool = GridManager.getInstance().getGemFirePool();
		Execution execution = FunctionService.onServers(pool);
		switch (type) {
		case REGION:
			// irs.onRegion(path, filters)
		case SERVER:
			execution = FunctionService.onServer(pool);
		case SERVERS:
			execution = FunctionService.onServers(pool);
		default:
			execution = FunctionService.onServers(pool);
		}
		ResultCollector<?, ?> rc = execution.execute(funcName);
		System.out.println(rc.getResult());
	}
	
	public ADFService getAdfService() {
		return adfService;
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		AdfClient ac = new AdfClient();
		ac.connect("adf-it");
		JsonObject js = JsonObject.create();
		js.append("aaa", "AAA 1Ac");
//		for(int i = 100;i < 200;i++){
//			ac.put("/synthes/ProductMaster", UUID.randomUUID().toString(), js.toJson());
//		}
//		RegionHelper.createProxyRegion("adf-it-grid", "/_RTM/_gfs_customer.files");
//		Region<?, ?> fileRegion = RegionHelper.getRegion("/_RTM/_gfs_customer.files");
//		
//		RegionHelper.createProxyRegion("adf-it-grid", "/_RTM/_gfs_customer.chunks");
//		Region<?, ?> chunkRegion = RegionHelper.getRegion("/_RTM/_gfs_customer.chunks");
//		System.out.println(fileRegion.keySetOnServer());
//		System.out.println(chunkRegion.keySetOnServer().size());
//		fileRegion.keySetOnServer().forEach(k -> {
//			System.out.println(fileRegion.get(k));
//		});
//		ac.put("gfs/order", "aaa", js.toJson());
//		ac.delete("/gfs/ce", "bbb");
//		ac.get("/gfs/ce", "aaa");
//		ac.temporalPut("/gfs/prod", "bbb", js.toJson());
		
//		ac.query("/synthes/ProductMaster");
		
//		System.out.println(pool);
//		System.out.println(ac.get("/gfs/order", "aaa"));
		Object o = ac.oqlQueryForObject("/gfs/order","SELECT aaa FROM /gfs/order WHERE aaa=$1", new Object[]{"AAA 1Ac"});
		if(o instanceof PdxInstance){
			System.out.println(PdxUtils.toJson((PdxInstance)o));;
		}else{
			System.out.println(o);
		}
		
	}
}
