package characters;

import enums.CharacterClass;

public class Archer extends Character{
    public Archer(String name) {
        super(name, CharacterClass.ARCHER);
    }

    @Override
    public void introduce() {
        System.out.println("I am Archer " + name + ", swift and sharp!");
    }

    @Override
    public int calculateDamage() {
        int damage = (int) (dexterity + ((double) level * 1.5));
        
        return (int) (damage + (double) level / 2);
    }
}
