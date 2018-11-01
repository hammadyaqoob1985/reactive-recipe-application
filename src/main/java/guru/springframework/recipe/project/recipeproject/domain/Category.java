package guru.springframework.recipe.project.recipeproject.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

//lombok creates getters, setters, constructors, toequals and tostring methods for you for each of your properties
@Data
//avoid addind recipes in hashcode as can cause circular reference error
@EqualsAndHashCode(exclude = {"recipes"})
@Document
public class Category {

    @Id
    private String Id;
    private String description;

    //do not need to have table in jpa for this as already done in recipe categories variable
    @DBRef
    Set<Recipe> recipes;

}
