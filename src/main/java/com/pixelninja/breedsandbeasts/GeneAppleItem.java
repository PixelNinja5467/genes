package com.pixelninja.breedsandbeasts;

import com.pixelninja.breedsandbeasts.component.GeneComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.List;

public class GeneAppleItem extends Item {
    Gene gene;
    List<Gene> geneList;
    Settings settings;
    public boolean isConverting = false;

    public GeneAppleItem(Settings settings, Gene gene) {
        super(settings);
        this.gene = gene;
        this.settings = settings;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity.hasStatusEffect(StatusEffects.WEAKNESS)){
            this.isConverting = true;
            this.finishConverting(entity);
            stack.decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public Gene getGene() {
        return gene;
    }

    public void finishConverting(LivingEntity entity) {
        int conversionTime = 1200;
        while (this.isConverting) {
            conversionTime--;
            if (conversionTime < 0) {
                GeneComponent.KEY.get(entity).addGene(this.gene);
                entity.removeStatusEffect(StatusEffects.WEAKNESS);
                this.isConverting = false;
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
