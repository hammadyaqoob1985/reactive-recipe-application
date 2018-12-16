package guru.springframework.recipe.project.recipeproject.services;


import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {
     Mono<Void> saveImageFile(String recipeId, MultipartFile multipartFile);
}
