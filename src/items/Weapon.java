package items;

public class Weapon extends Equipment {

    public Weapon(String name, String description, int value, int weight,
            Slot slot, int strengthBonus, int dexterityBonus, int intelligenceBonus,
            int vitalityBonus, int wisdomBonus, int charismaBonus, int luckBonus,
            int damageReduction, int attackDamage) {
        super(name, description, value, weight,
                slot, strengthBonus, dexterityBonus, intelligenceBonus,
                vitalityBonus, wisdomBonus, charismaBonus, luckBonus,
                damageReduction, attackDamage);

        if (slot != Slot.WEAPON) {
            System.err.println("Aviso: Criando uma Arma em um slot que não é WEAPON.");
        }
    }

}
