package guru.springframework.recipe.project.recipeproject.repositories;

import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
