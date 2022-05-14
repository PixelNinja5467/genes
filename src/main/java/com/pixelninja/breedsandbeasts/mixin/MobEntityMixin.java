package com.pixelninja.breedsandbeasts.mixin;

import com.pixelninja.breedsandbeasts.Gene;
import com.pixelninja.breedsandbeasts.component.GeneComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "playHurtSound", at = @At("HEAD"))
    public void fire(DamageSource source, CallbackInfo ci) {
        if (GeneComponent.KEY.get(this).hasGene(Gene.FLAMING)) {
            this.world.spawnEntity(EntityType.SMALL_FIREBALL.create(this.world));
        }
    }

}
