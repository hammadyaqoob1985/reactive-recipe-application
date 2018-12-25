package guru.springframework.recipe.project.recipeproject.services;

import guru.springframework.recipe.project.recipeproject.commands.IngredientCommand;
import guru.springframework.recipe.project.recipeproject.coverters.IngredientCommandToIngredient;
import guru.springframework.recipe.project.recipeproject.coverters.IngredientToIngredientCommand;
import guru.springframework.recipe.project.recipeproject.domain.Ingredient;
import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipe.project.recipeproject.repositories.UnitOfMeasureRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 UnitOfMeasureReactiveRepository unitOfMeasureRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientCaptor) {
        return recipeReactiveRepository.findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredientCaptor.equalsIgnoreCase(ingredient.getId()))
                .single()
                .map(ingredient -> {
                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
                    ingredientCommand.setRecipeId(recipeId);
                    return ingredientCommand;
                });



        //        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId).block();
//
//        if(!recipeOptional.isPresent()) {
//            log.debug("recipe not found" + recipeId);
//        }
//        Recipe recipe = recipeOptional.get();
//        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
//                .filter(ingredient -> ingredient.getId().equals(ingredientCaptor))
//                .map( ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
//
//        if(!ingredientCommandOptional.isPresent()){
//            //todo impl error handling
//            log.error("Ingredient id not found: " + ingredientCaptor);
//        }
//
//        //enhance command object with recipe id
//        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
//        ingredientCommand.setRecipeId(recipe.getId());
//
//        return Mono.just(ingredientCommand);
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {
        Recipe recipe = recipeReactiveRepository.findById(ingredientCommand.getRecipeId()).block();

        if (recipe == null) {

            //todo toss error if not found!
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return Mono.just(new IngredientCommand());
        } else {

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUom().getId()).block());
                //.orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
                if (ingredientFound.getUom() == null) {
                    new RuntimeException("UOM NOT FOUND");
                }

            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);

            }

            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if (!savedIngredientOptional.isPresent()) {
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }


            //enhance with id value
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
        }
    }

    @Override
    public Mono<Void> deleteByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
       Recipe recipe = recipeReactiveRepository.findById(recipeId).block();

        if (recipe == null) {

            //todo toss error if not found!
            log.error("Recipe not found for id: " + recipeId);

        } else {

            Optional<Ingredient> ingredientToBedeletedOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

            if (ingredientToBedeletedOptional.isPresent()) {
                Ingredient ingredientToBeDeleted = ingredientToBedeletedOptional.get();
                ingredientToBeDeleted.setRecipe(null);
                recipe.getIngredients().remove(ingredientToBeDeleted);
                recipeReactiveRepository.save(recipe).block();
            }
        }
        return Mono.empty();
    }
}
