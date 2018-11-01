package guru.springframework.recipe.project.recipeproject.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
//avoid addind recipes in hashcode as can cause circular reference error
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {

    private String id;


    private Recipe recipe;
    //for large information in hibernate as hibernate only support 255 characted max. Will be stored is clob in hibernate
    private String recipeNotes;

}
