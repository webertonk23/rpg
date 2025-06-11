package items;

import enums.WeaponType;

public class Weapon extends Equipment {
    protected int attackDamage;
    protected WeaponType type;

    public Weapon(String name, String description, int value, int weight,
                  Slot slot, int strengthBonus, int dexterityBonus, int intelligenceBonus,
                  int vitalityBonus, int wisdomBonus, int charismaBonus, int luckBonus,
                  int damageReduction, int attackDamage, WeaponType type) {
        super(name, description, value, weight,
                slot, strengthBonus, dexterityBonus, intelligenceBonus,
                vitalityBonus, wisdomBonus, charismaBonus, luckBonus,
                damageReduction, attackDamage);

        this.attackDamage = attackDamage;
        this.type = type;

        if (slot != Slot.WEAPON) {
            System.err.println("Aviso: Criando uma Arma em um slot que não é WEAPON.");
        }
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public WeaponType getType() {
        return type;
    }


}
