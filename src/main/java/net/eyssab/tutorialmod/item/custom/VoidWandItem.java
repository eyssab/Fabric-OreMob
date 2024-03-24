package net.eyssab.tutorialmod.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidWandItem extends Item {

    public VoidWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            World world = context.getWorld();

            for(int i = 0; i <= positionClicked.getY() + 64; i++){
                BlockPos pos = positionClicked.down(i);
                BlockState state = context.getWorld().getBlockState(positionClicked.down(i));

                if(state.getBlock() != Blocks.BEDROCK) {
                    world.removeBlock(pos, true);
                } else {
                    break;
                }
            }
        }

        return super.useOnBlock(context);
    }
}
