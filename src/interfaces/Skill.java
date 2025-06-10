package interfaces;

import java.io.Serializable;

import characters.Character;

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
