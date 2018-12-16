package guru.springframework.recipe.project.recipeproject.services;

import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile multipartFile) {

        Mono<Recipe> monoRecipe = recipeRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] byteObjects = new Byte[0];
                    try {

                        byteObjects = new Byte[multipartFile.getBytes().length];


                        int i = 0;

                        for (Byte b : multipartFile.getBytes()) {
                            byteObjects[i++] = b;
                        }

                        recipe.setImage(byteObjects);

                        return recipe;
                    } catch (IOException e) {

                        log.debug("error occurred");
                        throw new RuntimeException(e);
                    }
                });

        recipeRepository.save(monoRecipe.block()).block();

        return Mono.empty();
    }
}

//        try {
//            Recipe recipe = recipeRepository.findById(recipeId).get();
//
//
//            Byte[] byteObjects = new Byte[multipartFile.getBytes().length];
//
//
//            int i = 0;
//
//            for (Byte b : multipartFile.getBytes()) {
//                byteObjects[i++] = b;
//            }
//
//            recipe.setImage(byteObjects);
//
//            recipeRepository.save(recipe);
//            log.debug("image received");
//        } catch (IOException e) {
//
//            log.debug("error occurred");
//            e.printStackTrace();
//        }
        //  }
