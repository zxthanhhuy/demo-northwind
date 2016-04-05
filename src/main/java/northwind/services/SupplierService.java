package northwind.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import northwind.controllers.forms.SupplierForm;
import northwind.entities.Product;
import northwind.entities.Supplier;
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
public class SupplierService extends GenericCRUDService<Supplier, SupplierForm> {
  final static Logger logger = LoggerFactory.getLogger(SupplierService.class);

  @Autowired 
  SupplierRepository  supplierRepository;

  @Autowired 
  ProductRepository  productRepository;

  @Override
  public GraphRepository<Supplier> getRepository() {
    return supplierRepository;
  }

  @Override
  public Iterable<Map<String, Object>> entityIDs() {
    return supplierRepository.supplierIDs();
  }

  @Override
  public void convertToForm(Supplier supplier, SupplierForm form) {
    form.setId(supplier.id.toString());
    form.setSupplierID(supplier.supplierID.toString());
    form.setCompanyName(supplier.companyName);
    form.setContactName(supplier.contactName);
    form.setContactTitle(supplier.contactTitle);
    form.setHomePage(supplier.homePage);
    form.setCountry(supplier.country);
    form.setRegion(supplier.region);
    form.setCity(supplier.city);
    form.setAddress(supplier.address);
    form.setPostalCode(supplier.postalCode);
    form.setPhone(supplier.phone);
    form.setFax(supplier.fax);
    
    if (supplier.products != null) {
      supplier.products.stream().forEach(p ->{
        form.getProducts().put(Integer.valueOf(p.getProductID()), p.getProductName());
      });
    }

  }

  @Override
  public Supplier convertToEntity(SupplierForm form) {
    Supplier supplier = new Supplier();
    if (StringUtils.isNotEmpty(form.getId())) {
      supplier.id = Long.valueOf(form.getId());
    } else {
      supplier.id = null; //new node
    }
    if (StringUtils.isNotEmpty(form.getSupplierID())) {
      supplier.supplierID = form.getSupplierID();
    } else {
      supplier.supplierID = (maxEntityID() + 1) + ""; //new node
    }
    supplier.companyName = form.getCompanyName();
    supplier.contactName = form.getContactName();
    supplier.contactTitle = form.getContactTitle();
    supplier.homePage = ServiceUtils.nvl(form.getHomePage());
    supplier.country = form.getCountry();
    supplier.region = form.getRegion();
    supplier.city = form.getCity();
    supplier.address = form.getAddress();
    supplier.postalCode = ServiceUtils.nvl(form.getPostalCode());
    supplier.phone = form.getPhone();
    supplier.fax = ServiceUtils.nvl(form.getFax());

    //retrieve products
    if (form.getProducts() != null) {
      Set<Product> p = new HashSet<>();
      form.getProducts().forEach((key,value)->{
        p.add(productRepository.findOne(Long.valueOf(key), 0));
      });
      supplier.products = p;
    } else {
      supplier.products = null;
    }

    return supplier;
  }

  @Override
  public Integer maxEntityID() {
    return supplierRepository.maxSupplierID();
  }

}