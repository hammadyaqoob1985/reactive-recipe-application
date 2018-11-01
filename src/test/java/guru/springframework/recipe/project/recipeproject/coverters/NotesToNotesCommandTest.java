package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.NotesCommand;
import guru.springframework.recipe.project.recipeproject.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    public static final String ID = "1";
    public static final String TEST_NOTES = "Test Notes";
    NotesToNotesCommand notesToNotesCommand;

    @Before
    public void setUp() throws Exception {
        notesToNotesCommand =  new NotesToNotesCommand();
    }

    @Test
    public void testNullSource() {
        assertNull(notesToNotesCommand.convert(null));
    }

    @Test
    public void testEmptySource() {
        assertNotNull(notesToNotesCommand.convert(new Notes()));
    }

    @Test
    public void testPropertyConversion() {
        Notes notes =  new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(TEST_NOTES);

        NotesCommand notesCommand = notesToNotesCommand.convert(notes);

        assertEquals(ID, notesCommand.getId());
        assertEquals(TEST_NOTES, notesCommand.getRecipeNotes());
    }
}