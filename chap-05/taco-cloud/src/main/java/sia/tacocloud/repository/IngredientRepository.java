package sia.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.models.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
