package interfaces;

import characters.Character;

import java.io.Serializable;

public interface Skill extends Serializable {
    String getName();

    String getDescription();

    int getManaCost();

    int getCooldown();

    int getCurrentCooldown();

    void setCurrentCooldown(int cooldown);

    boolean canUse(Character player);

    void execute(Character player, Character target);

    void reduceCooldown();
}
