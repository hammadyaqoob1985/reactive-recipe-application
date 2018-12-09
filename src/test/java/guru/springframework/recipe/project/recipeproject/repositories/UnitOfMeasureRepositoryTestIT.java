package guru.springframework.recipe.project.recipeproject.repositories;

import guru.springframework.recipe.project.recipeproject.bootstrap.RecipeBootStrap;
import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.CategoryReactiveRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryTestIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;


    @Before
    public void setUp() throws Exception {

        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();

        RecipeBootStrap recipeBootStrap = new RecipeBootStrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
        recipeBootStrap.onApplicationEvent(null);
    }

    @Test
    public void findByUom() {
        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByUom("Teaspoon");
        assertEquals("Teaspoon", optionalUnitOfMeasure.get().getUom());
    }

    @Test
    public void findByUomCup() {
        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByUom("Cup");
        assertEquals("Cup", optionalUnitOfMeasure.get().getUom());
    }
}