package com.pixelninja.breedsandbeasts.mixin;

import com.pixelninja.breedsandbeasts.Gene;
import com.pixelninja.breedsandbeasts.component.GeneComponent;
import com.pixelninja.breedsandbeasts.component.GeneComponentImpl;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {

    @Shadow @Nullable public abstract EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt);

    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob", at = @At("RETURN"), cancellable = true)
    public void fireball(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        player.setOnFireFor(10);
    }

    @Inject(method = "onEatingGrass", at = @At("HEAD"))
    public void eGreaass(CallbackInfo ci) {
    if (GeneComponent.KEY.get(this).hasGene(Gene.CREEPER)) {
            world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 5.0F, Explosion.DestructionType.DESTROY);
        }
    }

    @Inject(method = "createChild", at = @At("HEAD"), cancellable = true)
    public void inheritBreed(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<SheepEntity> cir) {
        List<Gene> genes = GeneComponent.KEY.get(passiveEntity).getGenes();
        SheepEntity entity = new SheepEntity(EntityType.SHEEP, serverWorld);
        GeneComponent.KEY.get(entity).addGenes(genes);
        cir.setReturnValue(entity);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (GeneComponent.KEY.get(this).hasGene(Gene.FLAMING)) {
            if (source.getAttacker() instanceof LivingEntity entity) {
                if (!world.isClient){
                    double distance = Math.sqrt(Math.sqrt(this.squaredDistanceTo(entity))) * 0.5D;
                    SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.world, this, entity.getX() - this.getX() + new Random().nextGaussian() * distance, entity.getBodyY(0.5D) - this.getBodyY(0.5D), new Random().nextGaussian() * distance);
                    smallFireballEntity.setPosition(smallFireballEntity.getX(), this.getBodyY(0.5D) + 0.5D, smallFireballEntity.getZ());
                    world.spawnEntity(smallFireballEntity);
                }
            }
        }
        return super.damage(source, amount);
    }


}
