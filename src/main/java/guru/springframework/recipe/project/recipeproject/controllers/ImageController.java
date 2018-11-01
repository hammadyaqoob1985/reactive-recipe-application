package guru.springframework.recipe.project.recipeproject.controllers;

import guru.springframework.recipe.project.recipeproject.commands.RecipeCommand;
import guru.springframework.recipe.project.recipeproject.services.ImageService;
import guru.springframework.recipe.project.recipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.isNull;

@Slf4j
@Controller
public class ImageController {

    RecipeService recipeService;

    ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String getImageform(Model model, @PathVariable String recipeId) {
        RecipeCommand recipeCommand =  recipeService.findCommandById(recipeId);
        model.addAttribute("recipe", recipeCommand);
        return "/recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String uploadImage(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile multipartFile) {

        imageService.saveImageFile(recipeId, multipartFile);

        return "redirect:/recipe/" + recipeId + "/show/";
    }

    @GetMapping("/recipe/{recipeId}/recipeimage")
    public void renderImageFromDb(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand =  recipeService.findCommandById(recipeId);

        if(!isNull(recipeCommand.getImage())) {
            byte[] imageBytes = new byte[recipeCommand.getImage().length];

            int i = 0;

            for (Byte bytee : recipeCommand.getImage()) {
                imageBytes[i++] = bytee;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(imageBytes);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
