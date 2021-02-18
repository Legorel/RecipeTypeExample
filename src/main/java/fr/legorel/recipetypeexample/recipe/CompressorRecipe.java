package fr.legorel.recipetypeexample.recipe;

import com.google.gson.JsonObject;
import fr.legorel.recipetypeexample.RecipeTypeExample;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CompressorRecipe implements Recipe<Inventory> {
    private final Ingredient ingredient1;
    private final Ingredient ingredient2;
    private final Ingredient ingredient3;
    private final ItemStack result;
    private final Identifier id;

    public CompressorRecipe(Ingredient ingredient1, Ingredient ingredient2, Ingredient ingredient3, ItemStack result, Identifier id) {
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.result = result;
        this.id = id;
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        return
                this.ingredient1.test(inv.getStack(0)) &&
                this.ingredient2.test(inv.getStack(1)) &&
                this.ingredient3.test(inv.getStack(2));
    }

    @Override
    public ItemStack craft(Inventory inv) {
        ItemStack result = this.result.copy();
        CompoundTag compoundTag = inv.getStack(0).getTag();
        if (compoundTag != null) {
            result.setTag(compoundTag.copy());
        }

        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 3;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public ItemStack getRecipeKindIcon() {
        return new ItemStack(Items.PISTON);
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypeExample.COMPRESSOR_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeExample.COMPRESSOR_RECIPE;
    }

    public static class Serializer implements RecipeSerializer<CompressorRecipe> {
        @Override
        public CompressorRecipe read(Identifier id, JsonObject json) {
            Ingredient ingredient1 = Ingredient.fromJson(json.get("ingredient1").getAsJsonObject());
            Ingredient ingredient2 = Ingredient.fromJson(json.get("ingredient2").getAsJsonObject());
            Ingredient ingredient3 = Ingredient.fromJson(json.get("ingredient3").getAsJsonObject());
            ItemStack result = ShapedRecipe.getItemStack(json.get("result").getAsJsonObject());
            return new CompressorRecipe(ingredient1, ingredient2, ingredient3, result, id);
        }

        @Override
        public CompressorRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient ingredient1 = Ingredient.fromPacket(buf);
            Ingredient ingredient2 = Ingredient.fromPacket(buf);
            Ingredient ingredient3 = Ingredient.fromPacket(buf);
            ItemStack result = buf.readItemStack();
            return new CompressorRecipe(ingredient1, ingredient2, ingredient3, result, id);
        }

        @Override
        public void write(PacketByteBuf buf, CompressorRecipe recipe) {
            recipe.ingredient1.write(buf);
            recipe.ingredient2.write(buf);
            recipe.ingredient3.write(buf);
            buf.writeItemStack(recipe.result);
        }
    }
}
