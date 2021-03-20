package models;

public class Product {
	public String id;
	public String name;
	
	public Product(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String toString() {
		return "[" + id + " - " + name + "]";
	}
}
