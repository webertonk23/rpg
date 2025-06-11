package characters;

import enums.CharacterClass;

public class Mage extends Character {

    public Mage(String name) {
        super(name, CharacterClass.MAGE);
    }

    @Override
    public void introduce() {
        System.out.println("I am Mage " + name + " with arcane power!");
    }

    @Override
    public int calculateDamage() {
        int damage = (int) (intelligence + ((double) level * 1.5));

        return (int) (damage + (double) level / 2);
    }
}
