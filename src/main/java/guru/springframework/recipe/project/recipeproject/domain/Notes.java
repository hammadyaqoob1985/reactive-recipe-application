package guru.springframework.recipe.project.recipeproject.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
//avoid addind recipes in hashcode as can cause circular reference error
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {

    @Id
    private String id;


    private Recipe recipe;
    //for large information in hibernate as hibernate only support 255 characted max. Will be stored is clob in hibernate
    private String recipeNotes;

}
