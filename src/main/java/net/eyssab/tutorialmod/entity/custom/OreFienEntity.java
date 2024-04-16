package net.eyssab.tutorialmod.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.EnumSet;

public class OreFienEntity extends VexEntity {

    public OreFienEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createOreFienAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 5f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(4, new ChargeTargetGoal());
        this.goalSelector.add(8, new LookAtTargetGoal());
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 100.0f, 1.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 100.0f));
        this.targetSelector.add(3, new ActiveTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, false));
    }

    public static boolean canSpawn(EntityType<? extends OreFienEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && canMobSpawn(type, world, spawnReason, pos, random);
    }

//    public static boolean canSpawn(EntityType<WolfEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
//        return world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) && WolfEntity.isLightLevelValidForNaturalSpawn(world, pos);
//    }

    public static boolean canMobSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos blockPos = pos.down();
        return spawnReason == SpawnReason.SPAWNER || world.getBlockState(blockPos).allowsSpawning(world, blockPos, type);
    }

    class ChargeTargetGoal
            extends Goal {
        public ChargeTargetGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = OreFienEntity.this.getTarget();
            if (livingEntity != null && livingEntity.isAlive() && !OreFienEntity.this.getMoveControl().isMoving() && OreFienEntity.this.random.nextInt(OreFienEntity.ChargeTargetGoal.toGoalTicks(7)) == 0) {
                return OreFienEntity.this.squaredDistanceTo(livingEntity) > 20.0;
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return true;
        }

        @Override
        public void start() {
            LivingEntity livingEntity = OreFienEntity.this.getTarget();
            if (livingEntity != null) {
                Vec3d vec3d = livingEntity.getEyePos();
                OreFienEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 5.0);
            }
            OreFienEntity.this.setCharging(true);
            OreFienEntity.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0f, 3.0f);
        }

        @Override
        public void stop() {
            OreFienEntity.this.setCharging(false);
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = OreFienEntity.this.getTarget();
            if (livingEntity == null) {
                return;
            }
            if (OreFienEntity.this.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
                OreFienEntity.this.tryAttack(livingEntity);
                OreFienEntity.this.setCharging(false);
            } else {
                double d = OreFienEntity.this.squaredDistanceTo(livingEntity);
                if (d < 100.0) {
                    Vec3d vec3d = livingEntity.getEyePos();
                    OreFienEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0);
                }
            }
        }
    }

    class LookAtTargetGoal
            extends Goal {
        public LookAtTargetGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return !OreFienEntity.this.getMoveControl().isMoving() && OreFienEntity.this.random.nextInt(OreFienEntity.LookAtTargetGoal.toGoalTicks(7)) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void tick() {
            BlockPos blockPos = OreFienEntity.this.getBounds();
            if (blockPos == null) {
                blockPos = OreFienEntity.this.getBlockPos();
            }
            for (int i = 0; i < 3; ++i) {
                BlockPos blockPos2 = blockPos.add(OreFienEntity.this.random.nextInt(15) - 7, OreFienEntity.this.random.nextInt(11) - 5, OreFienEntity.this.random.nextInt(15) - 7);
                if (!OreFienEntity.this.getWorld().isAir(blockPos2)) continue;
                OreFienEntity.this.moveControl.moveTo((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5, 0.25);
                if (OreFienEntity.this.getTarget() != null) break;
                OreFienEntity.this.getLookControl().lookAt((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5, 180.0f, 20.0f);
                break;
            }
        }
    }

}

