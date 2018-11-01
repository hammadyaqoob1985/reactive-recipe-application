package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String ID = "1";
    public static final String TEST_UOM = "TEST UOM";
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    @Before
    public void setUp() throws Exception {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullSource() {
        assertNull(unitOfMeasureToUnitOfMeasureCommand.convert(null));
    }

    @Test
    public void testEmptySource() {
        assertNotNull(unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure()));
    }

    @Test
    public void testPropertyConversion() {
        UnitOfMeasure unitOfMeasure =  new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setUom(TEST_UOM);

        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);

        assertEquals(ID, unitOfMeasureCommand.getId());
        assertEquals(TEST_UOM, unitOfMeasureCommand.getUom());
    }
}