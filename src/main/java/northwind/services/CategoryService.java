package northwind.services;

import java.util.Map;

import northwind.controllers.forms.CategoryForm;
import northwind.entities.Category;
import northwind.repositories.CategoryRepository;
import northwind.services.utils.GenericCRUDService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends GenericCRUDService<Category, CategoryForm> {
  final static Logger logger = LoggerFactory.getLogger(CategoryService.class);

  @Autowired 
  CategoryRepository  categoryRepository;

  @Override
  public GraphRepository<Category> getRepository() {
    return categoryRepository;
  }

  @Override
  public Iterable<Map<String, Object>> entityIDs() {
    return categoryRepository.categoryIDs();
  }

  @Override
  public void convertToForm(Category category, CategoryForm form) {
    form.setId(category.getId().toString());
    form.setCategoryID(category.getCategoryID().toString());
    form.setCategoryName(category.getCategoryName());
    form.setDescription(category.getDescription());
    form.setPicture(category.getPicture());
  }

  @Override
  public Category convertToEntity(CategoryForm form) {
    Category category = new Category();
    if (StringUtils.isNotEmpty(form.getId())) {
      category.setId(Long.valueOf(form.getId()));
    } else {
      category.setId(null); //new node
    }
    if (StringUtils.isNotEmpty(form.getCategoryID())) {
    	category.setCategoryID(form.getCategoryID());
    } else {
    	category.setCategoryID((maxEntityID() + 1) + ""); //new node
    }
    category.setCategoryName(form.getCategoryName());
    category.setDescription(ServiceUtils.nvl(form.getDescription()));
    category.setPicture(ServiceUtils.nvl(form.getPicture()));
    return category;
  }

  @Override
  public Integer maxEntityID() {
    return categoryRepository.maxCategoryID();
  }

}