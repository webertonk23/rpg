package items;

public class Armor extends Equipment {

    public Armor(String name, String description, int value, int weight,
                 Slot slot, int strengthBonus, int dexterityBonus, int intelligenceBonus,
                 int vitalityBonus, int wisdomBonus, int charismaBonus, int luckBonus,
                 int damageReduction, int attackDamage) {

        super(name, description, value, weight, slot, strengthBonus, dexterityBonus,
                intelligenceBonus, vitalityBonus, wisdomBonus, charismaBonus, luckBonus,
                damageReduction, attackDamage);

        if (slot == Slot.WEAPON || slot == Slot.RING || slot == Slot.AMULET) {
            System.err.println("Aviso: Criando uma Armadura em um slot que não é de armadura corporal.");
        }
    }
}
