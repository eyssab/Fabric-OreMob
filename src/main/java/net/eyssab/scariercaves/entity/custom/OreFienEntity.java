package net.eyssab.scariercaves.entity.custom;

import net.eyssab.scariercaves.item.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.EnumSet;

import static net.eyssab.scariercaves.ScarierCaves.MOD_ID;

public class OreFienEntity extends VexEntity {

    public OreFienEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    public static DefaultAttributeContainer.Builder createOreFienAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 45)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 3f)
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

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WARDEN_AMBIENT;
    }

    @Override
    public boolean canGather(ItemStack stack) {
        return super.canGather(stack);
    }

    float movementPauseTicks = 0f;
    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient()) {
            // Check if the player is holding a diamond
            if (player.getStackInHand(hand).getItem() == Items.DIAMOND) {
                // Stop the mob's movement for 5 seconds (100 ticks)
                this.setVelocity(-1,-1,-1);
                this.movementPauseTicks = 100;

                // Add diamond to the mob's main hand
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND));

                // Play a sound effect indicating the action
                this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, this.getSoundVolume(), this.getSoundPitch());

                // Consume the diamond from the player's hand
                player.getStackInHand(hand).decrement(1);

                return ActionResult.SUCCESS;
            }
        }
        // Proceed with the default interaction behavior
        return super.interactMob(player, hand);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.movementPauseTicks > 0) {
            this.movementPauseTicks--;
            this.setVelocity(0, 0, 0);
            if (this.movementPauseTicks == 1) {
                this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 0, 0, 0);

                // Play random sound effect
                this.playSound(SoundEvents.ENTITY_GHAST_DEATH, 1.0f, 1.0f);

                //10 percent chance of diamond drop
                if (random.nextInt(100) < 10) {
                    this.dropItem(Items.DIAMOND);
                }

                //50 percent chance of iron drop
                if (random.nextInt(100) < 50) {
                    this.dropItem(Items.IRON_INGOT);
                }

                //100 percent chance of stick drop
                if (random.nextInt(100) < 100) {
                    this.dropItem(Items.STICK);
                }

                //5 percent chance for elytra shard drop
                if (random.nextInt(100) < 5) {
                    this.dropItem(ModItems.ELYTRA_SHARD);
                }

                // Die
                this.kill();
            }
        }
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

            // Check if the target is a player
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

                    //apply blindness and slowness when staring
                    World world = getWorld();
                    if (world instanceof ServerWorld serverWorld) {
                        StatusEffectInstance darknessEffectInstance = new StatusEffectInstance(StatusEffects.DARKNESS, 100, 2);
                        StatusEffectInstance slownessEffectInstance = new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 2);
                        StatusEffectUtil.addEffectToPlayersWithinDistance(serverWorld, targetEntity, targetEntity.getPos(), 50.0, darknessEffectInstance, 1000);
                        StatusEffectUtil.addEffectToPlayersWithinDistance(serverWorld, targetEntity, targetEntity.getPos(), 50.0, slownessEffectInstance, 1000);
                    }
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

