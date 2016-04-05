package northwind.services;

import java.util.Map;

import northwind.controllers.forms.CustomerForm;
import northwind.entities.Customer;
import northwind.repositories.CustomerRepository;
import northwind.services.utils.GenericCRUDService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends GenericCRUDService<Customer, CustomerForm> {
  final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

  @Autowired 
  CustomerRepository  customerRepository;

  @Override
  public GraphRepository<Customer> getRepository() {
    return customerRepository;
  }

  @Override
  public Iterable<Map<String, Object>> entityIDs() {
    return customerRepository.customerIDs();
  }

  @Override
  public void convertToForm(Customer customer, CustomerForm form) {
    form.setId(customer.getId().toString());
    form.setCustomerID(customer.getCustomerID());
    form.setContactName(customer.getContactName());
    form.setContactTitle(customer.getContactTitle());
    form.setCountry(customer.getCountry());
    form.setRegion(customer.getRegion());
    form.setCity(customer.getCity());
    form.setAddress(customer.getAddress());
    form.setPostalCode(customer.getPostalCode());
    form.setPhone(customer.getPhone());
    form.setFax(customer.getFax());
  }

  @Override
  public Customer convertToEntity(CustomerForm form) {
    Customer customer = new Customer();
    if (StringUtils.isNotEmpty(form.getId())) {
      customer.setId(Long.valueOf(form.getId()));
    } else {
      customer.setId(null); //new node
    }
    customer.setCustomerID(form.getCustomerID());
    customer.setContactName(form.getContactName());
    customer.setContactTitle(ServiceUtils.nvl(form.getContactTitle()));
    customer.setCountry(form.getCountry());
    customer.setRegion(form.getRegion());
    customer.setCity(form.getCity());
    customer.setAddress(form.getAddress());
    customer.setPostalCode(ServiceUtils.nvl(form.getPostalCode()));
    customer.setPhone(form.getPhone());
    customer.setFax(ServiceUtils.nvl(form.getFax()));
    return customer;
  }

  @Override
  public Integer maxEntityID() {
    return null;
  }
}