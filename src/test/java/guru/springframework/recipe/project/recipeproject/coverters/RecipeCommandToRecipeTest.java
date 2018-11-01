package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.CategoryCommand;
import guru.springframework.recipe.project.recipeproject.commands.IngredientCommand;
import guru.springframework.recipe.project.recipeproject.commands.NotesCommand;
import guru.springframework.recipe.project.recipeproject.commands.RecipeCommand;
import guru.springframework.recipe.project.recipeproject.domain.Difficulty;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    public static final String ID = "1";
    public static final Integer COOK_TIME = 5;
    public static final String TEST_DESCRIPTION = "Test description";
    public static final Difficulty EASY = Difficulty.EASY;
    public static final String TEST_DESC = "Test desc";
    public static final Integer PREP_TIME = 5;
    public static final Integer SERVINGS = 2;
    public static final String TEST_URL = "TEST URL";
    public static final Integer SOURCE = 2;
    public static final String ID1 = "5";
    public static final String ID2 = "6";
    public static final String ID3 = "7";
    public static final String ID4 = "6";
    public static final String ID5 = "8";
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {

        recipeCommandToRecipe = new RecipeCommandToRecipe( new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void testNullSource() {
        assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    public void testEmptySource() {
        assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
    }

    @Test
    public void testPropertyConversion() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setDescription(TEST_DESCRIPTION);
        recipeCommand.setDifficulty(EASY);
        recipeCommand.setDirections(TEST_DESC);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setUrl(TEST_URL);
        recipeCommand.setSource(SOURCE);

        NotesCommand notes = new NotesCommand();
        notes.setId(ID1);

        recipeCommand.setNotes(notes);

        CategoryCommand category = new CategoryCommand();
        category.setId(ID2);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(ID3);

        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(ID4);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(ID5);

        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);

        //when
        Recipe recipe  = recipeCommandToRecipe.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(TEST_DESCRIPTION, recipe.getDescription());
        assertEquals(EASY, recipe.getDifficulty());
        assertEquals(TEST_DESC, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(TEST_URL, recipe.getUrl());
        assertEquals(ID1, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}