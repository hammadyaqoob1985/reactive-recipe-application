package guru.springframework.recipe.project.recipeproject.services;

import guru.springframework.recipe.project.recipeproject.commands.IngredientCommand;
import guru.springframework.recipe.project.recipeproject.coverters.IngredientCommandToIngredient;
import guru.springframework.recipe.project.recipeproject.coverters.IngredientToIngredientCommand;
import guru.springframework.recipe.project.recipeproject.coverters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipe.project.recipeproject.coverters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.project.recipeproject.domain.Ingredient;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipe.project.recipeproject.repositories.UnitOfMeasureRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    private IngredientService ingredientService;


    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeReactiveRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {

        Recipe recipe = new Recipe();
        recipe.setId("1");
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        ingredient.setRecipe(recipe);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");
        ingredient2.setRecipe(recipe);


        recipe.setIngredients(new HashSet<>(Arrays.asList(ingredient, ingredient2)));

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just((recipe)));

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "2").block();

        assertEquals(ingredientCommand.getId(), String.valueOf(2));
        assertEquals(ingredientCommand.getRecipeId(), String.valueOf(1));

    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertEquals("3", savedCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void testDeleteIngredientFromRecipe() throws Exception {
        //given
        Recipe existingRecipe = new Recipe();
        existingRecipe.setId("1");

        Ingredient ingredient = new Ingredient();
        ingredient.setId("2");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("3");

        existingRecipe.addIngredient(ingredient);
        existingRecipe.addIngredient(ingredient1);


        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(existingRecipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(existingRecipe));

        ingredientService.deleteByRecipeIdAndIngredientId("1","3");

        verify(recipeReactiveRepository, times(1)).findById(anyString());

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeReactiveRepository, times(1)).save(recipeArgumentCaptor.capture());
        Recipe recipeThatWasSaved = recipeArgumentCaptor.getValue();
        Set<Ingredient> ingredients = recipeThatWasSaved.getIngredients();
        assertEquals(ingredients.size(), 1);
        assertEquals(ingredients.iterator().next().getId(), "2");

    }
}