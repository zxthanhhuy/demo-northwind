package northwind.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "Customer")
public class Customer {

	@GraphId
	private Long id;

	@Property(name = "customerID")
	private String customerID;

	private String contactName;
	private String contactTitle;
	private String country;
	private String region;
	private String city;
	private String address;
	private String postalCode;
	private String phone;
	private String fax;

	/**
	 * (Customer)-[PURCHASED]->(Order)
	 */
	@Relationship(type = "PURCHASED", direction = Relationship.OUTGOING)
	private Set<Order> orders = new HashSet<>();

	public Customer() {
	}

	public Customer(Long id, String customerID, String contactTitle,
			String contactName, String address, String city, String postalCode,
			String country, String region, String phone, String fax) {
		this.id = id;
		this.customerID = customerID;
		this.contactTitle = contactTitle;
		this.contactName = contactName;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.region = region;
		this.phone = phone;
		this.fax = fax;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}