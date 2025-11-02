package com.julizey.moreplugins.recipes.types;

import java.util.List;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import com.julizey.moreplugins.recipes.Ingredients.Ingredient;
import com.julizey.moreplugins.recipes.RecipeType;

public interface IRecipe extends Keyed {

  public String getName();

  public RecipeType getType();

  public org.bukkit.inventory.Recipe toBukkit();

  public Ingredient getResult();

  public IRecipe setResult(final Ingredient output);

  public IRecipe setResult(final RecipeChoice output);

  public IRecipe setResult(final Material output);

  public IRecipe setResult(final ItemStack output);

  public IRecipe setResult(final ItemStack output, boolean strict);

  public List<Ingredient> getIngredients();

  public IRecipe setIngredients(final List<Ingredient> ingredients);

  public IRecipe setIngredients(final Ingredient ingredients);

  public IRecipe setIngredients(final RecipeChoice ingredient);

  public IRecipe setIngredients(final Material ingredient);

  public IRecipe setIngredients(final ItemStack ingredient);

  public IRecipe setIngredients(ItemStack ingredient, boolean strict);

  public int hashCode();

  public boolean equals(final Object obj);

  public String toString();
}
