package guru.springframework.recipe.project.recipeproject.services;

import guru.springframework.recipe.project.recipeproject.commands.RecipeCommand;
import guru.springframework.recipe.project.recipeproject.coverters.RecipeCommandToRecipe;
import guru.springframework.recipe.project.recipeproject.coverters.RecipeToRecipeCommand;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipe.project.recipeproject.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }
    @Test
    public void getRecipeById() {

        Recipe recipe = new Recipe();
        recipe.setId("1");

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById("1");

        assertNotNull(recipeReturned);
        verify(recipeRepository,times(1)).findById(anyString());
        verify(recipeRepository,never()).findAll();
    }

    @Test
    public void getRecipes() {

        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipeData);
        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    public void saveRecipe(){

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        Recipe recipe = new Recipe();
        recipe.setId("2");

        when(recipeCommandToRecipe.convert(any(RecipeCommand.class))).thenReturn(recipe);
        when(recipeToRecipeCommand.convert(any(Recipe.class))).thenReturn(recipeCommand);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        RecipeCommand recipeCommandReturned = recipeService.saveRecipeCommand(recipeCommand);

        assertNotNull(recipeCommandReturned);

        ArgumentCaptor<RecipeCommand> recipeCommandCaptor = ArgumentCaptor.forClass(RecipeCommand.class);
        verify(recipeCommandToRecipe,times(1)).convert(recipeCommandCaptor.capture());

        ArgumentCaptor<Recipe> recipeCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeToRecipeCommand,times(1)).convert(recipeCaptor.capture());

        ArgumentCaptor<Recipe> recipeCaptor1 = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeRepository,times(1)).save(recipeCaptor1.capture());

        assertEquals(recipeCommand, recipeCommandCaptor.getValue());
        assertEquals(recipe, recipeCaptor.getValue());
        assertEquals(recipe, recipeCaptor1.getValue());

    }

    @Test
    public void getRecipeCommandById() {

        Recipe recipe = new Recipe();
        recipe.setId("1");

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeToRecipeCommand.convert(any(Recipe.class))).thenReturn(recipeCommand);

        RecipeCommand commandReturned = recipeService.findCommandById("1");

        assertNotNull(commandReturned);
        verify(recipeRepository,times(1)).findById(anyString());
        verify(recipeRepository,never()).findAll();
        verify(recipeToRecipeCommand,times(1)).convert(any(Recipe.class));
    }

    @Test
    public void deleteRecipeById() {
        recipeService.deleteById("2");
        verify(recipeRepository,times(1)).deleteById(anyString());
    }

    @Test (expected = NotFoundException.class)
    public void testExceptionThrownWhenRecipeNotFound() {
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Recipe recipe =  recipeService.findById("1");
    }
}