package bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.CategoryDAO;
import model.CategorySP;

public class Category {
	private Integer id;
	private String name;
	private String fileName;
	private List<Category> subMenu;

	public Category() {
		
	}
	
	public Category(Integer id, String name, String fileName) {
		this.id = id;
		this.name = name;
		this.fileName = fileName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<Category> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Category> subMenu) {
		this.subMenu = subMenu;
	}
	
	public List<Category> getListDB() {
		List<Category> list = new ArrayList();
		List<CategorySP> sp = CategoryDAO.getAll();
		Map<Integer, List<CategorySP>> map = sp.stream().collect(Collectors.groupingBy(CategorySP::getProductTypeId));
		
		if (map.size() == 0) return new ArrayList();
		
		for(var entry : map.entrySet()) {
			List<CategorySP> items = entry.getValue();
			
			Category category = new Category(
					items.get(0).getProductTypeId(), 
					items.get(0).getProductTypeName(), 
					items.get(0).getFileName());
			
			List<Category> subList = new ArrayList<Category>();
			for(CategorySP item : items) {
				if (item.getBrandId() > 0) {
					Category sub = new Category(item.getBrandId(), item.getBrandName(), null);
					subList.add(sub);
				}
			}
			category.setSubMenu(subList);
			
			list.add(category);
		}
		
		return list;
	}
}
