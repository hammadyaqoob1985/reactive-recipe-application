package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.RecipeCommand;
import guru.springframework.recipe.project.recipeproject.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {

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
    RecipeToRecipeCommand recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {

        recipeCommandToRecipe = new RecipeToRecipeCommand( new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand());
    }

    @Test
    public void testNullSource() {
        assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    public void testEmptySource() {
        assertNotNull(recipeCommandToRecipe.convert(new Recipe()));
    }

    @Test
    public void testPropertyConversion() {
        Recipe recipeCommand = new Recipe();
        recipeCommand.setId(ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setDescription(TEST_DESCRIPTION);
        recipeCommand.setDifficulty(EASY);
        recipeCommand.setDirections(TEST_DESC);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setUrl(TEST_URL);
        recipeCommand.setSource(SOURCE);

        Notes notes = new Notes();
        notes.setId(ID1);

        recipeCommand.setNotes(notes);

        Category category = new Category();
        category.setId(ID2);

        Category category2 = new Category();
        category2.setId(ID3);

        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID4);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ID5);

        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);

        //when
        RecipeCommand recipeCommand2  = recipeCommandToRecipe.convert(recipeCommand);

        assertNotNull(recipeCommand2);
        assertEquals(ID, recipeCommand2.getId());
        assertEquals(COOK_TIME, recipeCommand2.getCookTime());
        assertEquals(PREP_TIME, recipeCommand2.getPrepTime());
        assertEquals(TEST_DESCRIPTION, recipeCommand2.getDescription());
        assertEquals(EASY, recipeCommand2.getDifficulty());
        assertEquals(TEST_DESC, recipeCommand2.getDirections());
        assertEquals(SERVINGS, recipeCommand2.getServings());
        assertEquals(SOURCE, recipeCommand2.getSource());
        assertEquals(TEST_URL, recipeCommand2.getUrl());
        assertEquals(ID1, recipeCommand2.getNotes().getId());
        assertEquals(2, recipeCommand2.getCategories().size());
        assertEquals(2, recipeCommand2.getIngredients().size());
    }
}