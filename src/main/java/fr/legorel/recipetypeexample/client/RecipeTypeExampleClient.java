package fr.legorel.recipetypeexample.client;

import fr.legorel.recipetypeexample.RecipeTypeExample;
import fr.legorel.recipetypeexample.client.gui.screen.ingame.CompressorScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class RecipeTypeExampleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(RecipeTypeExample.COMPRESSOR_SCREEN_HANDLER, CompressorScreen::new);
    }
}
