package northwind.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "Product")
public class Product {

	@GraphId
	public Long id;

	@Property(name = "productID")
	private String productID;

	private String productName;
	private String quantityPerUnit;
	private Double unitPrice;
	private Integer unitsInStock;
	private Integer unitsOnOrder;
	private Integer reorderLevel;
	private Boolean discontinued;
	private String supplierID;
	private String categoryID;

	/**
	 * (Product)<-[ORDERS]-(Order)
	 */
	@Relationship(type = "ORDERS", direction = Relationship.INCOMING)
	private Set<Order> orders = new HashSet<>();

	/**
	 * (Product)<-[SUPPLIES]-(Supplier)
	 */
	@Relationship(type = "SUPPLIES", direction = Relationship.INCOMING)
	private Supplier supplier;

	/**
	 * (Product)-[PART_OF]->(Category)
	 */
	@Relationship(type = "PART_OF", direction = Relationship.OUTGOING)
	private Category category;

	public Product() {
	}

	public Product(Long id, String productID, String productName,
			String supplierID, String categoryID, String quantityPerUnit,
			Double unitPrice, Integer unitsInStock, Integer unitsOnOrder,
			Integer reorderLevel, Boolean discontinued) {
		this.id = id;
		this.productID = productID;
		this.productName = productName;
		this.supplierID = supplierID;
		this.categoryID = categoryID;
		this.quantityPerUnit = quantityPerUnit;
		this.unitPrice = unitPrice;
		this.unitsInStock = unitsInStock;
		this.unitsOnOrder = unitsOnOrder;
		this.reorderLevel = reorderLevel;
		this.discontinued = discontinued;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getQuantityPerUnit() {
		return quantityPerUnit;
	}

	public void setQuantityPerUnit(String quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(Integer unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public Integer getUnitsOnOrder() {
		return unitsOnOrder;
	}

	public void setUnitsOnOrder(Integer unitsOnOrder) {
		this.unitsOnOrder = unitsOnOrder;
	}

	public Integer getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(Integer reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public Boolean getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Boolean discontinued) {
		this.discontinued = discontinued;
	}

	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}