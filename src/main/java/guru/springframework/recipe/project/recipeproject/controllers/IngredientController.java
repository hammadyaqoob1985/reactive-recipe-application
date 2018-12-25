package guru.springframework.recipe.project.recipeproject.controllers;

import guru.springframework.recipe.project.recipeproject.commands.IngredientCommand;
import guru.springframework.recipe.project.recipeproject.commands.RecipeCommand;
import guru.springframework.recipe.project.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.project.recipeproject.services.IngredientService;
import guru.springframework.recipe.project.recipeproject.services.RecipeService;
import guru.springframework.recipe.project.recipeproject.services.UomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class IngredientController {

    RecipeService recipeService;
    IngredientService ingredientService;
    UomService uomService;

    private WebDataBinder webDataBinder;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UomService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {

        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getIngredientsList(Model model, @PathVariable String id) {
        log.debug("getting Ingredients list");
        Mono<RecipeCommand> recipeCommand = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipeCommand);
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredientForRecipe(Model model, @PathVariable String recipeId, @PathVariable String ingredientId) {
        Mono<IngredientCommand> ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/add")
    public String addIngredientForRecipe(Model model, @PathVariable String recipeId) {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/ingredientform";
    }


    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredientForRecipe(Model model, @PathVariable String recipeId, @PathVariable String ingredientId) {

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block();
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute("ingredient") IngredientCommand command) {

        webDataBinder.validate();

        BindingResult bindingResult = webDataBinder.getBindingResult();

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.debug(error.toString());
            });
            return "recipe/ingredient/ingredientform";
        }

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredientForRecipe(Model model, @PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @ModelAttribute("uomList")
    public Flux<UnitOfMeasureCommand> getAllUom() {
        return uomService.getAllUoms();
    }
}
