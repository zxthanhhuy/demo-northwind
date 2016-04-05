package northwind.controllers;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import northwind.controllers.forms.ProductForm;
import northwind.controllers.forms.UserForm;
import northwind.entities.User;
import northwind.services.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends NorthWindController {
	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	final static int PAGE_SIZE = 20;

	@Autowired
	UserService userService;

	// @RequestMapping(value = "/", method = RequestMethod.GET)
	// public String index(Model model) {
	// return index(null, model);
	// }

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		Iterable<User> result;
		result = userService.getAll();
		model.addAttribute("result", result);
		return "User/index";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(UserForm form, Model model) {
		//addHeaderAttr(HEADER_CREATE, model);
		return "User/register";
	}

	@SuppressWarnings("serial")
	final static Map<String, String> HEADER_INDEX = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
		{
			put("title", "Product");
			put("subtitle", "list");
		}
	});

	@SuppressWarnings("serial")
	final static Map<String, String> HEADER_DETAIL = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
		{
			put("title", "Product");
			put("subtitle", "detail");
		}
	});

	@SuppressWarnings("serial")
	final static Map<String, String> HEADER_CREATE = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
		{
			put("title", "Product");
			put("subtitle", "create");
		}
	});

	@SuppressWarnings("serial")
	final static Map<String, String> HEADER_EDIT = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
		{
			put("title", "Product");
			put("subtitle", "edit");
		}
	});

}
