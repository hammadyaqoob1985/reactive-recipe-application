package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.IngredientCommand;
import guru.springframework.recipe.project.recipeproject.domain.Ingredient;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {

    public static final String ID = "1";
    public static final String TEST_DESCRIPTION = "TEST DESCRIPTION";
    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final Recipe RECIPE = new Recipe();
    public static final String ID1 = "2";
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @Before
    public void setUp() throws Exception {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullSource() {
        assertNull(ingredientToIngredientCommand.convert(null));
    }

    @Test
    public void testEmptySource() {
        assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));
    }

    @Test
    public void testNullUom() {

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(TEST_DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setRecipe(RECIPE);
        ingredient.getRecipe().setId("1");

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        assertEquals(ID,ingredientCommand.getId());
        assertEquals(TEST_DESCRIPTION,ingredientCommand.getDescription());
        assertEquals(AMOUNT,ingredientCommand.getAmount());
        assertEquals("1",ingredientCommand.getRecipeId());
    }

    @Test
    public void testWithlUom() {

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(TEST_DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setRecipe(RECIPE);

        UnitOfMeasure unitOfMeasure =  new UnitOfMeasure();
        unitOfMeasure.setId(ID1);
        ingredient.setUom(unitOfMeasure);

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        assertEquals(ID,ingredientCommand.getId());
        assertEquals(TEST_DESCRIPTION,ingredientCommand.getDescription());
        assertEquals(AMOUNT,ingredientCommand.getAmount());
        assertEquals(ID1,ingredientCommand.getUom().getId());
    }

}