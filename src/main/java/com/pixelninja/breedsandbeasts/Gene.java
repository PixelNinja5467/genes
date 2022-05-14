package com.pixelninja.breedsandbeasts;

public enum Gene {
    SHEEP("Sheep"),
    ENDER("Ender"),
    CREEPER("Creeper"),
    FLAMING("Flaming"),
    GOAT("Goat"),
    LLAMA("Llama"),
    GHASTLY("Ghastly"),
    ARTHROPOD("Arthropod"),
    UNDEAD("Undead");

    public String name;
    Gene(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
