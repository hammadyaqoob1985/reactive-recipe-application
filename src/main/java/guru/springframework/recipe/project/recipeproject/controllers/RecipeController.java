package guru.springframework.recipe.project.recipeproject.controllers;

import guru.springframework.recipe.project.recipeproject.commands.RecipeCommand;
import guru.springframework.recipe.project.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipe.project.recipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
//import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    public static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
    RecipeService recipeService;


    private WebDataBinder webDataBinder;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {

        this.webDataBinder = webDataBinder;
    }

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"/recipe/{id}/show"})
    public String getRecipe(Model model, @PathVariable String id) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "/recipe/show";
    }

    @GetMapping({"/recipe/new"})
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_RECIPEFORM_URL;
    }

    @GetMapping({"/recipe/{id}/update"})
    public String getRecipeCommand(Model model, @PathVariable String id) {
        model.addAttribute("recipe", recipeService.findCommandById(id).block());
        return RECIPE_RECIPEFORM_URL;
    }

    @GetMapping({"/recipe/{id}/delete"})
    public String deleteById(@PathVariable String id) {
        log.debug("deleting recipe with id" + id);
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute("recipe") RecipeCommand recipeCommand) {

        webDataBinder.validate();

        BindingResult bindingResult = webDataBinder.getBindingResult();


        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.debug(error.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand).block();

        return "redirect:/recipe/" + savedCommand.getId() + "/show/";
    }

 //   Add response status here other 200 will be returned not 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model) {
        log.error("Handling Not Found Error");
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);

        return "404error";
    }
}
