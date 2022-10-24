package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import utils.Connect;

public class Menu {
	private int itemId, itemPrice, stock, quantity;
	private String itemName;
	
	public Menu(int itemId, int itemPrice, int stock, String itemName, int quantity) {
		super();
		this.itemId = itemId;
		this.itemPrice = itemPrice;
		this.stock = stock;
		this.itemName = itemName;
	}
	
	public Menu(int itemId, String itemName, int itemPrice, int stock) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.stock = stock;
	}
	
	public Menu(int itemId, String itemName, int quantity) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
	}
	
	public Menu(int itemId, String itemName) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public static Vector<Menu> getAllMenus () {
		
		Vector<Menu> menus = new Vector<>();
		
		String queryGetMenus = "SELECT * FROM MsMenu WHERE ItemStock > 0";
		ResultSet res = Connect.getConnection().executeQuery(queryGetMenus);
		
		try {
			while (res.next()) {
				menus.add(new Menu(res.getInt("ItemId"), res.getString("ItemName"), res.getInt("ItemPrice"), res.getInt("ItemStock"))); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}
	
	public static void updateItemStock (Vector<Menu> soldMenus) {
		
		String updateItemStockQuery = "UPDATE MsMenu SET ItemStock = ItemStock - ? WHERE  ItemId = ?";
		PreparedStatement preparedStatement = Connect.getConnection().prepareStatement(updateItemStockQuery);
		
		for (Menu m : soldMenus) {
			try {
				preparedStatement.setInt(1, m.getQuantity());
				preparedStatement.setInt(2, m.getItemId());
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
