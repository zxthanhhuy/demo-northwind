package northwind.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import northwind.controllers.forms.OrderForm;
import northwind.entities.Order;
import northwind.entities.Product;
import northwind.repositories.CustomerRepository;
import northwind.repositories.OrderRepository;
import northwind.repositories.ProductRepository;
import northwind.services.utils.GenericCRUDService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends GenericCRUDService<Order, OrderForm> {
  final static Logger logger = LoggerFactory.getLogger(OrderService.class);

  @Autowired 
  OrderRepository  orderRepository;

  @Autowired 
  CustomerRepository  customerRepository;

  @Autowired 
  ProductRepository  productRepository;

  @Override
  public GraphRepository<Order> getRepository() {
    return orderRepository;
  }

  @Override
  public Iterable<Map<String, Object>> entityIDs() {
    return null;
  }

  @Override
  public void convertToForm(Order order, OrderForm form) {
    form.setId(order.getId().toString());
    form.setOrderID(order.getOrderID().toString());
    form.setOrderDate(order.getOrderDate());
    form.setShipName(order.getShipName());
    form.setShipCountry(order.getShipCountry());
    form.setShipRegion(order.getShipRegion());
    form.setShipCity(order.getShipCity());
    form.setShipAddress(order.getShipAddress());
    form.setShipPostalCode(order.getShipPostalCode());
    form.setShippedDate(order.getShippedDate());
    form.setRequiredDate(order.getRequiredDate());
    form.setShipVia(order.getShipVia());
    form.setFreight(order.getFreight());
    form.setEmployeeID(ServiceUtils.nvl(order.getEmployeeID()));
    form.setCustomerID(order.getCustomerID());

    if (order.getProducts() != null) {
      order.getProducts().stream().forEach(p ->{
        form.getProducts().put(Integer.valueOf(p.getProductID()), p.getProductName());
      });
    }

  }

  @Override
  public Order convertToEntity(OrderForm form) {
    Order order = new Order();
    if (StringUtils.isNotEmpty(form.getId())) {
      order.setId(Long.valueOf(form.getId()));
    } else {
      order.setId(null); //new node
    }
    if (StringUtils.isNotEmpty(form.getOrderID())) {
      //order.orderID = Integer.valueOf(form.getOrderID());
    	order.setOrderID(form.getOrderID());
    } else {
      //order.orderID = maxEntityID() + 1; //new node
    	order.setOrderID((maxEntityID() + 1) + ""); //new node
    }
    order.setOrderDate(form.getOrderDate());
    order.setShipName(form.getShipName());
    order.setShipCountry(form.getShipCountry());
    order.setShipRegion(form.getShipRegion());
    order.setShipCity(form.getShipCity());
    order.setShipAddress(form.getShipAddress());
    order.setShipPostalCode(ServiceUtils.nvl(form.getShipPostalCode()));
    order.setShippedDate(form.getShippedDate());
    order.setRequiredDate(form.getRequiredDate());
    order.setShipVia(form.getShipVia());
    order.setFreight(form.getFreight());
    //order.employeeID = ServiceUtils.nvlToInt(form.getEmployeeID());
    order.setEmployeeID(form.getEmployeeID());

    //retrieve customer
    if (StringUtils.isNotEmpty(form.getCustomerID())) {
      order.setCustomerID(form.getCustomerID());
      order.setCustomer(customerRepository.findByCustomerID(order.getCustomerID()));
    } else {
      throw new RuntimeException("Customer not found");
    }

    //retrieve products
    if (form.getProducts() != null) {
      Set<Product> p = new HashSet<>();
      form.getProducts().forEach((key,value)->{
        p.add(productRepository.findOne(Long.valueOf(key), 0));
      });    
      order.setProducts(p);
    } else {
      order.setProducts(null);
    }

    return order;
  }

  public long countByShipNameLike(String name) {
    return orderRepository.countByShipNameLike(ServiceUtils.nameLike(name));
  }

  public Iterable<Order> findByShipNameLike(String name, int page, int size) {
    int skip = (page - 1) * size;
    return orderRepository.findByShipNameLike(ServiceUtils.nameLike(name), skip, size);
  }

  @Override
  public Integer maxEntityID() {
    return orderRepository.maxOrderID();
  }

}