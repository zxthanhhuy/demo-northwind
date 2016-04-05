package northwind.repositories;

import java.util.Map;

import northwind.entities.Supplier;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends GraphRepository<Supplier> {

  @Query("MATCH (s:Supplier) WHERE s.supplierID = {0} RETURN s LIMIT 1")
  Supplier findBySupplierID(String supplierID);

  @Query("MATCH (s:Supplier) RETURN s.supplierID AS supplierID, s.companyName AS companyName ORDER BY s.companyName ASC")
  Iterable<Map<String, Object>> supplierIDs();

  @Query("MATCH (s:Supplier) RETURN MAX(s.supplierID)")
  Integer maxSupplierID();

}