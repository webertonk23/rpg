package characters;

import enums.CharacterClass;

public class Warrior extends Character {
    public Warrior(String name) {
        super(name, CharacterClass.WARRIOR);
    }

    @Override
    public void introduce() {
        System.out.println(String.format("I am Warrior %s strong!", name));
    }

    @Override
    public int calculateDamage() {
        int damage = (int) (strength + ((double) level * 1.5));

        return (int) (damage + (double) level / 2);
    }
}
