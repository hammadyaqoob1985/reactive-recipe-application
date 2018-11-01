package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.CategoryCommand;
import guru.springframework.recipe.project.recipeproject.domain.Category;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    public static final String ID = "1";
    public static final String TEST_DECRIPTION = "TEST DECRIPTION";
    public static final HashSet<Recipe> RECIPES = new HashSet<Recipe>();
    CategoryCommandToCategory categoryCommandToCategory;

    @Before
    public void setUp() throws Exception {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    public void testNullEntry() {
        assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }


    @Test
    public void testPropertiesConverted() {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(TEST_DECRIPTION);

        Category convertedCategory = categoryCommandToCategory.convert(categoryCommand);

        assertEquals(ID, convertedCategory.getId());
        assertEquals(TEST_DECRIPTION, convertedCategory.getDescription());

    }

}