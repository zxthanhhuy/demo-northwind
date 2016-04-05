package northwind.controllers;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import northwind.controllers.forms.OrderForm;
import northwind.entities.Order;
import northwind.entities.Product;
import northwind.services.OrderService;
import northwind.services.RecommendService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends NorthWindController {
  final static Logger logger = LoggerFactory.getLogger(OrderController.class);

  final  static  int  PAGE_SIZE  =  50;

  @Autowired 
  OrderService  orderService;

  @Autowired 
  RecommendService  recommendService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Model model) {
    return index(1, null, model);
  }

  @RequestMapping(value = "/{pageNo}", method = RequestMethod.GET)
  public String index(
    @PathVariable Integer pageNo,
    @RequestParam(required = false, defaultValue = "") String searchName,
    Model model) {
    int totalCount = 0;
    Iterable<Order> result;
    if (StringUtils.isNotEmpty(searchName)) {
      result = orderService.findByShipNameLike(searchName, pageNo, PAGE_SIZE);
      totalCount = (int)orderService.countByShipNameLike(searchName);
    } else {
      result = orderService.findAll(pageNo, PAGE_SIZE, "orderID");
      totalCount = (int)orderService.count();
    }
    model.addAttribute("result", result);
    addPageAttr(orderService.calcPage(totalCount, pageNo, PAGE_SIZE), model);
    model.addAttribute("searchName", searchName);
    addHeaderAttr(HEADER_INDEX, model);
    return "Order/index";
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  public String detail(
    @PathVariable Long id,
    Model model) {
    Order order = orderService.findOne(id);
    model.addAttribute("order", order);
    Iterable<Product> recommend = recommendService.recommendProducts(order.getCustomerID(), order.getOrderID());
    model.addAttribute("recommend", recommend);
    addHeaderAttr(HEADER_DETAIL, model);
    return "Order/detail";
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String create(
    OrderForm form,
    Model model) {
    addHeaderAttr(HEADER_CREATE, model);
    return "Order/create";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public String edit(
    @PathVariable Long id,
    OrderForm form,
    Model model) {
    orderService.findOneToForm(id, form);
    addHeaderAttr(HEADER_EDIT, model);
    return "Order/create";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(
    @Validated @ModelAttribute OrderForm form,
    BindingResult result,
    Model model) {
    if (result.hasErrors()) {
      model.addAttribute("errorMessage", "validation error");
      return create(form, model);
    }
    Order order = orderService.save(form, 0);
    model.addAttribute("order", order);
    addHeaderAttr(HEADER_DETAIL, model);
    return "Order/detail";
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public String delete(
    @RequestParam(required = true) Long id) {
    orderService.delete(id);
    return "redirect:/order/";
  }

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_INDEX =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Order");
        put("subtitle", "list");
    }});

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_DETAIL =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Order");
        put("subtitle", "detail");
    }});

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_CREATE =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Order");
        put("subtitle", "create");
    }});

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_EDIT =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Order");
        put("subtitle", "edit");
    }});
}