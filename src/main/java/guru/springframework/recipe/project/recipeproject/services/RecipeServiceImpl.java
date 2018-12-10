package guru.springframework.recipe.project.recipeproject.services;

import guru.springframework.recipe.project.recipeproject.commands.RecipeCommand;
import guru.springframework.recipe.project.recipeproject.coverters.RecipeCommandToRecipe;
import guru.springframework.recipe.project.recipeproject.coverters.RecipeToRecipeCommand;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//lombok automatically gives you log
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository,
                             RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
       Recipe recipe = recipeRepository.findById(id).block();
       if(recipe == null) {
           throw new NotFoundException("Recipe with Id value " + id.toString() + " not found!");
       }
       return Mono.just(recipe);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String Id) {

        return recipeRepository.findById(Id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

                    recipeCommand.getIngredients().forEach(ingredient -> {
                        ingredient.setRecipeId(recipeCommand.getId());
                    });
                    return recipeCommand;
                });

//        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(Id).block());
//
//        //enhance command object with id value
//        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
//            recipeCommand.getIngredients().forEach(rc -> {
//                rc.setRecipeId(recipeCommand.getId());
//            });
//        }
//
//        return recipeCommand;
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
        return recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        recipeRepository.deleteById(id).block();
        return Mono.empty();
    }

}
