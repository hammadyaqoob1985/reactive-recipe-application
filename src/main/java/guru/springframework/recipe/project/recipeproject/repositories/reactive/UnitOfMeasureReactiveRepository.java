package guru.springframework.recipe.project.recipeproject.repositories.reactive;

import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

    Mono<UnitOfMeasure> findByUom(String uom);
}
