package northwind.controllers;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import northwind.controllers.forms.CustomerForm;
import northwind.entities.Customer;
import northwind.services.CustomerService;

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
@RequestMapping(value = "/customer")
public class CustomerController extends NorthWindController {
  final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

  final  static  int  PAGE_SIZE  =  10;

  @Autowired 
  CustomerService  customerService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Model model) {
    return index(1, model);
  }

  @RequestMapping(value = "/{pageNo}", method = RequestMethod.GET)
  public String index(
    @PathVariable Integer pageNo,
    Model model) {
    Iterable<Customer> result = customerService.findAll(pageNo, PAGE_SIZE, "customerID");
    model.addAttribute("result", result);
    int totalCount = (int)customerService.count();
    addPageAttr(customerService.calcPage(totalCount, pageNo, PAGE_SIZE), model);
    addHeaderAttr(HEADER_INDEX, model);
    return "Customer/index";
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  public String detail(
    @PathVariable Long id,
    Model model) {
    Customer customer = customerService.findOne(id);
    model.addAttribute("customer", customer);
    addHeaderAttr(HEADER_DETAIL, model);
    return "Customer/detail";
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String create(
    CustomerForm form,
    Model model) {
    addHeaderAttr(HEADER_CREATE, model);
    return "Customer/create";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public String edit(
    @PathVariable Long id,
    CustomerForm form,
    Model model) {
    customerService.findOneToForm(id, form);
    addHeaderAttr(HEADER_EDIT, model);
    return "Customer/create";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(
    @Validated @ModelAttribute CustomerForm form,
    BindingResult result,
    Model model) {
    if (result.hasErrors()) {
      model.addAttribute("errorMessage", "validation error");
      return create(form, model);
    }
    Customer customer = customerService.save(form, 0);
    model.addAttribute("customer", customer);
    addHeaderAttr(HEADER_DETAIL, model);
    return "Customer/detail";
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public String delete(
    @RequestParam(required = true) Long id) {
    customerService.delete(id);
    return "redirect:/customer/";
  }

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_INDEX =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Customer");
        put("subtitle", "list");
    }});

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_DETAIL =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Customer");
        put("subtitle", "detail");
    }});

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_CREATE =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Customer");
        put("subtitle", "create");
    }});

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_EDIT =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Customer");
        put("subtitle", "edit");
    }});

}