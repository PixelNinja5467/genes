package com.pixelninja.breedsandbeasts.mixin;

import com.pixelninja.breedsandbeasts.Gene;
import com.pixelninja.breedsandbeasts.component.GeneComponent;
import com.pixelninja.breedsandbeasts.component.GeneComponentImpl;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {

    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

   /* @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void setBreed(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isEmpty()){
            GeneComponent.KEY.get(this).addGene(Gene.CREEPER);
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    } */

    @Inject(method = "onEatingGrass", at = @At("HEAD"))
    public void eGreaass(CallbackInfo ci) {
        if (GeneComponent.KEY.get(this).hasGene(Gene.Creeper)) {
            world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 5.0F, Explosion.DestructionType.DESTROY);
        }
        if (GeneComponent.KEY.get(this).hasGene(Gene.Flaming)) {
            SmallFireballEntity entity = EntityType.SMALL_FIREBALL.create(world);
            assert entity != null;
            world.spawnEntity(entity);
            entity.pushAwayFrom(this);
        }
    }

    @Inject(method = "createChild", at = @At("HEAD"), cancellable = true)
    public void inheritBreed(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<SheepEntity> cir) {
        List<Gene> genes = GeneComponent.KEY.get(passiveEntity).getGenes();
        SheepEntity entity = new SheepEntity(EntityType.SHEEP, serverWorld);
        GeneComponent.KEY.get(entity).addGenes(genes);
        cir.setReturnValue(entity);
    }

}
