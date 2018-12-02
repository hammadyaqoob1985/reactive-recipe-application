package guru.springframework.recipe.project.recipeproject.repositories.reactive;

import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {
}
