package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.IngredientCommand;
import guru.springframework.recipe.project.recipeproject.domain.Ingredient;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static org.thymeleaf.util.StringUtils.isEmpty;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();

        if (!isEmpty(source.getId())) {
            ingredient.setId(source.getId());
        }
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(unitOfMeasureReactiveRepository.findById(source.getUom().getId()).block());
        return ingredient;
    }
}
