package guru.springframework.recipe.project.recipeproject.repositories;

import guru.springframework.recipe.project.recipeproject.bootstrap.RecipeBootStrap;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.CategoryReactiveRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReactiveRepositoryTestIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception {
      recipeRepository.deleteAll();
    }

    @Test
    //release spring context after test. Beans releasesed after test
    @DirtiesContext
    public void saveAndFetch() {
        Recipe recipe = new Recipe();
        recipe.setId("TEST ID");
        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();
        assertEquals(Long.valueOf(1), count);
    }


}