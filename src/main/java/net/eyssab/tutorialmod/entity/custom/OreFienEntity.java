package net.eyssab.tutorialmod.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;

import static net.eyssab.tutorialmod.TutorialMod.MOD_ID;

public class OreFienEntity extends VexEntity {

    public OreFienEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


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

    public static boolean canSpawn(EntityType<OreFienEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && isLightLevelValidForNaturalSpawn(world, pos) && !world.isSkyVisible(pos) && pos.getY() < 50;
    }

    protected static boolean isLightLevelValidForNaturalSpawn(BlockRenderView world, BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) <= 5;
    }

    class ChargeTargetGoal
            extends Goal {
        private LivingEntity targetEntity;

        public ChargeTargetGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = OreFienEntity.this.getTarget();
            if (livingEntity != null && livingEntity.isAlive() && !OreFienEntity.this.getMoveControl().isMoving() && OreFienEntity.this.random.nextInt(OreFienEntity.ChargeTargetGoal.toGoalTicks(7)) == 0 && livingEntity.getArmor() > 0) {
                return OreFienEntity.this.squaredDistanceTo(livingEntity) > 20.0;
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return this.targetEntity != null && this.targetEntity.isAlive() && this.targetEntity.getArmor() > 0;
        }

        @Override
        public void start() {
            OreFienEntity.this.setCharging(true);
            OreFienEntity.this.playSound(SoundEvents.BLOCK_ANVIL_FALL, 10.0f, 1.0f);
            LivingEntity livingEntity = OreFienEntity.this.getTarget();

            // Check if the target is a player and if they are wearing armor
            if (livingEntity != null) {
                Vec3d vec3d = livingEntity.getEyePos();
                OreFienEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 5.0);
            }
            this.targetEntity = OreFienEntity.this.getTarget();
        }


        @Override
        public void stop() {
            this.targetEntity = null;
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

            //Check if mob can see player
            if (OreFienEntity.this.canSee(this.targetEntity)) {
                // Check if the player is staring
                if (isPlayerStaringAtMob(OreFienEntity.this, this.targetEntity)) {
                    LOGGER.info("Player Looking at Mob");
                    // If mob is not charging, stop its movement
                    OreFienEntity.this.setVelocity(0, 0, 0);
                    OreFienEntity.this.playSound(SoundEvents.ENTITY_ENDERMAN_STARE, 5.0f, 3.0f);
                }
            }

            //Charge at player
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

        private boolean isPlayerStaringAtMob(MobEntity mob, LivingEntity player) {
            Vec3d mobEyes = mob.getCameraPosVec(1.0F);
            Vec3d playerEyes = player.getCameraPosVec(1.0F);
            Vec3d lookVec = player.getRotationVec(1.0F);

            double dotProduct = lookVec.dotProduct(mobEyes.subtract(playerEyes));
            double distance = mobEyes.distanceTo(playerEyes);
            double angle = Math.acos(dotProduct / distance);

            return Math.toDegrees(angle) < 5;
        }

//        private boolean isDroppedDiamondInFront() {
//            Vec3d entityLook = OreFienEntity.this.getRotationVec(1.0F); // Get the entity's look vector
//            Vec3d entityPos = OreFienEntity.this.getPos(); // Get the entity's position
//            Vec3d diamondPos = entityPos.add(entityLook.multiply(3.0)); // Calculate a position 3 blocks in front of the entity
//            // Check if there's a dropped diamond within a certain range in front of the entity
//            List<Entity> diamondList = OreFienEntity.this.getWorld().getEntitiesByClass(ItemEntity.class, new Box(diamondPos.x - 1, diamondPos.y - 1, diamondPos.z - 1, diamondPos.x + 1, diamondPos.y + 1, diamondPos.z + 1), entity -> entity == Items.DIAMOND);
//            return !diamondList.isEmpty();
//        }
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

