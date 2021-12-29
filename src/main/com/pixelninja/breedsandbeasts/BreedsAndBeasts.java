package com.pixelninja.breedsandbeasts;

import com.pixelninja.breedsandbeasts.component.GeneComponent;
import com.pixelninja.breedsandbeasts.component.GeneComponentImpl;
import com.pixelninja.breedsandbeasts.registry.ItemRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class BreedsAndBeasts implements ModInitializer, EntityComponentInitializer {

    public static Identifier identifier(String path) {
        return new Identifier("breedsandbeasts", path);
    }

    @Override
    public void onInitialize() {
        //ItemRegistry.register();
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> GeneCommand.register(dispatcher));

    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(LivingEntity.class, GeneComponent.KEY)
                .impl(GeneComponentImpl.class)
                .respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY)
                .end(GeneComponentImpl::new);
    }
}
