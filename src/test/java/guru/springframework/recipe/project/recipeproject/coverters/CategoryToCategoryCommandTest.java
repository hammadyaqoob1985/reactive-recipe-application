package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.CategoryCommand;
import guru.springframework.recipe.project.recipeproject.domain.Category;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    public static final String ID = "1";
    public static final String TEST_DESCRIPTION = "TEST DESCRIPTION";
    public static final HashSet<Recipe> RECIPES = new HashSet<>();
    CategoryToCategoryCommand categoryToCategoryCommand;
    @Before
    public void setUp() throws Exception {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullSource() {
        assertNull(categoryToCategoryCommand.convert(null));
    }

    @Test
    public void testEmptySource() {
        assertNotNull(categoryToCategoryCommand.convert(new Category()));
    }

    @Test
    public void testPropertyConverters() {

        Category category = new Category();
        category.setId(ID);
        category.setDescription(TEST_DESCRIPTION);
        category.setRecipes(RECIPES);

        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);
        assertEquals(ID, categoryCommand.getId());
        assertEquals(TEST_DESCRIPTION, categoryCommand.getDescription());


    }
}