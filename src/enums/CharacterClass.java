package enums;

public enum CharacterClass {
    WARRIOR(
            "Warrior",
            30,
            10,
            10,
            20,
            10,
            10,
            10
    ),
    MAGE(
            "Mage",
            10,
            15,
            30,
            10,
            15,
            10,
            10
    ),
    ARCHER(
            "Archer",
            10,
            30,
            10,
            15,
            10,
            15,
            10
    );

    private final String className;
    private final int strength;
    private final int dexterity;
    private final int intelligence;
    private final int vitality;
    private final int wisdom;
    private final int charisma;
    private final int luck;

    CharacterClass(String className, int strength, int dexterity, int intelligence, int vitality, int wisdom, int charisma, int luck) {
        this.className = className;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.luck = luck;
    }

    public String getClassName() {
        return className;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getVitality() {
        return vitality;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getLuck() {
        return luck;
    }


    @Override
    public String toString() {
        return className + " (base [SRT: " + strength + ", INT: " + intelligence + ", DEX: " + dexterity + "])";
    }
}
