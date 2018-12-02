package guru.springframework.recipe.project.recipeproject.repositories.reactive;

import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
