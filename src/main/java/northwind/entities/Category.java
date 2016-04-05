package northwind.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "Category")
public class Category {

  @GraphId
  private Long id;

  @Property(name = "categoryID")
  private String categoryID;
  
  private String categoryName;
  private String description;
  private String picture;

  /**
   * (Category)<-[PART_OF]-(Product)
   */
  @Relationship(type = "PART_OF", direction = Relationship.INCOMING)
  private Set<Product> products = new HashSet<>();

  public Category()  {}

  public Category(Long id, String categoryID, String categoryName, String description,
      String picture) {
    this.id = id;
    this.categoryID = categoryID;
    this.categoryName = categoryName;
    this.description = description;
    this.picture = picture;
  }

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCategoryID() {
		return categoryID;
	}
	
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}