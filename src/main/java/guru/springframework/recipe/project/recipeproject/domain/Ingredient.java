package guru.springframework.recipe.project.recipeproject.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
//avoid addind recipe in hashcode as can cause circular reference error
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {

    private Long id;
    private String description;
    private BigDecimal amount;

    private UnitOfMeasure uom;

    private Recipe recipe;

    //have to add empty one otherwise hibernate will throw error
    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }
}
