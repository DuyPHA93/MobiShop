package model;

public class HomeProductSP {
	private String productTypeCode;
	private String productTypeName;
	private String brandCode;
	private String brandName;
	private int id;
	private String code;
	private String name;
	private Double price;
	private String fileName;

	public HomeProductSP(String productTypeCode, String productTypeName, String brandCode, String brandName, int id,
			String code, String name, Double price, String fileName) {
		this.productTypeCode = productTypeCode;
		this.productTypeName = productTypeName;
		this.brandCode = brandCode;
		this.brandName = brandName;
		this.id = id;
		this.code = code;
		this.name = name;
		this.price = price;
		this.fileName = fileName;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
