package com.pixelninja.breedsandbeasts.mixin;

import com.pixelninja.breedsandbeasts.Gene;
import com.pixelninja.breedsandbeasts.component.GeneComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PassiveEntity.class)
public class PassiveEntityMixin {

    @Inject(method = "createChild", at = @At("HEAD"), cancellable = true)
    public void inheritBreed(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<PassiveEntity> cir) {
        List<Gene> genes = GeneComponent.KEY.get(passiveEntity).getGenes();
        PassiveEntity entity = (PassiveEntity) passiveEntity.getType().create(serverWorld); //new SheepEntity(EntityType.SHEEP, serverWorld);
        GeneComponent.KEY.get(entity).addGenes(genes);
        cir.setReturnValue(entity);
    }
}
