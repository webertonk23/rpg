package items;

import characters.Character;
import interfaces.Usable;

public class Potion extends Item implements Usable {

    private PotionType type;
    private int amount;
    public Potion(String name, String description, int value, int weight, PotionType type, int amount) {
        super(name, description, value, weight);
        this.type = type;
        this.amount = amount;
    }

    public PotionType getType() {
        return type;
    }

    @Override
    public void use(Character character) {
        switch (type) {
            case HEALING:
                int healthBefore = character.getHealth();
                character.setHealth(Math.min(character.getMaxHealth(), character.getHealth() + amount));
                int healthGained = character.getHealth() - healthBefore;
                System.out.println(character.getName() + " usou " + this.getName() + " e recuperou " + healthGained
                        + " de vida. Vida atual: " + character.getHealth() + "/" + character.getMaxHealth());
                break;
            case MANA:
                int manaBefore = character.getMana();
                character.setMana(Math.min(character.getMaxMana(), character.getMana() + amount));
                int manaGained = character.getMana() - manaBefore;
                System.out.println(character.getName() + " usou " + this.getName() + " e recuperou " + manaGained
                        + " de vida. Vida atual: " + character.getMana() + "/" + character.getMaxMana());
                break;
            case BUFF:
                // Lógica para buff temporário (complexo, exige timer ou sistema de buffs)
                System.out.println(character.getName() + " usou " + this.getName() + " e ganhou " + amount
                        + " de Força temporariamente.");
                break;
        }
    }

    public enum PotionType {
        HEALING, MANA, BUFF
    }
}
