package com.julizey.moreplugins.recipes.types;

import com.julizey.moreplugins.recipes.RecipeType;
import com.julizey.moreplugins.recipes.Ingredients.Ingredient;

public class AnvilRecipe extends Recipe {

  private Ingredient base;
  private Ingredient addition;
  private int costLevel = 0;

  public AnvilRecipe(String name, Ingredient result, Ingredient base,
      Ingredient addition) {
    super(name, RecipeType.ANVIL);
    this.base = base;
    this.addition = addition;
  }

  public Ingredient getBase() {
    return base;
  }

  public AnvilRecipe setBase(Ingredient base) {
    this.base = base;
    return this;
  }

  public Ingredient getAddition() {
    return addition;
  }

  public AnvilRecipe setAddition(Ingredient addition) {
    this.addition = addition;
    return this;
  }

  public int costLevel() {
    return costLevel;
  }

  public AnvilRecipe setCostLevel(int costLevel) {
    this.costLevel = costLevel;
    return this;
  }

  @Override
  public org.bukkit.inventory.Recipe toBukkit() {
    throw new UnsupportedOperationException("Unimplemented method 'toBukkit'");
  }
}
