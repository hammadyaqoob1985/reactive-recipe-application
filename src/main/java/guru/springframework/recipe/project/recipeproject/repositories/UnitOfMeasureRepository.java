package guru.springframework.recipe.project.recipeproject.repositories;

import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findByUom(String uom);
}
