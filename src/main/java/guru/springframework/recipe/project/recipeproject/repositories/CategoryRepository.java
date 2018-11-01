package guru.springframework.recipe.project.recipeproject.repositories;

import guru.springframework.recipe.project.recipeproject.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {

    Optional<Category> findByDescription(String description);
}
