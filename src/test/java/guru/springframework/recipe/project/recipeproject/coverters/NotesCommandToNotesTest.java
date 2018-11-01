package guru.springframework.recipe.project.recipeproject.coverters;

import guru.springframework.recipe.project.recipeproject.commands.NotesCommand;
import guru.springframework.recipe.project.recipeproject.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    public static final String ID = "1";
    public static final String TEST_NOTES = "Test Notes";
    NotesCommandToNotes notesCommandToNotes;

    @Before
    public void setUp() throws Exception {
        notesCommandToNotes =  new NotesCommandToNotes();
    }

    @Test
    public void testNullSource() {
        assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    public void testEmptySource() {
        assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
    }

    @Test
    public void testPropertyConversion() {
        NotesCommand notesCommand =  new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(TEST_NOTES);

        Notes notes = notesCommandToNotes.convert(notesCommand);

        assertEquals(ID, notes.getId());
        assertEquals(TEST_NOTES, notes.getRecipeNotes());
    }
}