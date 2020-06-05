package com.hxq.action.stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hxq.service.ContractProductService;
import com.hxq.web.action.BaseAction;
import com.sun.mail.iap.Response;

@Namespace(value="/stat")
public class StatChartAction extends BaseAction {
	
	@Autowired
	private ContractProductService contractProductService;
	
	@Action(value="statChartAction_factorysale",results={@Result(name="toFactorySale",location="/WEB-INF/pages/stat/factorySale.jsp")})
	public String toFactorySale() throws Exception {
		// TODO Auto-generated method stub
		return  "toFactorySale";
	}
	
	@Action(value="statChartAction_productsale",results={@Result(name="toProductSale",location="/WEB-INF/pages/stat/productSale.jsp")})
	public String toProductSale() throws Exception {
		// TODO Auto-generated method stub
		return  "toProductSale";
	}
	
	@Action(value="statChartAction_onlineinfo",results={@Result(name="toOnlineInfo",location="/WEB-INF/pages/stat/onlineinfo.jsp")})
	public String toOnlineInfo() throws Exception {
		// TODO Auto-generated method stub
		return  "toOnlineInfo";
	}
	
	@Action(value="statChartAction_getFactorySale")
	public String getFactorySale() throws IOException{
		// TODO Auto-generated method stub
		//[{name: "待收本金",y: 0.0}, {name: "可用代金券",y: 0.0}, {name: "待收收益",y: 42.9}, {name: "可用余额",y: 57.1}]
		List jsonData=new ArrayList<>();
		List<Object[]> findFactrySale = contractProductService.findFactrySale();
		for (int i = 0; i < findFactrySale.size(); i++) {
			HashMap maps=new HashMap<>();
			maps.put("name", findFactrySale.get(i)[0].toString());
			maps.put("y",Double.valueOf(findFactrySale.get(i)[1].toString()));
			jsonData.add(maps);
		}
		String jsonString = JSON.toJSONString(jsonData);
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
		return NONE;
	}
	
	@Action(value="statChartAction_getProductSale")
	public String getProductSale() throws IOException{
		// TODO Auto-generated method stub
		List x=new ArrayList<>();
		List data=new ArrayList<>();
		List<Object[]> findProdcutSale = contractProductService.finProductSale();
		for (int i = 0; i < findProdcutSale.size(); i++) {
			x.add(findProdcutSale.get(i)[0].toString());
			data.add(Double.valueOf(findProdcutSale.get(i)[1].toString()));
		}
		HashMap map=new HashMap<>();
		map.put("x", x);
		map.put("data", data);
		String jsonString = JSON.toJSONString(map);
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
		return NONE;
	}
	
	
	@Action(value="statChartAction_getOnlineInfo")
	public String getOnlineInfo() throws IOException{
		// TODO Auto-generated method stub
		List time=new ArrayList<>();
		List count=new ArrayList<>();
		List<Object[]> findProdcutSale = contractProductService.findOnlineInfo();
		for (int i = 0; i < findProdcutSale.size(); i++) {
			time.add(findProdcutSale.get(i)[0].toString()+"时");
			count.add(Double.valueOf(findProdcutSale.get(i)[1].toString()));
		}
		HashMap map=new HashMap<>();
		map.put("time", time);
		map.put("count", count);
		String jsonString = JSON.toJSONString(map);
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonString);
		return NONE;
	}
}

