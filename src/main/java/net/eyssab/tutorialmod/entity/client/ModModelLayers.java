package net.eyssab.tutorialmod.entity.client;

import net.eyssab.tutorialmod.TutorialMod;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer BUFFED_MINECART =
            new EntityModelLayer(new Identifier(TutorialMod.MOD_ID, "buffed_minecart"), "main");

    public static final EntityModelLayer ORE_FIEN =
            new EntityModelLayer(new Identifier(TutorialMod.MOD_ID, "ore_fien"), "main");
}
