package guru.springframework.recipe.project.recipeproject.services;

import guru.springframework.recipe.project.recipeproject.domain.Recipe;
import guru.springframework.recipe.project.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(String recipeId, MultipartFile multipartFile) {

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();


            Byte[] byteObjects = new Byte[multipartFile.getBytes().length];


            int i = 0;

            for (Byte b : multipartFile.getBytes()) {
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);
            log.debug("image received");
        } catch (IOException e) {

            log.debug("error occurred");
            e.printStackTrace();
        }
    }
}
