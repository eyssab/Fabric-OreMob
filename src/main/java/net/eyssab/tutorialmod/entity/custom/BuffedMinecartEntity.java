package net.eyssab.tutorialmod.entity.custom;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class BuffedMinecartEntity extends ChestMinecartEntity {
    public BuffedMinecartEntity(EntityType<? extends ChestMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

//    @Override
//    public void onPlayerCollision(PlayerEntity player) {
//        World world = player.getWorld();
//        BlockPos pos = player.getBlockPos(); // Get the position of the player
//        BlockState blockState = world.getBlockState(pos); // Get the state of the block at player's position
//
//        if(isOnRail()) {
//            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3.0f, World.ExplosionSourceType.TNT);
//        }
//        super.onPlayerCollision(player);
//    }

    @Override
    public void onClose(PlayerEntity player) {
        //if on a railcart
        World world = player.getWorld();
        BlockPos blockPos = this.getBlockPos();

        if(isOnRail()) {
            world.createExplosion(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3.0f, World.ExplosionSourceType.TNT);
        }
        player.heal((float) 10.0F);
        super.onClose(player);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if (world != null) { // Ensure world is not null
            BlockPos blockPosBelow = this.getBlockPos().down(); // Get the block position below the entity
            BlockPos blockPos = this.getBlockPos(); // Get the block position of the entity

            Block blockBelow = world.getBlockState(blockPosBelow).getBlock();
            Block curBlock = world.getBlockState(blockPos).getBlock();
            if(blockBelow instanceof GrassBlock){
                if (!(curBlock instanceof RailBlock)) {
                    world.setBlockState(blockPos, Blocks.RAIL.getDefaultState());
                    this.setVelocity(this.getVelocity().getX()*1.1, this.getVelocity().getY()*1.1, this.getVelocity().getZ()*1.1);
                }
            }
        }
    }

}