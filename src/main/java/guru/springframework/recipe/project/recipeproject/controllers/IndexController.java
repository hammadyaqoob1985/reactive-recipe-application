package guru.springframework.recipe.project.recipeproject.controllers;

import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
@Slf4j
@Controller
public class IndexController {


    RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model) {
        log.debug("getting index page");
        model.addAttribute("recipes", recipeService.getRecipes().collectList().block());
        return "index";
    }
}
