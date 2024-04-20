package net.eyssab.scariercaves.entity.client;

import net.eyssab.scariercaves.ScarierCaves;
import net.eyssab.scariercaves.entity.custom.OreFienEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
@Environment(value= EnvType.CLIENT)
public class OreFienRenderer extends MobEntityRenderer<OreFienEntity, OreFienModel<OreFienEntity>> {
    private static final Identifier TEXTURE = new Identifier(ScarierCaves.MOD_ID, "textures/entity/ore_fien.png");

    public OreFienRenderer(EntityRendererFactory.Context context) {
        super(context, new OreFienModel<>(context.getPart(ModModelLayers.ORE_FIEN)), 0.5f);
    }


    @Override
    public Identifier getTexture(OreFienEntity entity) {
        return TEXTURE;
    }
}
