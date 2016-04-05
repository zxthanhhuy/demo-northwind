package northwind.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import northwind.entities.User;

@Repository
public interface UserRepository extends GraphRepository<User> {
	@Query("MATCH (user:User) RETURN user")
	Iterable<User> getAll();

	// TODO:
	@Query()
	Integer maxEntityID();
}
