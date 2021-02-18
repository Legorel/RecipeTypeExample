package fr.legorel.recipetypeexample;

import fr.legorel.recipetypeexample.block.Compressor;
import fr.legorel.recipetypeexample.recipe.CompressorRecipe;
import fr.legorel.recipetypeexample.screen.CompressorScreenHandler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeTypeExample implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "recipetypeexample";
    public static final String MOD_NAME = "RecipeTypeExample";

    public static final Identifier COMPRESSOR_ID = new Identifier(MOD_ID, "compressor");
    public static final Compressor COMPRESSOR;
    public static final RecipeType<CompressorRecipe> COMPRESSOR_RECIPE;
    public static final RecipeSerializer<CompressorRecipe> COMPRESSOR_RECIPE_SERIALIZER;
    public static final ScreenHandlerType<CompressorScreenHandler> COMPRESSOR_SCREEN_HANDLER;

    static {
        COMPRESSOR = Registry.register(Registry.BLOCK, COMPRESSOR_ID,
                new Compressor(FabricBlockSettings.of(Material.METAL)));
        Registry.register(Registry.ITEM, COMPRESSOR_ID,
                new BlockItem(COMPRESSOR, new Item.Settings().group(ItemGroup.DECORATIONS)));
        COMPRESSOR_RECIPE = Registry.register(Registry.RECIPE_TYPE, COMPRESSOR_ID,
                new RecipeType<CompressorRecipe>() {
                    @Override
                    public String toString() {
                        return COMPRESSOR_ID.getPath();
                    }
                });
        COMPRESSOR_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, COMPRESSOR_ID,
                new CompressorRecipe.Serializer());
        COMPRESSOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(COMPRESSOR_ID,
                CompressorScreenHandler::new);
    }

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}