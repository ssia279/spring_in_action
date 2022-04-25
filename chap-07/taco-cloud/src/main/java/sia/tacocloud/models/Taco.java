package sia.tacocloud.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Document
@RestResource(rel = "tacos", path = "tacos")
public class Taco {

  @Id
  private String id;
  @NotNull
  @Size(min = 5, message = "Name must be at least 5 characters long")
  private String name;

  private Date createdAt = new Date();

  @NotNull
  @Size(min = 1, message = "You must choose at least 1 ingredient")
  private List<Ingredient> ingredients;

  public void addIngredient(Ingredient ingredient) {
    ingredients.add(ingredient);
  }
}
