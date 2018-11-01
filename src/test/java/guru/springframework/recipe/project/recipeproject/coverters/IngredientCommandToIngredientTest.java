package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.IngredientCommand;
import guru.springframework.recipe.project.recipeproject.commands.RecipeCommand;
import guru.springframework.recipe.project.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.project.recipeproject.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {

    public static final String ID = "1";
    public static final String TEST_DESCRIPTION = "TEST DESCRIPTION";
    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final RecipeCommand RECIPE = new RecipeCommand();
    public static final String UOM_ID = "2";
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @Before
    public void setUp() throws Exception {

        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullSource() {
        assertNull(ingredientCommandToIngredient.convert(null));
    }

    @Test
    public void testEmptySource() {
        assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
    }

    @Test
    public void testPropertyConverter() {

        IngredientCommand ingredientCommand =  new IngredientCommand();
        ingredientCommand.setId(ID);
        ingredientCommand.setDescription(TEST_DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        ingredientCommand.setUom(unitOfMeasureCommand);

        Ingredient convertedIngredient = ingredientCommandToIngredient.convert(ingredientCommand);

        assertEquals(ID, convertedIngredient.getId());
        assertEquals(TEST_DESCRIPTION, convertedIngredient.getDescription());
        assertEquals(AMOUNT, convertedIngredient.getAmount());

        assertEquals(UOM_ID, convertedIngredient.getUom().getId());

    }

    @Test
    public void testPropertyConverterWithNullUoM() {

        IngredientCommand ingredientCommand =  new IngredientCommand();
        ingredientCommand.setId(ID);
        ingredientCommand.setDescription(TEST_DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);

        Ingredient convertedIngredient = ingredientCommandToIngredient.convert(ingredientCommand);

        assertEquals(ID, convertedIngredient.getId());
        assertEquals(TEST_DESCRIPTION, convertedIngredient.getDescription());
        assertEquals(AMOUNT, convertedIngredient.getAmount());

        assertNull(convertedIngredient.getUom());

    }
}