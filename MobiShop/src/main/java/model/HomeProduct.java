package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import dao.ProductDAO;

public class HomeProduct {
	private Map<String, String> tabs;
	private Map<String, List<Product>> tabContent;
	
	public HomeProduct() {}

	public HomeProduct(Map<String, String> tabs, Map<String, List<Product>> tabContent) {
		this.tabs = tabs;
		this.tabContent = tabContent;
	}
	
	public List<HomeProduct> getData() {
		List<HomeProduct> result = new ArrayList<HomeProduct>();
		
		List<HomeProductSP> list = ProductDAO.getHomeProductsSP();
		Map<String, List<HomeProductSP>> groupByType = list.stream().collect(Collectors.groupingBy(HomeProductSP::getProductTypeCode));
		
		for(var entry : groupByType.entrySet()) {
			Map<String, String> tab = new HashMap<String, String>();
			Map<String, List<Product>> content = new HashMap<String, List<Product>>();
			List<Product> productByTypeList = new ArrayList<>();
			
			String productTypeCode = entry.getKey();
			tab.put("A_" + productTypeCode, entry.getValue().get(0).getProductTypeName());
			
			for(HomeProductSP k : entry.getValue()) {
				if (productByTypeList.size() < 10)
					productByTypeList.add(new Product(k.getId(), k.getCode(), k.getName(), k.getPrice(), k.getFileName()));
				
				if (!tab.containsKey("B_" + k.getBrandCode())) {
					tab.put("B_" + k.getBrandCode(), k.getBrandName());
				}
				
				if (!content.containsKey("B_" + k.getBrandCode())) {
					List<Product> productByBrandList = new ArrayList<>();
					productByBrandList.add(new Product(k.getId(), k.getCode(), k.getName(), k.getPrice(), k.getFileName()));
					content.put("B_" + k.getBrandCode(), productByBrandList);
				} else if (content.get("B_" + k.getBrandCode()).size() < 10) {
					content.get("B_" + k.getBrandCode()).add(new Product(k.getId(), k.getCode(), k.getName(), k.getPrice(), k.getFileName()));
				}
			}
			
			content.put("A_" + productTypeCode, productByTypeList);
			result.add(new HomeProduct(new TreeMap(tab), new TreeMap(content)));
		}
		
		
		return result;
	}

	public Map<String, String> getTabs() {
		return tabs;
	}

	public void setTabs(Map<String, String> tabs) {
		this.tabs = tabs;
	}

	public Map<String, List<Product>> getTabContent() {
		return tabContent;
	}

	public void setTabContent(Map<String, List<Product>> tabContent) {
		this.tabContent = tabContent;
	}
}
