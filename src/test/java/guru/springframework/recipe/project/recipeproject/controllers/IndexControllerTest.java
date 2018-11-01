package guru.springframework.recipe.project.recipeproject.controllers;

import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {

        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe1 =  new Recipe();
        recipe1.setId("1");
        recipes.add(recipe1);

        Recipe recipe2 =  new Recipe();
        recipe2.setId("2");
        recipes.add(recipe2);

        //set up argument captor to capture argument of type set
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        when(recipeService.getRecipes()).thenReturn(recipes);
        String result = indexController.getIndexPage(model);

        //when doing verify capture arguments then
        verify(model,times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        verify(recipeService,times(1)).getRecipes();

        assertEquals("index",result);

        Set<Recipe> recipeSetAddedToModel = argumentCaptor.getValue();
        assertEquals(2, recipeSetAddedToModel.size());


    }
}