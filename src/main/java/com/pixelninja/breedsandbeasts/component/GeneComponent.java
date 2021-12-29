package com.pixelninja.breedsandbeasts.component;

import com.pixelninja.breedsandbeasts.BreedsAndBeasts;
import com.pixelninja.breedsandbeasts.Gene;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;

import java.util.List;


public interface GeneComponent extends Component {

    ComponentKey<GeneComponent> KEY = ComponentRegistry.getOrCreate(BreedsAndBeasts.identifier("genes"), GeneComponent.class);

    List<Gene> getGenes();

    boolean hasGene(Gene gene);

    void addGene(Gene gene);

    void addGenes(List<Gene> geneList);

    void removeGene(Gene gene);

    void clearGenes();

}
