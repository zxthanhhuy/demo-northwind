package northwind.repositories;

import java.util.Map;

import northwind.entities.Category;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends GraphRepository<Category> {

  @Query("MATCH (c:Category) WHERE c.categoryID = {0} RETURN c LIMIT 1")
  Category findByCategoryID(String categoryID);

  @Query("MATCH (c:Category) RETURN c.categoryID AS categoryID, c.categoryName AS categoryName ORDER BY c.categoryName ASC")
  Iterable<Map<String, Object>> categoryIDs();

  @Query("MATCH (c:Category) RETURN MAX(c.categoryID)")
  Integer maxCategoryID();

}