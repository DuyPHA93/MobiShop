package bean;

import java.util.ArrayList;
import java.util.List;

import model.Product;

public class Cart {
	private int quantity;
	private double totalPrice;
	private List<Product> list;
	
	public Cart() {
		this.quantity = 0;
		this.totalPrice = 0;
		list = new ArrayList<>();
	}
	
	public void addItem(Product item) {
		try {
			if (item != null) {
				this.quantity += item.getQuantity();
				this.totalPrice += (item.getPrice() * item.getQuantity());
				
				for(Product tmp : list) {
					if (tmp.equals(item)) {
						tmp.setQuantity(tmp.getQuantity() + item.getQuantity());
						return;
					}
				}
				
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}
}
