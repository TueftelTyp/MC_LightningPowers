package de.mcrising.lightningpowers.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class LightningModMenu implements ModMenuApi {
    @Override public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return PowerConfigScreen::create;
    }
}
