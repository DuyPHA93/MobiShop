package model;

public class CategorySP {
	private String fileName;
	private Integer productTypeId;
	private String productTypeName;
	private Integer brandId;
	private String brandName;

	public CategorySP(String fileName, Integer productTypeId, String productTypeName, Integer brandId, String brandName) {
		this.fileName = fileName;
		this.productTypeId = productTypeId;
		this.productTypeName = productTypeName;
		this.brandId = brandId;
		this.brandName = brandName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
