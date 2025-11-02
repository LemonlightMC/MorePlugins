package com.julizey.moreplugins.recipes.types;

import com.julizey.moreplugins.recipes.RecipeType;
import com.julizey.moreplugins.recipes.Ingredients.Ingredient;

public class BrewingRecipe extends Recipe {

  private Ingredient potion;

  public BrewingRecipe(String name, Ingredient result, Ingredient potion, Ingredient ingredient) {
    super(name, RecipeType.BREWING);
    this.potion = potion;
    setResult(result);
    setIngredients(ingredient);
  }

  public Ingredient setPotion() {
    return potion;
  }

  public BrewingRecipe setPotion(Ingredient potion) {
    this.potion = potion;
    return this;
  }

  @Override
  public org.bukkit.inventory.Recipe toBukkit() {
    throw new UnsupportedOperationException("Unimplemented method 'toBukkit'");
  }
}
