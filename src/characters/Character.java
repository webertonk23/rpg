package characters;

import enums.CharacterClass;
import interfaces.Combatant;
import inventory.Inventory;

import java.io.Serializable;
import java.util.Random;

public abstract class Character implements Combatant, Serializable {
    private static final long serialVersionUID = 1L;
    protected static final Random random = new Random();

    protected String name;
    protected CharacterClass characterClass;
    protected int level;
    protected int health;
    protected int mana;
    protected int strength;
    protected int dexterity;
    protected int vitality;
    protected int intelligence;
    protected int wisdom;
    protected int charisma;
    protected int luck;

    protected int maxHealth;
    protected int maxMana;

    protected int experience;
    protected int experienceToNextLevel;

    protected int totalDamageReduction;
    protected int weaponSkillBonus;

    protected Inventory inventory;

    protected Character(String name, CharacterClass characterClass) {
        this.name = name;
        this.characterClass = characterClass;

        this.strength = characterClass.getStrength();
        this.dexterity = characterClass.getDexterity();
        this.intelligence = characterClass.getIntelligence();
        this.vitality = characterClass.getVitality();
        this.wisdom = characterClass.getWisdom();
        this.charisma = characterClass.getCharisma();
        this.luck = characterClass.getLuck();

        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = 100;

        calculateMaxHealth();
        this.health = this.maxHealth;

        calculateMaxMana();
        this.mana = this.maxMana;

        this.totalDamageReduction = 0;
        this.weaponSkillBonus = 0;

        this.inventory = new Inventory(this);
    }

    @Override
    public void attack(Combatant target) {
        if (!(target instanceof Character defender))
            return;

        if (!attemptHit(defender)) {
            System.out.println(this.name + " errou o ataque!");
            return;
        }

        if (defender.hasDodged()) {
            System.out.println(defender.name + " desviou do ataque!");
            return;
        }

        int damage = calculateDamage() + random.nextInt(5);
        if (isCriticalHit()) {
            damage *= 2;
            System.out.println(this.name + " acertou um **GOLPE CRÍTICO**!");
        }

        damage = defender.calculateDamageReduction(damage);

        System.out.println(this.name + " ataca " + defender.getName() + " causando " + damage + " de dano!");
        defender.takeDamage(damage);
        defender.printHealthBar();
    }

    @Override
    public void takeDamage(int amount) {
        this.health = Math.max(0, this.health - amount);
        System.out.println(name + " agora tem " + health + " de vida.");
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    public abstract void introduce();

    public abstract int calculateDamage();

    protected boolean attemptHit(Character defender) {
        int hitRating = this.dexterity * 3;
        int evasionRating = defender.dexterity;

        hitRating += this.level * 0.5;
        evasionRating += defender.level * 0.3;

        int netChance = hitRating - evasionRating;

        int finalHitChance = Math.max(10, Math.min(90, 50 + netChance));

        return random.nextInt(100) < finalHitChance;
    }

    protected boolean isCriticalHit() {
        int baseCritChance = 5;
        int finalCritChance = baseCritChance + (this.luck / 2);

        finalCritChance = Math.min(50, finalCritChance);

        return random.nextInt(100) < finalCritChance;
    }

    protected boolean hasDodged() {
        int baseDodgeChance = 5;
        int dodgeChance = baseDodgeChance + (this.dexterity * 1);
        dodgeChance += this.level / 4;

        int finalDodgeChance = Math.max(baseDodgeChance, Math.min(75, dodgeChance));

        return random.nextInt(100) < finalDodgeChance;
    }

    protected int calculateDamageReduction(int incomingDamage) {
        int flatDefense = (int) (this.vitality * 0.3 + (double) this.level / 3);
        int baseFlatDefense = Math.max(0, incomingDamage - flatDefense);

        int totalDefense = baseFlatDefense + this.totalDamageReduction;

        return Math.max(1, totalDefense);
    }

    public void gainExperience(int amount) {
        this.experience += amount;
        System.out.println(String.format("%s ganhou %d XP.", name, amount));

        while (this.experience >= this.experienceToNextLevel) {
            this.experience -= this.experienceToNextLevel;
            levelUp();
        }
    }

    protected void levelUp() {
        this.level++;
        this.experienceToNextLevel *= 1.5;

        System.out.println(String.format("%s subiu para o Nível %d!", name, this.level));

        this.strength += (int) Math.round((double) characterClass.getStrength() / 10.0);
        this.dexterity += (int) Math.round((double) characterClass.getDexterity() / 10.0);
        this.intelligence += (int) Math.round((double) characterClass.getIntelligence() / 10.0);
        this.vitality += (int) Math.round((double) characterClass.getVitality() / 10.0);
        this.wisdom += (int) Math.round((double) characterClass.getWisdom() / 10.0);
        this.charisma += (int) Math.round((double) characterClass.getCharisma() / 10.0);
        this.luck += (int) Math.round((double) characterClass.getLuck() / 10.0);

        calculateMaxHealth();

        this.health = this.maxHealth;
    }

    public void calculateMaxHealth() {
        int baseHealth = 100;
        this.maxHealth = (int) (baseHealth * this.level * (1 + (double) this.vitality / 100));
    }

    protected void calculateMaxMana() {
        int baseMana = 100;
        this.maxMana = (int) (baseMana * this.level * (1 + (double) this.intelligence / 100));
    }

    public void printHealthBar() {
        int totalBars = 100; // total de pipes na barra
        double healthPercentage = (double) health / (double) maxHealth;
        int barsToShow = (int) Math.ceil(healthPercentage * totalBars);

        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < barsToShow; i++) {
            bar.append("|");
        }
        for (int i = barsToShow; i < totalBars; i++) {
            bar.append(" ");
        }

        System.out.printf("%s Vida: [\u001b[32m %s \u001b[0m] %d/%d\n", name, bar.toString(), health, maxHealth);
    }

    @Override
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
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

    public int getWisdom() {
        return wisdom;
    }

    public int getVitality() {
        return vitality;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getLuck() {
        return luck;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getTotalDamageReduction() {
        return totalDamageReduction;
    }

    public int getWeaponSkillBonus() {
        return weaponSkillBonus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public void setTotalDamageReduction(int totalDamageReduction) {
        this.totalDamageReduction = totalDamageReduction;
    }

    public void setWeaponSkillBonus(int weaponSkillBonus) {
        this.weaponSkillBonus = weaponSkillBonus;
    }
}
