package com.jnj.unity.unity_views.deploy;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jnj.adf.grid.view.domain.ViewJoin;
import com.jnj.adf.grid.view.domain.ViewJoin.JoinOp;
import com.jnj.unity.unity_views.UnityBaseViewDeploy;

public class MentorProductLotViewDeploy extends UnityBaseViewDeploy {
	public final static String COMMON_RULE_PATH = "rules/";
	
	@Override
	public void process() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime = new Date();
		// 规则文件路径设置
		setRulePath("rules/");
		// join关系添加
		List<ViewJoin> joins = new ArrayList<>();
		String[] materialOtherPaths = {"/MENTOR/mentor_f4102", "/MENTOR/mentor_f4801"};
		
		joins.add(new ViewJoin("ibitm", "/MENTOR/mentor_f4102", "/MENTOR/mentor_f4108", "iolitm", JoinOp.EQUAL));
		joins.add(new ViewJoin("ibmcu", "/MENTOR/mentor_f4102", "/MENTOR/mentor_f4108", "iomcu", JoinOp.EQUAL));
		joins.add(new ViewJoin("wamcu", "/MENTOR/mentor_f4801", "/MENTOR/mentor_f4108", "iomcu", JoinOp.EQUAL));
		joins.add(new ViewJoin("wadcto", "/MENTOR/mentor_f4801", "/MENTOR/mentor_f4108", "iodcto", JoinOp.EQUAL));
		createProductLotUnitsRule("/MENTOR/mentor_f4108", materialOtherPaths, "UnityProductLotMentor.drl", joins);
		Date endTime = new Date();
		System.out.println("jde mentor startTime : " + sdf.format(startTime) + " endTime : " + sdf.format(endTime)
				+ " totalTime : " + String.valueOf(endTime.getTime() - startTime.getTime()));
				
	}
	
	public static void main(String[] args) {
		MentorProductLotViewDeploy inst = new MentorProductLotViewDeploy();
		inst.connect();
		inst.process();
	}

}
