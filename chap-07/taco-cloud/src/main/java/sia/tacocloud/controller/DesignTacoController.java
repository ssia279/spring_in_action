package sia.tacocloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.models.Ingredient;
import sia.tacocloud.models.Taco;
import sia.tacocloud.models.TacoOrder;
import sia.tacocloud.models.User;
import sia.tacocloud.repository.IngredientRepository;
import sia.tacocloud.repository.TacoRepository;
import sia.tacocloud.repository.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

  private final IngredientRepository ingredientRepo;
  private final UserRepository userRepo;
  private final TacoRepository tacoRepo;

  @Autowired
  public DesignTacoController(IngredientRepository ingredientRepo, UserRepository userRepo, TacoRepository tacoRepo) {
    this.ingredientRepo = ingredientRepo;
    this.userRepo = userRepo;
    this.tacoRepo = tacoRepo;
  }

  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepo.findAll().forEach(i -> ingredients.add(i));

    Ingredient.Type[] types = Ingredient.Type.values();
    for (Ingredient.Type type : types) {
      model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
    }
  }

  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @ModelAttribute(name = "user")
  public User user(Principal principal) {
    String username = principal.getName();
    User user = userRepo.findByUsername(username);
    return user;
  }

  @GetMapping
  public String showDesignForm() {
    return "design";
  }

  @PostMapping
  public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
    if (errors.hasErrors()) {
      return "design";
    }
    Taco saved = tacoRepo.save(taco);
    tacoOrder.addTaco(saved);
    log.info("Processing taco: {}", taco);

    return "redirect:/orders/current";
  }

  private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
    return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
  }
}
