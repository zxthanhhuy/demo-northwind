package northwind.controllers;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import northwind.controllers.forms.CategoryForm;
import northwind.entities.Category;
import northwind.services.CategoryService;

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
@RequestMapping(value = "/category")
public class CategoryController extends NorthWindController {
  final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

  final  static  int  PAGE_SIZE  =  5;

  @Autowired 
  CategoryService  categoryService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Model model) {
    return index(1, model);
  }
  
  @RequestMapping(value = "/{pageNo}", method = RequestMethod.GET)
  public String index(
    @PathVariable Integer pageNo,
    Model model) {
    Iterable<Category> result = categoryService.findAll(pageNo, PAGE_SIZE, "categoryID");
    model.addAttribute("result", result);
    int totalCount = (int)categoryService.count();
    addPageAttr(categoryService.calcPage(totalCount, pageNo, PAGE_SIZE), model);
    addHeaderAttr(HEADER_INDEX, model);
    return "Category/index";
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  public String detail(
    @PathVariable Long id,
    Model model) {
    Category category = categoryService.findOne(id);
    model.addAttribute("category", category);
    addHeaderAttr(HEADER_DETAIL, model);
    return "Category/detail";
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String create(
    CategoryForm form,
    Model model) {
    addHeaderAttr(HEADER_CREATE, model);
    return "Category/create";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public String edit(
    @PathVariable Long id,
    CategoryForm form,
    Model model) {
    categoryService.findOneToForm(id, form);
    addHeaderAttr(HEADER_EDIT, model);
    return "Category/create";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(
    @Validated @ModelAttribute CategoryForm form,
    BindingResult result,
    Model model) {
    if (result.hasErrors()) {
      model.addAttribute("errorMessage", "validation error");
      return create(form, model);
    }
    Category category = categoryService.save(form, 0);
    model.addAttribute("category", category);
    addHeaderAttr(HEADER_DETAIL, model);
    return "Category/detail";
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public String delete(
    @RequestParam(required = true) Long id) {
    categoryService.delete(id);
    return "redirect:/category/";
  }

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_INDEX =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Category");
        put("subtitle", "list");
    }});

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_DETAIL =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Category");
        put("subtitle", "detail");
    }});

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_CREATE =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Category");
        put("subtitle", "create");
    }});

  @SuppressWarnings("serial")
  final static Map<String, String> HEADER_EDIT =
    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        put("title", "Category");
        put("subtitle", "edit");
    }});

}