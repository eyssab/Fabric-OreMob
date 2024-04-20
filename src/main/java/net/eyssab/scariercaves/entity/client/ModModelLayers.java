package net.eyssab.scariercaves.entity.client;

import net.eyssab.scariercaves.ScarierCaves;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer BUFFED_MINECART =
            new EntityModelLayer(new Identifier(ScarierCaves.MOD_ID, "buffed_minecart"), "main");

    public static final EntityModelLayer ORE_FIEN =
            new EntityModelLayer(new Identifier(ScarierCaves.MOD_ID, "ore_fien"), "main");
}
