package sia.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.models.User;

public interface UserRepository extends CrudRepository<User, String> {

  User findByUsername(String username);
}
