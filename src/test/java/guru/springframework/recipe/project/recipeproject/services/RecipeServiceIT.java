package guru.springframework.recipe.project.recipeproject.services;

import guru.springframework.recipe.project.recipeproject.commands.RecipeCommand;
import guru.springframework.recipe.project.recipeproject.coverters.RecipeCommandToRecipe;
import guru.springframework.recipe.project.recipeproject.coverters.RecipeToRecipeCommand;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    public static final String TEST_DESCRIPTION = "Test description";
    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    public void testSaveDescription() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();

        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(testRecipe);

        recipeCommand.setDescription(TEST_DESCRIPTION);

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand).block();

        assertEquals(TEST_DESCRIPTION, savedRecipeCommand.getDescription());

        assertEquals(savedRecipeCommand.getId(), testRecipe.getId());
        assertEquals(savedRecipeCommand.getCategories().size(), testRecipe.getCategories().size());
        assertEquals(savedRecipeCommand.getIngredients().size(), testRecipe.getIngredients().size());

    }

}