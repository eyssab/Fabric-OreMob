package net.eyssab.tutorialmod.entity.custom;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.logging.Logger;

public class BuffedMinecartEntity extends ChestMinecartEntity {
    public BuffedMinecartEntity(EntityType<? extends ChestMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onClose(PlayerEntity player) {
        //if on a railcart
        World world = player.getWorld();
        BlockPos blockPos = this.getBlockPos();

        if(isOnRail()) {
            world.createExplosion(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3.0f, World.ExplosionSourceType.TNT);
        }
        player.heal(10.0F);
        super.onClose(player);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if (world != null) { // Ensure world is not null
            BlockPos blockPosBelow = this.getBlockPos().down(); // Get the block position below the entity
            BlockPos blockPosInFront = this.getBlockPos().north(); // Get the block position in front of the entity
            BlockPos blockPosAboveInFront = this.getBlockPos().north().up(); // Get the block position above in front of the entity
            BlockPos blockPos = this.getBlockPos(); // Get the block position of the entity

            Block blockBelow = world.getBlockState(blockPosBelow).getBlock();
            Block blockInFront = world.getBlockState(blockPosInFront).getBlock();
            Block blockAboveInFront = world.getBlockState(blockPosAboveInFront).getBlock();
            Block curBlock = world.getBlockState(blockPos).getBlock();
            if (blockBelow instanceof GrassBlock) {
                if (!(curBlock instanceof RailBlock)) {
                    if (blockAboveInFront instanceof AirBlock && blockInFront instanceof GrassBlock) {
                        // If block in front is grass and next block up and in front is air then place rail at that spot
                        world.setBlockState(blockPosAboveInFront, Blocks.RAIL.getDefaultState());
                    }
                    // Place rail block below the entity
                    else {
                        world.setBlockState(blockPos, Blocks.RAIL.getDefaultState());
                    }
                    this.setVelocity(this.getVelocity().getX()*5 + 1, this.getVelocity().getY()*5 + 1, this.getVelocity().getZ()*5 + 1);
                }
            }
        }
    }


}