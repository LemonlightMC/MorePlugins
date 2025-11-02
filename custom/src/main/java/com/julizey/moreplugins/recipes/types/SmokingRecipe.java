package com.julizey.moreplugins.recipes.types;

import com.julizey.moreplugins.recipes.Ingredients;
import com.julizey.moreplugins.recipes.RecipeType;

public class SmokingRecipe extends SmeltingRecipe {

  public SmokingRecipe(final String recipeName) {
    super(RecipeType.SMOKING, recipeName);
  }

  public SmokingRecipe(final String recipeName, final float experience, final int burningTime) {
    super(RecipeType.SMOKING, recipeName, experience, burningTime);
  }

  public org.bukkit.inventory.SmokingRecipe toBukkit() {
    final org.bukkit.inventory.SmokingRecipe recipe = new org.bukkit.inventory.SmokingRecipe(getKey(),
        this.output.item(),
        Ingredients.toExactChoice(this.ingredients), experience, burningTime);
    if (category != null) {
      recipe.setCategory(category);
    }
    if (group != null && group.length() > 0) {
      recipe.setGroup(group);
    }
    return recipe;
  }
}