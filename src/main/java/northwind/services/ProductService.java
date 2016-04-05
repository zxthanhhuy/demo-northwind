package northwind.services;

import java.util.Map;

import northwind.controllers.forms.ProductForm;
import northwind.entities.Product;
import northwind.repositories.CategoryRepository;
import northwind.repositories.ProductRepository;
import northwind.repositories.SupplierRepository;
import northwind.services.utils.GenericCRUDService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends GenericCRUDService<Product, ProductForm> {
  final static Logger logger = LoggerFactory.getLogger(ProductService.class);

  @Autowired 
  ProductRepository  productRepository;

  @Autowired 
  CategoryRepository  categoryRepository;

  @Autowired 
  SupplierRepository  supplierRepository;

  @Override
  public GraphRepository<Product> getRepository() {
    return productRepository;
  }

  @Override
  public Iterable<Map<String, Object>> entityIDs() {
    return null;
  }

  @Override
  public void convertToForm(Product product, ProductForm form) {
    form.setId(product.id.toString());
    form.setProductID(product.getProductID().toString());
    form.setProductName(product.getProductName());
    form.setQuantityPerUnit(product.getQuantityPerUnit());
    form.setUnitPrice(product.getUnitPrice().toString());
    form.setUnitsInStock(product.getUnitsInStock().toString());
    form.setUnitsOnOrder(product.getUnitsOnOrder().toString());
    form.setReorderLevel(product.getReorderLevel().toString());
    form.setDiscontinued(product.getDiscontinued().toString());
    form.setSupplierID(product.getSupplierID().toString());
    form.setCategoryID(product.getCategoryID().toString());
  }

  @Override
  public Product convertToEntity(ProductForm form) {
    Product product = new Product();
    if (StringUtils.isNotEmpty(form.getId())) {
      product.setId(Long.valueOf(form.getId()));
    } else {
      product.setId(null); //new node
    }
    if (StringUtils.isNotEmpty(form.getProductID())) {
      //product.productID = Integer.valueOf(form.getProductID());
    	product.setProductID(form.getProductID());
    } else {
      product.setProductID((maxEntityID() + 1) + ""); //new node
    }
    product.setProductName(form.getProductName());
    product.setQuantityPerUnit(form.getQuantityPerUnit());
    product.setUnitPrice(Double.valueOf(form.getUnitPrice()));
    product.setUnitsInStock(Integer.valueOf(form.getUnitsInStock()));
    product.setUnitsOnOrder(Integer.valueOf(form.getUnitsOnOrder()));
    product.setReorderLevel(Integer.valueOf(form.getReorderLevel()));
    product.setDiscontinued(Boolean.valueOf(form.getDiscontinued()));

    //retrieve supplier
    if (StringUtils.isNotEmpty(form.getSupplierID())) {
      product.setSupplierID(form.getSupplierID());
      product.setSupplier(supplierRepository.findBySupplierID(product.getSupplierID()));
    } else {
      throw new RuntimeException("supplier not found");
    }

    //retrieve category
    if (StringUtils.isNotEmpty(form.getCategoryID())) {
      product.setCategoryID(form.getCategoryID());
      product.setCategory(categoryRepository.findByCategoryID(product.getCategoryID()));
    } else {
      throw new RuntimeException("category not found");
    }

    return product;
  }

  public long countByNameLike(String name) {
    return productRepository.countByNameLike(ServiceUtils.nameLike(name));
  }

  public Iterable<Product> findByNameLike(String name, int page, int size) {
    return productRepository.findByNameLike(ServiceUtils.nameLike(name));
  }

  @Override
  public Integer maxEntityID() {
    return productRepository.maxProductID();
  }

}