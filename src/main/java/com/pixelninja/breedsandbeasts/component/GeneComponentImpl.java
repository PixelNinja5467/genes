package com.pixelninja.breedsandbeasts.component;

import com.pixelninja.breedsandbeasts.Gene;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;

import java.util.LinkedList;
import java.util.List;

public class GeneComponentImpl implements GeneComponent{

    private final List<Gene> genes = new LinkedList<>();
    LivingEntity entity;

    public GeneComponentImpl(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public List<Gene> getGenes() {
        return genes;
    }

    @Override
    public boolean hasGene(Gene gene) {
            for (Gene var : genes) {
                if (var.equals(gene)) {
                    return true;
                }
            }
        return false;
    }

    @Override
    public void addGene(Gene gene) {
        genes.add(gene);
    }

    @Override
    public void addGenes(List<Gene> geneList) {
        genes.addAll(geneList);
    }

    @Override
    public void removeGene(Gene gene) {
        genes.remove(gene);
    }

    @Override
    public void clearGenes() {
        genes.clear();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        genes.clear();
        NbtList list = (NbtList) tag.get("Genes");
        assert list != null;
        for (NbtElement elem : list) {
            int ordinal = ((NbtInt) elem).intValue();
            genes.add(Gene.values()[ordinal]);
        }

    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        NbtList list = new NbtList();
        for (Gene gene : genes) {
            list.add(NbtInt.of(gene.ordinal()));
        }
        tag.put("Genes", list);
    }
}
