package items;

import characters.Character;
import interfaces.Equippable;

public abstract class Equipment extends Item implements Equippable {
    protected Slot slot;
    protected int strengthBonus;
    protected int dexterityBonus;
    protected int intelligenceBonus;
    protected int vitalityBonus;
    protected int wisdomBonus;
    protected int charismaBonus;
    protected int luckBonus;
    protected int damageReduction;
    protected int attackDamage;
    public Equipment(String name, String description, int value, int weight,
                     Slot slot, int strengthBonus, int dexterityBonus, int intelligenceBonus,
                     int vitalityBonus, int wisdomBonus, int charismaBonus, int luckBonus,
                     int damageReduction, int attackDamage) {
        super(name, description, value, weight);
        this.slot = slot;
        this.strengthBonus = strengthBonus;
        this.dexterityBonus = dexterityBonus;
        this.intelligenceBonus = intelligenceBonus;
        this.vitalityBonus = vitalityBonus;
        this.wisdomBonus = wisdomBonus;
        this.charismaBonus = charismaBonus;
        this.luckBonus = luckBonus;
        this.damageReduction = damageReduction;
        this.attackDamage = attackDamage;
    }

    @Override
    public void onEquip(Character character) {
        character.setStrength(character.getStrength() + strengthBonus);
        character.setDexterity(character.getDexterity() + dexterityBonus);
        character.setIntelligence(character.getIntelligence() + intelligenceBonus);
        character.setVitality(character.getVitality() + vitalityBonus);
        character.setWisdom(character.getWisdom() + wisdomBonus);
        character.setCharisma(character.getCharisma() + charismaBonus);
        character.setLuck(character.getLuck() + luckBonus);

        if (vitalityBonus != 0) {
            character.calculateMaxHealth();
        }

        character.setTotalDamageReduction(character.getTotalDamageReduction() + this.damageReduction);

    }

    @Override
    public void onUnequip(Character character) {
        character.setStrength(character.getStrength() - strengthBonus);
        character.setDexterity(character.getDexterity() - dexterityBonus);
        character.setIntelligence(character.getIntelligence() - intelligenceBonus);
        character.setVitality(character.getVitality() - vitalityBonus);
        character.setWisdom(character.getWisdom() - wisdomBonus);
        character.setCharisma(character.getCharisma() - charismaBonus);
        character.setLuck(character.getLuck() - luckBonus);

        if (vitalityBonus != 0) {
            character.calculateMaxHealth();
        }

        character.setTotalDamageReduction(character.getTotalDamageReduction() - this.damageReduction);
    }

    public Slot getSlot() {
        return slot;
    }

    public int getStrengthBonus() {
        return strengthBonus;
    }

    public int getDexterityBonus() {
        return dexterityBonus;
    }

    public int getIntelligenceBonus() {
        return intelligenceBonus;
    }

    public int getVitalityBonus() {
        return vitalityBonus;
    }

    public int getDamageReduction() {
        return damageReduction;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public enum Slot {
        WEAPON, HEAD, CHEST, RING, AMULET
    }
}
