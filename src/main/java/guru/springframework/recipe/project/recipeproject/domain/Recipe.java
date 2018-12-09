package guru.springframework.recipe.project.recipeproject.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
public class Recipe {

    @Id
    private String id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private Integer source;
    private String url;

    private String directions;

    //cascase all persist all operations, Recipe is owner. Will be stored in ingredient recipe attribute
    private Set<Ingredient> ingredients = new HashSet<>();

     private Byte[] image;

    //Use string rather than ordinal as ordinal takes index of enum and if number changes the index will be messed up
    private Difficulty difficulty;

    private Notes notes;

    //added join table as only need one table to show recipes to categories. Without it two tables would be created

    private Set<Category> categories = new HashSet<>();

    //since method already here lombok wont override it
    //added so that when we add a note to a recipe the bidierectional relation is automatically set
    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            //notes.setRecipe(this);
        }
    }

    //added so that when adding Ingredient to recipe bidirectional relation set
    public Recipe addIngredient(Ingredient ingredient) {
        //ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
