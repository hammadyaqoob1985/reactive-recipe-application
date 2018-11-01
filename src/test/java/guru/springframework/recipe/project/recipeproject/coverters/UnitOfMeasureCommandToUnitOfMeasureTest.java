package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String ID = "1";
    public static final String TEST_UOM = "TEST UOM";
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    @Before
    public void setUp() throws Exception {
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullSource() {
        assertNull(unitOfMeasureCommandToUnitOfMeasure.convert(null));
    }

    @Test
    public void testEmptySource() {
        assertNotNull(unitOfMeasureCommandToUnitOfMeasure.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void testPropertyConversion() {
        UnitOfMeasureCommand unitOfMeasureCommand =  new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setUom(TEST_UOM);

        UnitOfMeasure unitOfMeasure= unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);

        assertEquals(ID, unitOfMeasure.getId());
        assertEquals(TEST_UOM, unitOfMeasure.getUom());
    }
}