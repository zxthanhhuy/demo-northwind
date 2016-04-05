package northwind.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateString;

@NodeEntity(label = "Order")
public class Order {

	@GraphId
	private Long id;

	@Property(name = "orderID")
	private String orderID;

	@DateString(value = "dd/MM/yyyy HH:mm:ss.SSS")
	private Date orderDate;
	private String shipName;
	private String shipCountry;
	private String shipRegion;
	private String shipCity;
	private String shipAddress;
	private String shipPostalCode;
	@DateString(value = "dd/MM/yyyy HH:mm:ss.SSS")
	private Date shippedDate;
	@DateString(value = "dd/MM/yyyy HH:mm:ss.SSS")
	private Date requiredDate;
	private String shipVia;
	private String freight;
	private String customerID;
	private String employeeID;

	/**
	 * (Order)-[ORDERS]->(Product)
	 */
	@Relationship(type = "ORDERS", direction = Relationship.OUTGOING)
	private Set<Product> products = new HashSet<>();

	/**
	 * (Order)<-[PURCHASED]-(Customer)
	 */
	@Relationship(type = "PURCHASED", direction = Relationship.INCOMING)
	private Customer customer;

	public Order() {
	}

	public Order(Long id, String orderID, Date orderDate, String shipAddress,
			String shipRegion, String freight, String shipCity,
			String shipCountry, String shipName, Date shippedDate,
			Date requiredDate, String shipPostalCode, String shipVia,
			String customerID, String employeeID) {
		this.id = id;
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.shipAddress = shipAddress;
		this.shipRegion = shipRegion;
		this.freight = freight;
		this.shipCity = shipCity;
		this.shipCountry = shipCountry;
		this.shipName = shipName;
		this.shippedDate = shippedDate;
		this.requiredDate = requiredDate;
		this.shipPostalCode = shipPostalCode;
		this.shipVia = shipVia;
		this.customerID = customerID;
		this.employeeID = employeeID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getShipCountry() {
		return shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

	public String getShipRegion() {
		return shipRegion;
	}

	public void setShipRegion(String shipRegion) {
		this.shipRegion = shipRegion;
	}

	public String getShipCity() {
		return shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getShipPostalCode() {
		return shipPostalCode;
	}

	public void setShipPostalCode(String shipPostalCode) {
		this.shipPostalCode = shipPostalCode;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	public String getShipVia() {
		return shipVia;
	}

	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}